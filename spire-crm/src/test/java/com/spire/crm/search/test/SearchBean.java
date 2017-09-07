package com.spire.crm.search.test;

import java.util.List;

public class SearchBean {

	private List<String>  SearchCriteria;
	private List<String>  SearchType;
	private String SearchQuery;
	private String SearchAttributeMap;
	private List<String> SearchOperation;
	private List<String>  paranthesis;
	private String freeTextData;
	private String freeTextQuery;
	
	public String getFreeTextData() {
		return freeTextData;
	}
	public void setFreeTextData(String freeTextData) {
		this.freeTextData = freeTextData;
	}
	public String getFreeTextQuery() {
		return freeTextQuery;
	}
	public void setFreeTextQuery(String freeTextQuery) {
		this.freeTextQuery = freeTextQuery;
	}
	public List<String> getSearchCriteria() {
		return SearchCriteria;
	}
	public void setSearchCriteria(List<String> searchCriteria) {
		SearchCriteria = searchCriteria;
	}
	public List<String> getSearchType() {
		return SearchType;
	}
	public void setSearchType(List<String> searchType) {
		SearchType = searchType;
	}
	public String getSearchQuery() {
		return SearchQuery;
	}
	public void setSearchQuery(String searchQuery) {
		SearchQuery = searchQuery;
	}
	public String getSearchAttributeMap() {
		return SearchAttributeMap;
	}
	public void setSearchAttributeMap(String searchAttributeMap) {
		SearchAttributeMap = searchAttributeMap;
	}
	public List<String> getSearchOperation() {
		return SearchOperation;
	}
	public void setSearchOperation(List<String> searchOperation) {
		SearchOperation = searchOperation;
	}
	public List<String> getParanthesis() {
		return paranthesis;
	}
	public void setParanthesis(List<String> paranthesis) {
		this.paranthesis = paranthesis;
	}
	
	
}
