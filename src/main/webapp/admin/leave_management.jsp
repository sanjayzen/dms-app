<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.servlet.admin.BaseServlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="../css/chosen.css" />
  <link rel="stylesheet" type="text/css" href="css/admin-style.css" />
  <script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
  <script type="text/javascript" src="../js/vanadium-min.js"></script>
  <script type="text/javascript" src="../js/chosen.jquery.js"></script>
   <script type="text/javascript">
   	$(document).ready(function() {
   	 $('select#uc_profile').chosen({disable_search_threshold : 10 });
   	});
   </script>
  <title>Leave Management</title>
  
<script type="text/javascript" src="http://services.iperfect.net/js/IP_generalLib.js"></script>
  
</head>
<body>

        Leave Application
        <br/>
      <form action="Leave" method="post">
      
        <input type="hidden" name="action" value="AddLeave"/>

		<table class="form" width="372px">
          <tr>
            <td>Leave Type Id: 

			</td>
            <td width="100%">
			<select id="leave_type_id" name="leave_type_id" size="4">
				<c:forEach var="lv" items="${leaveType}" varStatus="row">
					<option value="${lv.id}">
						${lv.leaveType}
					</option>
				 </c:forEach>
			 </select>
            </td>
          </tr>
          <tr>
            <td>From Date:</td>
            <td>
			  <input type="text" name="from_date" id="from_date" alt="date" class="IP_calendar" title="d/m/Y">
            </td>
          </tr>
          <tr>
            <td nowrap="nowrap">To Date:</td>
            <td>
			  <input type="text" name="to_date" id="to_date" alt="date" class="IP_calendar" title="d/m/Y">
			</td>
          </tr>
          <tr>
            <td>Note: </td>
            <td><input class=":required :only_on_blur" name="note" size="25" value="${usr.name}"/></td>
          </tr>
          <tr>
            <td>Leave Type Id: 

			</td>
            <td width="100%">
			<select id="approver" name="approver" size="4">
				<c:forEach var="user" items="${userList}" varStatus="row">
					<option value="${user.id}">
						${user.id}
					</option>
				 </c:forEach>
			 </select>
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right">
				<input type="submit" value="Create" class="yesButton"/>
            </td>
          </tr>
        </table>
		
		<h3>Leaves for Approval:</h3>
		<table id="results" class="results-old" width="60%">
          <thead>
            <tr>
              <th width="10%">Leave Type</th>
              <th width="10%">From Date</th>
              <th width="10%">To Date</th>
              <th width="15%">Note </th>
			  <th width="15%">Approver Comment </th>
            </tr>


			<c:if test="${not empty pendingLeaveList}">
				<c:forEach var="leave" items="${pendingLeaveList}" varStatus="row">
					<tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
						<td>${leave.leaveTypeId}</td>
						<td>${leave.fromDate}</td>
						<td>${leave.toDate}</td>
						<td>${leave.note}</td>
						<td>${leave.approverComment}</td>
					</tr>
				</c:forEach>
			</c:if>
          </thead>
	        </table>
		
		
		<table id="results" class="results">
            <tr>
&nbsp;
            </tr>
			<tr>
&nbsp;
            </tr>
            <tr>
&nbsp;
            </tr>
        </table>
		
		<h3>Leaves Applied :</h3>
		<table id="results" class="results-old" width="60%" >
          <thead>
            <tr>
              <th width="10%">Leave Type</th>
              <th width="10%">From Date</th>
              <th width="10%">To Date</th>
              <th width="15%">Note </th>
			  <th width="15%">Approver Comment </th>
            </tr>
			<c:forEach var="leave" items="${appliedLeave}" varStatus="row">
				<tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
					<td>${leave.leaveTypeId}</td>
					<td>${leave.fromDate}</td>
					<td>${leave.toDate}</td>
					<td>${leave.note}</td>
					<td>${leave.approverComment}</td>
				</tr>
			</c:forEach>
          </thead>
        </table>
      </form>
</body>
</html>
