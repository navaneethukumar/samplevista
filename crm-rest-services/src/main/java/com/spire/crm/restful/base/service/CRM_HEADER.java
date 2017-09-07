package com.spire.crm.restful.base.service;

public enum CRM_HEADER {

	TENANTID("tenantId"), REALMNAME("RealmName"), USERID("UserId"),AUTHORIZATION("Authorization"),CONTENTTYPE("Content-Type"),ACCEPT("Accept");

	private String headerName;

	CRM_HEADER(String headerName) {
		this.headerName = headerName;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

}
