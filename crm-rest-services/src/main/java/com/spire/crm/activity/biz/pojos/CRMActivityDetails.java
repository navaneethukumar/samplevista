package com.spire.crm.activity.biz.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import spire.commons.activitystream.Action;
import spire.commons.activitystream.Actor;
import spire.commons.activitystream.SingleEntity;
import spire.commons.activitystream.Target;

/**
 *  POJO Entity Class for CRM Activity
 * @author Chandan Kumar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "actorId", "actorName", "actorDetail", "action", "objectType", "objectId", "objectDetail",
        "targetId", "targetName", "targetDetail", "createdOn", "modifiedOn"})
public final class CRMActivityDetails<A, T> extends SingleEntity {

	private String id;
  	private Actor<A> actor;
    private Action action;
    private Target<T> target;
    private String activityTime;
    
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
  	/**
	 * @return the actor
	 */
	public Actor<A> getActor() {
		return actor;
	}

	public String getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}

	/**
	 * @param actor the actor to set
	 */
	public void setActor(Actor<A> actor) {
		this.actor = actor;
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
	 * @return the target
	 */
	public Target<T> getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(Target<T> target) {
		this.target = target;
	}

	

	
}
