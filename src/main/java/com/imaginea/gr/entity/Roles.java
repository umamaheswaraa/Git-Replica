package com.imaginea.gr.entity;

public enum Roles {

	ADMIN("Create,Update,Read,Delete"), GUEST("Read"), USER("Create,Update,Read");

	private String value;

	private Roles(String value){
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return this.value;
	}

}
