package com.spire.crm.activity.biz.pojos;

import java.util.List;

import spire.commons.activitystream.SearchCriteria;
import spire.commons.activitystream.TypeFilter;

public class InputContainer extends SearchCriteria {
	
	private List<String> personIds;
	private String type;
	private TypeFilter filter;
	/**
	 * @return the personIds
	 */
	public List<String> getPersonIds() {
		return personIds;
	}
	/**
	 * @param personIds the personIds to set
	 */
	public void setPersonIds(List<String> personIds) {
		this.personIds = personIds;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the filter
	 */
	public TypeFilter getFilter() {
		return filter;
	}
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(TypeFilter filter) {
		this.filter = filter;
	}
}
