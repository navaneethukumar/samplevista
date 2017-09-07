package com.spire.crm.restful.entity.consumers;

import java.util.List;

import spire.commons.activitystream.Activity;

public class CollectionEntityPojo {

	private int totalResults;
	private boolean hasMore;
	private List<Activity> items;
	private List<Links> links;

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	public boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}

	public void setItems(List<Activity> items) {
		this.items = items;
	}

	public List<Activity> getItems() {
		return items;
	}

	public void setLinks(List<Links> links) {
		this.links = links;
	}

	public List<Links> getLinks() {
		return links;
	}

}