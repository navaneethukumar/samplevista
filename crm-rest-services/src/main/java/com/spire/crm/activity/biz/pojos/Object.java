package com.spire.crm.activity.biz.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Object {

	private String id;
	@JsonProperty("objectType")
	private String objecttype;
	private Detail detail;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setObjecttype(String objecttype) {
		this.objecttype = objecttype;
	}

	public String getObjecttype() {
		return objecttype;
	}

	public void setDetail(Detail detail) {
		this.detail = detail;
	}

	public Detail getDetail() {
		return detail;
	}

}

