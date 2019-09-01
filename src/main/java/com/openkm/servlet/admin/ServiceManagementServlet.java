package com.openkm.servlet.admin;

import java.io.IOException;
import java.util.List;

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
import com.openkm.dao.ServiceDetailDAO;
import com.openkm.dao.ServiceTypeDAO;
import com.openkm.dao.bean.ServiceDetail;
import com.openkm.dao.bean.ServiceType;
import com.openkm.dao.bean.User;
import com.openkm.util.WebUtils;

public class ServiceManagementServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ServiceManagementServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
	ServletException {
		log.debug("doGet({}, {})", request.getSession(), response);
		
		ServletContext sc = getServletContext();
		sc = setRequestData(request, sc);
		sc.setAttribute("message", "");
		sc.getRequestDispatcher("/admin/service_management.jsp").forward(request, response);
	}
	
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		log.debug("doPost({}, {})", request, response);
		
		ServletContext sc = getServletContext();
		String action = WebUtils.getString(request, "action");
		if (action.equals("AddService")) {
			sc = applyService(request, sc);
		} else 	if ("PROCESSED".equalsIgnoreCase(action) || "CANCEL".equalsIgnoreCase(action)) {
			sc = processServiceRequest(request, sc);
		}
		sc = setRequestData(request, sc);
		sc.getRequestDispatcher("/admin/service_management.jsp").forward(request, response);
    }
    
    private ServletContext setRequestData(HttpServletRequest request, ServletContext sc) {
		List<ServiceDetail> pendingServiceList = null;
		List<ServiceDetail> appliedService = null;
		List<ServiceType> serviceTypes = null;
		List<User> userList = null;
		List<HttpSessionInfo> sess = HttpSessionManager.getInstance().getSessions();
		String userName = null;
		for (HttpSessionInfo info : sess) {
			userName = info.getUser();
		}
		
		try {
			pendingServiceList = ServiceDetailDAO.findServiceByUserNameStatus(userName, "IN_PROGRESS");
			appliedService = ServiceDetailDAO.findAll();
			serviceTypes = ServiceTypeDAO.findServiceType();
			userList = AuthDAO.findAllUsers(true);
/*			User loggedInUser = null;
			if (userList != null) {
				for (User user: userList) {
					if (user.getId().equalsIgnoreCase(userName)) {
						loggedInUser = user;
					}
				}
			}
			if (loggedInUser != null) {
				userList.remove(loggedInUser);
			}*/
			sc.setAttribute("serviceTypes", serviceTypes);
			sc.setAttribute("userList", userList);
			sc.setAttribute("pendingServiceList", pendingServiceList);
			sc.setAttribute("appliedService", appliedService);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			sc.setAttribute("message", "Error in service process, please contact administrator");
			e.printStackTrace();
			return sc;

		}
		return sc;
		
    }
    
    
    private ServletContext applyService(HttpServletRequest request, ServletContext sc) {
    	
		ServiceDetail service = new ServiceDetail();
		List<HttpSessionInfo> sess = HttpSessionManager.getInstance().getSessions();
		String userName = null;
		for (HttpSessionInfo info : sess) {
			userName = info.getUser();
		}
		service.setCreatedBy(userName);
		service.setShortDescription(WebUtils.getString(request, "short_description"));
		service.setDescription(WebUtils.getString(request, "description"));
		//service.setComment(WebUtils.getString(request, "comment"));
		service.setAssignedUserName(WebUtils.getString(request, "assignedUserName"));
		service.setStatus("IN_PROGRESS");
		service.setServiceTypeId(Integer.valueOf(WebUtils.getString(request, "service_type_id")));
		try {
			ServiceDetailDAO.create(service);
			sc.setAttribute("message", "Service Registered successfully.");
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sc.setAttribute("message", "Error in leave process, please contact administrator! ");
			return sc;
		}
	return sc;
    }
    
/*    private String validateLeave(LeaveDetail leave, String leaveTypeId) {
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
    }*/
    
    
    private ServletContext processServiceRequest(HttpServletRequest request, ServletContext sc) {
		int serviceId = Integer.valueOf(WebUtils.getString(request, "service_id"));
		String comment = WebUtils.getString(request, "comment");
		String action = WebUtils.getString(request, "action");
		
		try {
			ServiceDetail service = ServiceDetailDAO.findByServiceId(serviceId);
			service.setComment(comment);
			if ("PROCESSED".equalsIgnoreCase(action)) {
				service.setStatus("PROCESSED");
			} else {
				service.setStatus("CANCEL");
			}
			ServiceDetailDAO.update(service);
		} catch (DatabaseException e) {
			sc.setAttribute("message", "Error in service registration, please contact administrator");

			e.printStackTrace();
			return sc;

		}
		return sc;

    }
}
