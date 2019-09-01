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
	
	function postReq(action, commentId, serviceId) {
		var frm = document.getElementById('post_req');
		var acomment = document.getElementById(commentId);
		if (frm) {
				var sAction = document.getElementById("action");
				sAction.value = action;
				var leaveIdElmt = document.getElementById("service_id");
				leaveIdElmt.value = serviceId;
				var acommentElmt = document.getElementById("comment");
				acommentElmt.value = acomment.value;
		} 
			frm.submit();
	}
	
   </script>
  <title>Service Management</title>
  
<script type="text/javascript" src="http://services.iperfect.net/js/IP_generalLib.js"></script>
  
</head>
<body>

        Service Application
        <br/>
      <form action="Service" method="post">
      
        <input type="hidden" name="action" value="AddService"/>

		<table class="form" width="372px">
          <tr>
            <td>Select Service Type: 

			</td>
            <td width="100%">
			<select id="service_type_id" name="service_type_id" size="4">
				<c:forEach var="sv" items="${serviceTypes}" varStatus="row">
					<option value="${sv.id}">
						${sv.serviceType}
					</option>
				 </c:forEach>
			 </select>
            </td>
          </tr>
          <tr>
            <td>Short Description:</td>
            <td>
			  <input type="text" name="short_description" id="short_description" class=":required :only_on_blur">
            </td>
          </tr>
          <tr>
            <td nowrap="nowrap">Description :</td>
            <td>
			  <input type="text" name="description" id="description" class=":required :only_on_blur">
			</td>
          </tr>
          <tr>
            <td>Assign Service: 

			</td>
            <td width="100%">
			<select id="assignedUserName" name="assignedUserName" size="4" class=":required :only_on_blur">
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
		
	</form>
	
	<form action="Service" method="post" id='post_req'>
		<input type="hidden" name="action" id="action" value="PROCESSED"/>
		<input type="hidden" name="service_id" id="service_id" value=""/>
		<input type="hidden" name="comment" id="comment" value=""/>

		<h3>Action on Approval:</h3>
		<table id="results" class="results-old" width="80%">
          <thead>
            <tr>
              <th width="10%">Service Type</th>
              <th width="10%">Short Description</th>
              <th width="10%">Description</th>
              <th width="15%">Assigned User </th>
			  <th width="15%"> Comment </th>
			  <th width="10%"> Action </th>
            </tr>


			<c:if test="${not empty pendingServiceList}">
				<c:forEach var="service" items="${pendingServiceList}" varStatus="row">
					<tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
					<c:forEach var="type" items="${serviceTypes}" varStatus="row">
						<c:if test="${type.getId() == service.serviceTypeId}">
							<td>
								${type.serviceType}
							</td>
						</c:if>
					</c:forEach>
						<td>${service.shortDescription}</td>
						<td>${service.description}</td>
						<td>${service.assignedUserName}</td>
						<td><input name="Acomment_${service.id}" id="Acomment_${service.id}" size="70" value=""/></td>
						<td><input type="submit" value="PROCESSED" onclick="postReq('PROCESSED','Acomment_${service.id}','${service.id}')" class="yesButton"/> 
						<input type="submit" onclick="postReq('CANCEL','Acomment_${service.id}','${service.id}')" value="Cancel" class="yesButton"/></td>
					</tr>
				</c:forEach>
			</c:if>
          </thead>
	    </table>
	</form>
		
		
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
		
		<h3>Server Request Created :</h3>
		<table id="results" class="results-old" width="60%" >
          <thead>
            <tr>
              <th width="10%">Service Type</th>
              <th width="10%">Short Description</th>
              <th width="10%">Description</th>
              <th width="15%">Assigned User </th>
			  <th width="15%"> Comment </th>
			  <th width="15%">Status </th>
			  
            </tr>
			<c:forEach var="service" items="${appliedService}" varStatus="row">
				<tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
						<c:forEach var="type" items="${serviceTypes}" varStatus="row">
							<c:if test="${type.id == service.serviceTypeId}">
								<td>
									${type.id}
								</td>
							</c:if>
						</c:forEach>
						<td>${service.shortDescription}</td>
						<td>${service.description}</td>
						<td>${service.assignedUserName}</td>
					<td>${service.comment}</td>
					<td>${service.status}</td>
					
				</tr>
			</c:forEach>
          </thead>
        </table>
      </form>
</body>
</html>
