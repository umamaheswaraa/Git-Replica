package com.imaginea.gr.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity implements Entity, Serializable {

	private static final long serialVersionUID = 4373587345581961514L;

	private Long pkey;
	private Boolean isDeleted = false;
	private String createdBy;
	private Date createdOn;
	private String changedBy;
	private Date changedOn;
	
	@Id
	@GeneratedValue
	@Column(name="PKEY")
	public Long getPkey() {
		return pkey;
	}
	public void setPkey(Long pkey) {
		this.pkey = pkey;
	}
	@Column(name="ISDELETED", columnDefinition="NUMBER(1) CHECK(isdeleted in (0,1))")
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	@Column(name="CREATEDBY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name="CREATEDON")
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	@Column(name="UPDATEDBY")
	public String getChangedBy() {
		return changedBy;
	}
	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}
	@Column(name="UPDATEDON")
	public Date getChangedOn() {
		return changedOn;
	}
	public void setChangedOn(Date changedOn) {
		this.changedOn = changedOn;
	} 
	

}
