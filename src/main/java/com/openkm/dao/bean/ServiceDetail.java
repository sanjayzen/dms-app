package com.openkm.dao.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "OKM_SERVICE")
public class ServiceDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private int id;


	@Column(name = "SERVICE_TYPE_ID")
	private int serviceTypeId;
	
	@Column(name = "SHORT_DESCRIPTION")
	private String shortDescription;
		
	@Column(name = "DESCRIPTION")
	private String description;
		
	@Column(name = "COMMENT")
	private String comment;
	
	@Column(name = "CREATED_DATE")
	private Date creatDate;
	
	@Column(name = "UPDATE_DATE")
	private Date updateDate;


	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "ASSIGNED_USER_NAME")
	private String assignedUserName;

	
	@Column(name = "STATUS")
	private String status;

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getServiceTypeId() {
		return serviceTypeId;
	}


	public void setServiceTypeId(int serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}


	public String getShortDescription() {
		return shortDescription;
	}


	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public Date getCreatDate() {
		return creatDate;
	}


	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}




	public String getAssignedUserName() {
		return assignedUserName;
	}


	public void setAssignedUserName(String assignedUserName) {
		this.assignedUserName = assignedUserName;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}
