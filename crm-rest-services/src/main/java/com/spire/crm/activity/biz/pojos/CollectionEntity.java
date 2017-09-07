package com.spire.crm.activity.biz.pojos;

import java.util.ArrayList;
import java.util.Collection;



/**
 * This represents a collection of entities 
 * 
 *
 * @param <T> Entity
 */
public class CollectionEntity<T>  {

	
	private Collection<T> items;

	/**
	 * @return the items
	 */
	public Collection<T> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(Collection<T> items) {
		this.items = items;
	}
	/**
	 * Add a new item to the collection
	 * @param item
	 */
	public void addItem(T item) {
		
		if(item != null) {
			if(items == null) {
				items = new ArrayList<T>();
			}
			items.add(item);
		}
	}
	
	
	
	
}