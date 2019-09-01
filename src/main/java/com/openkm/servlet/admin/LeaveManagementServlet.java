package com.openkm.servlet.admin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.HttpSessionInfo;
import com.openkm.core.DatabaseException;
import com.openkm.core.HttpSessionManager;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.LeaveDetailDAO;
import com.openkm.dao.LeaveTypeDAO;
import com.openkm.dao.bean.LeaveDetail;
import com.openkm.dao.bean.LeaveType;
import com.openkm.dao.bean.User;
import com.openkm.util.WebUtils;

public class LeaveManagementServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(LeaveManagementServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
	ServletException {
		log.debug("doGet({}, {})", request.getSession(), response);
		
		ServletContext sc = getServletContext();
		sc = setRequestData(request, sc);
		sc.setAttribute("message", "");
		sc.getRequestDispatcher("/admin/leave_management.jsp").forward(request, response);
	}
	
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		log.debug("doPost({}, {})", request, response);
		
		ServletContext sc = getServletContext();
		String action = WebUtils.getString(request, "action");
		if (action.equals("AddLeave")) {
			sc = applyLeave(request, sc);
		} else 	if ("APPROVE".equalsIgnoreCase(action) || "REJECT".equalsIgnoreCase(action)) {
			sc = processLeaveRequest(request, sc);
		}
		sc = setRequestData(request, sc);
		sc.getRequestDispatcher("/admin/leave_management.jsp").forward(request, response);
    }
    
    private ServletContext setRequestData(HttpServletRequest request, ServletContext sc) {
		List<LeaveDetail> pendingLeaveList = null;
		List<LeaveDetail> appliedLeave = null;
		List<LeaveType> leaveType = null;
		List<User> userList = null;
		List<HttpSessionInfo> sess = HttpSessionManager.getInstance().getSessions();
		String userName = null;
		for (HttpSessionInfo info : sess) {
			userName = info.getUser();
		}
		
		try {
			pendingLeaveList = LeaveDetailDAO.findPendingLeave(userName);
			appliedLeave = LeaveDetailDAO.findByUserName(userName);
			leaveType = LeaveTypeDAO.findLeaveType();
			userList = AuthDAO.findAllUsers(true);
			User loggedInUser = null;
			if (userList != null) {
				for (User user: userList) {
					if (user.getId().equalsIgnoreCase(userName)) {
						loggedInUser = user;
					}
				}
			}
			if (loggedInUser != null) {
				userList.remove(loggedInUser);
			}
			sc.setAttribute("leaveType", leaveType);
			sc.setAttribute("userList", userList);
			sc.setAttribute("pendingLeaveList", pendingLeaveList);
			sc.setAttribute("appliedLeave", appliedLeave);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			sc.setAttribute("message", "Error in leave process, please contact administrator");
			e.printStackTrace();
			return sc;

		}
		return sc;
		
    }
    
    
    private ServletContext applyLeave(HttpServletRequest request, ServletContext sc) {
    	
		LeaveDetail leave = new LeaveDetail();
		List<HttpSessionInfo> sess = HttpSessionManager.getInstance().getSessions();
		String userName = null;
		for (HttpSessionInfo info : sess) {
			userName = info.getUser();
		}
		leave.setUserName(userName);
		leave.setNote(WebUtils.getString(request, "note"));
		leave.setApproverComment(WebUtils.getString(request, "approver_comment"));
		leave.setApproverName(WebUtils.getString(request, "approver"));
		leave.setIsApproved("I");
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH).parse(WebUtils.getString(request, "from_date"));
			toDate = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH).parse(WebUtils.getString(request, "to_date"));
		} catch (ParseException e1) {
			sc.setAttribute("message", "Invalid date format !");
			e1.printStackTrace();
		}
		leave.setFromDate(fromDate);
		leave.setToDate(toDate);
		try {
			String validationError = validateLeave(leave, WebUtils.getString(request, "leave_type_id"));
			if (validationError == null) {
				LeaveDetailDAO.create(leave);
				sc.setAttribute("message", "Leave applied successfully.");
			} else {
				sc.setAttribute("message", validationError);
			}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sc.setAttribute("message", "Error in leave process, please contact administrator! ");
			return sc;
		}
	return sc;
    }
    
    private String validateLeave(LeaveDetail leave, String leaveTypeId) {
    	Date now = new Date();
    	StringBuffer vlidationMessage = new StringBuffer("Validation Error: ");
    	Boolean isValidationError = Boolean.FALSE;
    	if (leave.getFromDate() != null) {
	    	if (leave.getFromDate().before(now)) {
	    		vlidationMessage.append(" From date can not be in past! ");
	    		isValidationError = Boolean.TRUE;
	    	}
    	} else {
    		vlidationMessage.append("From date is mandatory! ");
    		isValidationError = Boolean.TRUE;
    	}
    	if (leave.getToDate() != null) {
	    	if (leave.getToDate().before(now)) {
	    		vlidationMessage.append("To date can not be in past! ");
	    		isValidationError = Boolean.TRUE;
	    	}
    	} else {
    		vlidationMessage.append("To date is mandatory! ");
    		isValidationError = Boolean.TRUE;
    	}

    	if (leaveTypeId == null || "".equals(leaveTypeId)) {
    		vlidationMessage.append("Leave type is mandatory! ");
    		isValidationError = Boolean.TRUE;
    	} else {
    		leave.setLeaveTypeId(Integer.valueOf(leaveTypeId));
    	}
    	
    	if (leave.getApproverName() == null || "".equals(leave.getApproverName())) {
    		vlidationMessage.append("Approver is mandatory! ");
    		isValidationError = Boolean.TRUE;
    	}
    	
    	if (isValidationError) {
    		return vlidationMessage.toString(); 		
    	} else {
    		return null;
    	}
    }
    
    
    private ServletContext processLeaveRequest(HttpServletRequest request, ServletContext sc) {
		int leaveId = Integer.valueOf(WebUtils.getString(request, "leave_id"));
		String approvarComment = WebUtils.getString(request, "approver_comment");
		String action = WebUtils.getString(request, "action");
		
		try {
			LeaveDetail leave = LeaveDetailDAO.findByLeaveId(leaveId);
			leave.setApproverComment(approvarComment);
			if ("APPROVE".equalsIgnoreCase(action)) {
				leave.setIsApproved("Y");
			} else {
				leave.setIsApproved("N");
			}
			LeaveDetailDAO.update(leave);
		} catch (DatabaseException e) {
			sc.setAttribute("message", "Error in leave process, please contact administrator");

			e.printStackTrace();
			return sc;

		}
		return sc;

    }
}
