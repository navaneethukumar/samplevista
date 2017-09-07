package com.spire.crm.activity.biz.pojos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import spire.commons.activitystream.Action;
import spire.commons.activitystream.ActivityType;
import spire.commons.activitystream.Actor;
import spire.commons.activitystream.Target;

/**
 *  POJO Entity Class for Activity
 * @author siddharth.kumar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "activityType", "description", "actors", "action", "object", "targets", "createdBy", "modifiedBy", "createdOn", "modifiedOn", "links" })
public class Activity {

    private String id;
    private ActivityType activityType;
    private String description;
    private List<Actor> actors;
    private Action action;
    private Object object;
    private List<Target> targets;
    private String createdBy;
    private String modifiedBy;
  /*  private ZonedDateTime createdOn;
    private ZonedDateTime modifiedOn;*/
    
   /* public Activity() {
    	
    	ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneOffset.UTC);
    	this.createdOn = currentDateTime; 
    	this.modifiedOn = currentDateTime;
    }*/

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the activityType
	 */
	public ActivityType getActivityType() {
		return activityType;
	}
	/**
	 * @param activityType the activityType to set
	 */
	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the actors
	 */
	public List<Actor> getActors() {
		return actors;
	}
	/**
	 * @param actors the actors to set
	 */
	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
	}
	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}
	/**
	 * @param object the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}
	/**
	 * @return the targets
	 */
	public List<Target> getTargets() {
		return targets;
	}
	/**
	 * @param targets the targets to set
	 */
	public void setTargets(List<Target> targets) {
		this.targets = targets;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the createdOn
	 */
	/*public ZonedDateTime getCreatedOn() {
		return createdOn;
	}
	*//**
	 * @param createdOn the createdOn to set
	 *//*
	public void setCreatedOn(ZonedDateTime createdOn) {
		this.createdOn = createdOn;
	}
	*//**
	 * @return the modifiedOn
	 *//*
	public ZonedDateTime getModifiedOn() {
		return modifiedOn;
	}
	*//**
	 * @param modifiedOn the modifiedOn to set
	 *//*
	public void setModifiedOn(ZonedDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}*/
    
	/**
	 * Add an actor to the list of actors associated to the activity
	 * @param actor
	 */
	public void addActor(Actor actor) {
		
		if(actors == null) {
			actors = new ArrayList<Actor>();
		}
		
		actors.add(actor);
	}
	
	/**
	 * Add a target to the list of targets associated to the activity
	 * @param target
	 */
	public void addTarget(Target target) {
		
		if(targets == null) {
			targets = new ArrayList<Target>();
		}
		
		targets.add(target);
	}
}
