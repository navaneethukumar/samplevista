package com.spire.common;

public class QueryParamPojo {

	String fromDate;
	String toDate;
	String offset;
	String limit;
	boolean summary;
	String sortingFlag;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean isSummary() {
		return summary;
	}

	public void setSummary(boolean summary) {
		this.summary = summary;
	}

	public String getSortingFlag() {
		return sortingFlag;
	}

	public void setSortingFlag(String sortingFlag) {
		this.sortingFlag = sortingFlag;
	}

	public String getEndPoint() {

		String queryParams = null;
		boolean isFirstQueryParam = true;

		if (fromDate != null)
			if (isFirstQueryParam) {
				queryParams = "fromDate=" + fromDate;
				isFirstQueryParam = false;
			} else {
				queryParams += "&fromDate=" + fromDate;
			}

		if (toDate != null)
			if (isFirstQueryParam) {
				queryParams = "toDate=" + toDate;
				isFirstQueryParam = false;
			} else {
				queryParams += "&toDate=" + toDate;
			}

		if (offset != null)
			if (isFirstQueryParam) {
				queryParams = "offset=" + offset;
				isFirstQueryParam = false;
			} else {
				queryParams += "&offset=" + offset;
			}

		if (limit != null)
			if (isFirstQueryParam) {
				queryParams = "limit=" + limit;
				isFirstQueryParam = false;
			} else {
				queryParams += "&limit=" + limit;
			}
		if (summary != false)
			if (isFirstQueryParam) {
				queryParams = "summary=" + summary;
				isFirstQueryParam = false;
			} else {
				queryParams += "&summary=" + summary;
			}
		if (sortingFlag != null)
			if (isFirstQueryParam) {
				queryParams = "sortingFlag=" + sortingFlag;
				isFirstQueryParam = false;
			} else {
				queryParams += "&sortingFlag=" + sortingFlag;
			}

		return queryParams;

	}

	public static void main(String[] args) {

		QueryParamPojo pojo = new QueryParamPojo();
		pojo.setFromDate("21-05-1991");
		pojo.setToDate("21-01-2016");
		pojo.setLimit("10");
		pojo.setOffset("0");
		pojo.setSortingFlag("true");
		pojo.setSummary(true);

		System.out.println(pojo.getEndPoint());

	}

}
