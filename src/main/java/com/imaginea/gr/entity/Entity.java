package com.imaginea.gr.entity;

import java.util.Date;

public interface Entity {

    Long getPkey();

    void setPkey(Long pkey);
    
    Boolean getIsDeleted();
    
    void setIsDeleted(Boolean deleted);
    
    String getCreatedBy();

    void setCreatedBy(String creator);
    
    Date getCreatedOn();

    void setCreatedOn(Date created);

    String getChangedBy();

    void setChangedBy(String changer);

    Date getChangedOn();

    void setChangedOn(Date changed);    

}
