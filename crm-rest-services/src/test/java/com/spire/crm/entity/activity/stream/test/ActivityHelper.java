package com.spire.crm.entity.activity.stream.test;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import com.google.gson.Gson;
import com.spire.common.TestData;
import com.spire.crm.biz.helperpojos.ActivityNew;
import com.spire.crm.restful.entity.consumers.ActivityStreamEntityServiceConsumer;

import spire.commons.activitystream.Activity;
import spire.commons.activitystream.Actor;
import spire.commons.activitystream.ObjectType;
import spire.commons.activitystream.Target;
import spire.commons.utils.IdUtils;

public class ActivityHelper {
	static Gson gson = new Gson();

	static ActivityStreamEntityServiceConsumer activityStreamEntityServiceConsumer = new ActivityStreamEntityServiceConsumer();
	static Activity activityRequest = new Activity();

	/**
	 * Create Activity
	 * 
	 * @return activityRequest
	 */
	@SuppressWarnings("rawtypes")
	public static Activity createActivityData(TestData data, Activity activityRequest) {

		ActivityNew createActivityRequest = new ActivityNew();
		try {
			createActivityRequest.setAction(activityRequest.getAction());
		} catch (NullPointerException e) {
			createActivityRequest.setAction(null);
		}
		try {
			createActivityRequest.setActivityType(activityRequest.getActivityType());
		} catch (NullPointerException e) {
			createActivityRequest.setActivityType(null);
		}

		createActivityRequest.setDescription("Communication type");

		List<Actor> actors = new ArrayList<Actor>();
		Actor<Object> actor = new Actor<Object>();
		actor.setId(IdUtils.generateID("6001", "6002"));
		actor.setName("Steve");
		actor.setDetail("actorDetail");
		actors.add(actor);
		createActivityRequest.setActors(actors);
		try {
			createActivityRequest.setAction(activityRequest.getAction());
		} catch (Exception e) {
			createActivityRequest.setAction(null);
		}
		spire.commons.activitystream.Object object = new spire.commons.activitystream.Object<>();
		object.setId(IdUtils.generateID("6001", "6002"));
		object.setObjectType(ObjectType.EMAIL);
		// object.setDetail("ObjectDetail");
		createActivityRequest.setObject(object);

		List<Target> targets = new ArrayList<Target>();
		Target<Object> target = new Target<Object>();
		target.setId(IdUtils.generateID("6001", "6002"));
		target.setName("Mark");
		target.setDetail("targetDetail");
		targets.add(target);
		createActivityRequest.setTargets(targets);
		createActivityRequest.setCreatedBy("Spire");
		createActivityRequest.setCreatedOn(null);
		createActivityRequest.setModifiedOn(null);
		return createActivityRequest;
	}

	/**
	 * Create customActivity[by giving few fields]
	 * 
	 * @param data
	 * @param activityRequest
	 * @return createActivityRequest
	 */
	@SuppressWarnings("rawtypes")
	public static Activity createCustomActivity(TestData data, Activity activityRequest) {
		ActivityNew createActivityRequest = new ActivityNew();

		createActivityRequest.setAction(activityRequest.getAction());
		createActivityRequest.setActivityType(activityRequest.getActivityType());
		createActivityRequest.setDescription("Communication type");

		List<Actor> actors = new ArrayList<Actor>();
		Actor<Object> actor = new Actor<Object>();
		actor.setId(IdUtils.generateID("6001", "6002"));
		actor.setName("Steve");
		actor.setDetail("actorDetail");
		actors.add(actor);
		createActivityRequest.setActors(actors);
		createActivityRequest.setAction(activityRequest.getAction());

		spire.commons.activitystream.Object object = new spire.commons.activitystream.Object<>();
		object.setId(IdUtils.generateID("6001", "6002"));
		object.setObjectType(ObjectType.EMAIL);
		// object.setDetail("ObjectDetail");
		createActivityRequest.setObject(object);

		List<Target> targets = new ArrayList<Target>();
		Target<Object> target = new Target<Object>();
		target.setId(IdUtils.generateID("6001", "6002"));
		target.setName("Mark");
		target.setDetail("targetDetail");
		targets.add(target);
		createActivityRequest.setTargets(targets);
		createActivityRequest.setCreatedBy("Spire");

		createActivityRequest.setCreatedOn(null);
		createActivityRequest.setModifiedOn(null);
		return createActivityRequest;
	}

	/**
	 * Validate CreateActivity
	 * 
	 * @param request
	 * @param response
	 */
	public static void validateCreatedActivity(Activity request, Activity response) {

		Assert.assertNotNull(response, "Not creating activity,, response id null !!");
		Assert.assertNotNull(response.getId(), "ActivityId is null !!");

		Assert.assertNotNull(response.getAction(), "Showing Action as null in response !!");
		Assert.assertEquals(response.getAction(), request.getAction(), "Showing wrong Action in response !!");

		Assert.assertNotNull(response.getActivityType(), "Showing ActivityType as null in response !!");
		Assert.assertEquals(response.getActivityType(), request.getActivityType(), "Showing wrong ActivityType !!");

		Assert.assertNotNull(response.getCreatedBy(), "Showing CreatedBy as null in response !!");
		Assert.assertEquals(response.getCreatedBy(), request.getCreatedBy(), "Showing wrong CreatedBy !!");

		Assert.assertNotNull(response.getActors(), "Actor field is showing null in response !!");
		Assert.assertEquals(response.getActors().get(0).getId(), request.getActors().get(0).getId(),
				"Showing wrong ActorId !!");
		Assert.assertEquals(response.getActors().get(0).getName(), request.getActors().get(0).getName(),
				"Showing wrong ActorName !!");
		Assert.assertEquals(response.getActors().get(0).getDetail(), request.getActors().get(0).getDetail(),
				"Showing wrong ActorDetail !!");

		Assert.assertNotNull(response.getObject(), "Object field is showing null in response !!");
		Assert.assertEquals(response.getObject().getId(), request.getObject().getId(), "Showing wrong ObjectId !!");
		Assert.assertEquals(response.getObject().getObjectType(), request.getObject().getObjectType(),
				"Showing wrong ObjectType !!");
		Assert.assertEquals(response.getObject().getDetail(), request.getObject().getDetail(),
				"Showing wrong ObjectDetail !!");

		Assert.assertNotNull(response.getTargets(), "Target field is showing null in response !!");
		Assert.assertEquals(response.getTargets().get(0).getId(), request.getTargets().get(0).getId(),
				"Showing wrong TargetId !!");
		Assert.assertEquals(response.getTargets().get(0).getName(), request.getTargets().get(0).getName(),
				"Showing wrong TargetName !!");
		Assert.assertEquals(response.getTargets().get(0).getDetail(), request.getTargets().get(0).getDetail(),
				"Showing wrong TargetDetail !!");
	}

}
