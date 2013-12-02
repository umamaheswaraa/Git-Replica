package com.imaginea.gr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="GITR_USER",uniqueConstraints={@UniqueConstraint(columnNames="USER_NAME")})
@NamedQueries({
    @NamedQuery(name="User.getUserbyName", query="select instance from User instance where instance.userName=:userName"),
    @NamedQuery(name="User.getUser", query="select instance from User instance where instance.userName=:userName and instance.password=:password")
})
public class User extends AbstractEntity {

    private static final long serialVersionUID = -8736437986006737649L;
    
    private String userName;
    private String password;
    private String role;
    
    @Column(name="ROLE",nullable=false)    
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    @Column(name="USER_NAME",nullable=false)
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Column(name="PASSWORD",nullable=false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    

}
