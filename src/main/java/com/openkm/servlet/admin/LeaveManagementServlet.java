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
		List<LeaveType> leaveType = null;
		List<User> userList = null;

		System.out.println("-----------------------START----------------------------");
		
		try {
			leaveType = LeaveTypeDAO.findLeaveType();
			userList = AuthDAO.findAllUsers(true);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.setAttribute("leaveType", leaveType);
		sc.setAttribute("userList", userList);

		System.out.println("-----------------------END----------------------------");
		sc.getRequestDispatcher("/admin/leave_management.jsp").forward(request, response);

	}
	
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		log.debug("doPost({}, {})", request, response);
		ServletContext sc = getServletContext();

		System.out.println("-----------------------START----------------------------");
		String action = WebUtils.getString(request, "action");
		if (action.equals("AddLeave")) {
			LeaveDetail leave = new LeaveDetail();
			List<HttpSessionInfo> sess = HttpSessionManager.getInstance().getSessions();
			String userName = null;
			for (HttpSessionInfo info : sess) {
				userName = info.getUser();
			}
			leave.setUserName(userName);
			leave.setLeaveTypeId(Integer.valueOf(WebUtils.getString(request, "leave_type_id")));
			leave.setNote(WebUtils.getString(request, "note"));
			leave.setApproverComment(WebUtils.getString(request, "approver_comment"));
			leave.setApproverName(WebUtils.getString(request, "approver"));
			Date fromDate = null;
			Date toDate = null;
			try {
				fromDate = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH).parse(WebUtils.getString(request, "from_date"));
				toDate = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH).parse(WebUtils.getString(request, "to_date"));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			leave.setFromDate(fromDate);
			leave.setToDate(toDate);
			try {
				LeaveDetailDAO.create(leave);
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("-----------------------END----------------------------");
		sc.getRequestDispatcher("/admin/leave_management.jsp").forward(request, response);
    	
    }

}
