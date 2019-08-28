package com.openkm.dao.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "OKM_LEAVE")
public class LeaveDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "LEAVE_ID")
	private int leaveId;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "LEAVE_TYPE_ID")
	private int leaveTypeId;

	@Column(name = "FROM_DATE")
	private Date fromDate;

	@Column(name = "TO_DATE")
	private Date toDate;

	@Column(name = "IS_HALF_DAY")
	private String isHalfDay;
	
	@Column(name = "NOTE")
	private String note;
	
	@Column(name = "APPROVER_NAME")
	private String approverName;

	@Column(name = "APPROVER_COMMENT")
	private String approverComment;

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getLeaveTypeId() {
		return leaveTypeId;
	}

	public void setLeaveTypeId(int leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getIsHalfDay() {
		return isHalfDay;
	}

	public void setIsHalfDay(String isHalfDay) {
		this.isHalfDay = isHalfDay;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}



	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getApproverComment() {
		return approverComment;
	}

	public void setApproverComment(String approverComment) {
		this.approverComment = approverComment;
	}
	


}
