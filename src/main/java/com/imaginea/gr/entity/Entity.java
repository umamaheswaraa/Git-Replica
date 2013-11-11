package com.imaginea.gr.entity;

import java.util.Date;

public interface Entity {

	public Long getPkey();

	public void setPkey(Long pkey);
	
	public Boolean getIsDeleted();
    
    public void setIsDeleted(Boolean deleted);
    
	public String getCreatedBy();

	public void setCreatedBy(String creator);
	
	public Date getCreatedOn();

	public void setCreatedOn(Date created);

	public String getChangedBy();

	public void setChangedBy(String changer);

	public Date getChangedOn();

	public void setChangedOn(Date changed);    

}
