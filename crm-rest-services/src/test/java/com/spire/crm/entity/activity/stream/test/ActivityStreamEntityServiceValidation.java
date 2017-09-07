package com.spire.crm.entity.activity.stream.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.testng.Assert;

import spire.commons.activitystream.Activity;
import spire.commons.activitystream.Actor;
import spire.commons.activitystream.Meta;
import spire.commons.activitystream.Meta.Originator;
import spire.commons.activitystream.Meta.OriginatorSubType;
import spire.commons.activitystream.ObjectType;
import spire.commons.activitystream.Target;
import spire.commons.activitystream.TypeFilter;
import spire.commons.activitystream.resources.MetaMapper;
import spire.commons.utils.IdUtils;
import spire.talent.common.beans.CollectionEntity;

import com.spire.base.controller.Logging;
import com.spire.base.helper.WebPageHelper;
import com.spire.common.TestData;
import com.spire.crm.biz.helperpojos.ActivityNew;
import com.spire.crm.restful.entity.consumers.ActivityStreamEntityServiceConsumer;
import com.spire.crm.restful.entity.consumers.CollectionEntityPojo;
import com.spire.crm.restful.entity.consumers.QueryParamPojo;

import crm.activitystream.beans.InputContainer;

/**
 * @author Santosh C
 *
 */
public class ActivityStreamEntityServiceValidation {

	static ActivityStreamEntityServiceConsumer activityStreamEntityServiceConsumer = new ActivityStreamEntityServiceConsumer();
	static Activity createActivityRequest = null;

	/**
	 * createActivity
	 * 
	 * @param data
	 */
	public static void createActivityTest(TestData data, Activity activityRequest) {

		Activity createActivityData = ActivityHelper.createActivityData(data, activityRequest);

		Activity createActivityResponse = activityStreamEntityServiceConsumer.createActivity(data, createActivityData);

		if (data.getData().equals("Response")) {
			// validating from response
			ActivityHelper.validateCreatedActivity(createActivityData, createActivityResponse);
		}

		WebPageHelper.sleep(10000);
		
		if (data.getData().equals("ActivityId")) {
			// validating getByActivityId response
			String activityId = createActivityResponse.getId();

			Activity getByActivityIdResponse = activityStreamEntityServiceConsumer.getActivitiesByActivityId(activityId,
					data);

			ActivityHelper.validateCreatedActivity(createActivityData, getByActivityIdResponse);
		}

		if (data.getData().equals("PersonId")) {
			// validating getByPersonId response
			Activity getByPersonIdResponse = activityStreamEntityServiceConsumer
					.getActivitiesByPerson(createActivityData.getActors().get(0).getId(), "0", "10", data).getItems()
					.iterator().next();
			ActivityHelper.validateCreatedActivity(createActivityData, getByPersonIdResponse);
		}

	}

	/**
	 * Create customActivity[by giving few fields]
	 * 
	 * @param data
	 * @param activityRequest
	 */
	@SuppressWarnings("rawtypes")
	public static void createCustomActivityTest(TestData data, Activity activityRequest) {

		ActivityNew createActivityRequest = new ActivityNew();

		createActivityRequest.setAction(activityRequest.getAction());
		createActivityRequest.setActivityType(activityRequest.getActivityType());

		List<Actor> actors = new ArrayList<Actor>();
		Actor<Object> actor = new Actor<Object>();
		actor.setId(IdUtils.generateID("6001", "6002"));
		actor.setName("Steve");
		actors.add(actor);
		createActivityRequest.setActors(actors);

		if (data.getData().equals("Actor_Object")) {
			spire.commons.activitystream.Object object = new spire.commons.activitystream.Object<>();
			object.setId(IdUtils.generateID("6001", "6002"));
			object.setObjectType(ObjectType.EMAIL);
			// object.setDetail("ObjectDetail");
			createActivityRequest.setObject(object);
		}

		if (data.getData().equals("Actor_Target")) {
			List<Target> targets = new ArrayList<Target>();
			Target<Object> target = new Target<Object>();
			target.setId(IdUtils.generateID("6001", "6002"));
			target.setName("Mark");
			target.setDetail("targetDetail");
			targets.add(target);
			createActivityRequest.setTargets(targets);
		}
		createActivityRequest.setCreatedOn(null);
		createActivityRequest.setModifiedOn(null);

		Activity response = activityStreamEntityServiceConsumer.createActivity(data, createActivityRequest);

		Assert.assertNotNull(response, "Not creating activity,, response id null !!");
		Assert.assertNotNull(response.getId(), "ActivityId is null !!");

		Assert.assertNotNull(response.getAction(), "Showing Action as null in response !!");
		Assert.assertEquals(response.getAction(), createActivityRequest.getAction(),
				"Showing wrong Action in response !!");

		Assert.assertNotNull(response.getActivityType(), "Showing ActivityType as null in response !!");
		Assert.assertEquals(response.getActivityType(), createActivityRequest.getActivityType(),
				"Showing wrong ActivityType !!");

		Assert.assertNotNull(response.getActors(), "Actor field is showing null in response !!");
		Assert.assertEquals(response.getActors().get(0).getId(), createActivityRequest.getActors().get(0).getId(),
				"Showing wrong ActorId !!");
		Assert.assertEquals(response.getActors().get(0).getName(), createActivityRequest.getActors().get(0).getName(),
				"Showing wrong ActorName !!");
		Assert.assertEquals(response.getActors().get(0).getDetail(),
				createActivityRequest.getActors().get(0).getDetail(), "Showing wrong ActorDetail !!");

		if (data.getData().equals("Actor_Object")) {
			Assert.assertNotNull(response.getObject(), "Object field is showing null in response !!");
			Assert.assertEquals(response.getObject().getId(), createActivityRequest.getObject().getId(),
					"Showing wrong ObjectId !!");
			Assert.assertEquals(response.getObject().getObjectType(), createActivityRequest.getObject().getObjectType(),
					"Showing wrong ObjectType !!");
			Assert.assertEquals(response.getObject().getDetail(), createActivityRequest.getObject().getDetail(),
					"Showing wrong ObjectDetail !!");
		}
		if (data.getData().equals("Actor_Target")) {
			Assert.assertNotNull(response.getTargets(), "Target field is showing null in response !!");
			Assert.assertEquals(response.getTargets().get(0).getId(), createActivityRequest.getTargets().get(0).getId(),
					"Showing wrong TargetId !!");
			Assert.assertEquals(response.getTargets().get(0).getName(),
					createActivityRequest.getTargets().get(0).getName(), "Showing wrong TargetName !!");
			Assert.assertEquals(response.getTargets().get(0).getDetail(),
					createActivityRequest.getTargets().get(0).getDetail(), "Showing wrong TargetDetail !!");
		}

	}

	/**
	 * Create an activity and list activities by personId
	 * 
	 * @param data
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void createActivity_List(TestData data, Activity act) {

		int numberOfActivitiesTocreate = 10;

		Activity createActivityRequest = ActivityHelper.createActivityData(data, act);
		String personId = createActivityRequest.getActors().get(0).getId();

		Logging.log("Creating " + numberOfActivitiesTocreate + " activities to candidate: " + personId);

		for (int i = 0; i < numberOfActivitiesTocreate; i++) {
			activityStreamEntityServiceConsumer.createActivity(data, createActivityRequest);
		}

		InputContainer listRequest = new InputContainer();
		List<String> pIds = new ArrayList<String>();
		pIds.add(personId);
		listRequest.setPersonIds(pIds);

		listRequest.setType("ACTION");

		TypeFilter filter = new TypeFilter();

		List<String> criteria = new ArrayList<String>();
		// criteria.add("READ");
		criteria.add(act.getAction().toString());
		filter.setCriteria(criteria);
		listRequest.setFilter(filter);

		QueryParamPojo param = new QueryParamPojo();
		param.setOffset("0");
		param.setLimit(data.getData());

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		CollectionEntityPojo listResponse = activityStreamEntityServiceConsumer.listActivities(param, listRequest,
				data);

		Assert.assertEquals(Integer.parseInt(data.getData()), listResponse.getItems().size(),
				"Showing wrong number of Activities for a person, It should display given numer of limit");

		Assert.assertEquals(numberOfActivitiesTocreate, listResponse.getTotalResults(),
				"Created 10 activities but it is showing wrong number of totalResults !!");

		Activity listActivityResonse = listResponse.getItems().iterator().next();

		ActivityHelper.validateCreatedActivity(createActivityRequest, listActivityResonse);
	}

	/**
	 * Create createBulkActivity
	 * 
	 * @param data
	 * @param act
	 */
	public static void createBulkActivity(TestData data, Activity act) {

		List<Activity> bulkActivityRequest = new ArrayList<Activity>();
		Activity activity1 = ActivityHelper.createActivityData(data, act);
		Activity activity2 = ActivityHelper.createActivityData(data, act);
		Activity activity3 = ActivityHelper.createActivityData(data, act);
		bulkActivityRequest.add(activity1);
		bulkActivityRequest.add(activity2);
		bulkActivityRequest.add(activity3);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		CollectionEntityPojo bulkActivityResponse = activityStreamEntityServiceConsumer
				.createBulkActivity(bulkActivityRequest);
		Collection<Activity> activities = bulkActivityResponse.getItems();

		List<Activity> bulkActivities = new ArrayList<Activity>();
		List<String> createdActivityIds = new ArrayList<String>();

		for (Activity activity : activities) {
			bulkActivities.add(activity);
			createdActivityIds.add(activity.getId());
		}

		Activity activityResponse = new Activity();
		if (data.getData().equals("Response")) {
			activityResponse = activities.iterator().next();

			Assert.assertEquals(3, activities.size(),
					"Showing wrong number of activities in bulk response after creating bulkActivities !!");
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (data.getData().equals("ActivityId")) {
			for (String aid : createdActivityIds) {
				activityResponse = activityStreamEntityServiceConsumer.getActivitiesByActivityId(aid, data);
				Assert.assertEquals(activityResponse.getId(), aid, "Showing wrong ActivityId in response !!");
			}
		}

		if (data.getData().equals("PersonId")) {
			String personId = activity1.getActors().get(0).getId();
			activityResponse = activityStreamEntityServiceConsumer.getActivitiesByPerson(personId, "0", "10", data)
					.getItems().iterator().next();
		}
		Assert.assertEquals(activityResponse.getAction(), act.getAction(), "Showing wrong Action !!");
		Assert.assertEquals(activityResponse.getActivityType(), act.getActivityType(),
				"Showing wrong ActivityType in response !!");

		for (int i = 0; i < bulkActivities.size(); i++) {
			ActivityHelper.validateCreatedActivity(bulkActivityRequest.get(i), bulkActivities.get(i));
		}

	}

	/**
	 * Create Activity and Search for some text in Activity
	 * 
	 * @param data
	 * @param act
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void list_SearchText(TestData data, Activity act) {

		Activity createActivityData = ActivityHelper.createActivityData(data, act);

		Activity createdActivity = activityStreamEntityServiceConsumer.createActivity(data, createActivityData);

		String activityId = createdActivity.getId();

		InputContainer listRequest = new InputContainer();
		List<String> pIds = new ArrayList<String>();
		pIds.add(createActivityData.getActors().get(0).getId());
		listRequest.setPersonIds(pIds);
		listRequest.setType("ACTION");
		// setting some text to search from created activity
		if (data.getData().equals("Valid")) {
			listRequest.setSearchText(createActivityData.getActors().get(0).getDetail().toString());
		}
		if (data.getData().equals("Invalid")) {
			listRequest.setSearchText("abcde");
		}

		TypeFilter filter = new TypeFilter();

		List<String> criteria = new ArrayList<String>();
		criteria.add(act.getAction().toString());
		filter.setCriteria(criteria);
		listRequest.setFilter(filter);

		QueryParamPojo param = new QueryParamPojo();
		param.setOffset("0");
		param.setLimit("10");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		CollectionEntityPojo listResponse = activityStreamEntityServiceConsumer.listActivities(param, listRequest,
				data);
		if (!data.getException().equals("EXCEPTION")) {
			Assert.assertEquals(activityId, listResponse.getItems().get(0).getId(), "SearchText is not working !!");
		}
	}

	/**
	 * invalidSearch[personId, activityId]
	 * 
	 * @param data
	 * @param act
	 */
	public static void invalidSearch(TestData data, Activity act) {

		if (data.getData().equals("ActivityId")) {
			activityStreamEntityServiceConsumer.getActivitiesByActivityId(IdUtils.generateID("6000", "6002"), data);
		}

		if (data.getData().equals("PersonId")) {
			activityStreamEntityServiceConsumer.getActivitiesByPerson(IdUtils.generateID("6000", "6002"), "0", "10",
					data);
		}
	}

	/**
	 * invalidSearch[personId, activityId]
	 * 
	 * @param data
	 * @param act
	 */
	public static void createActivity_Meta(TestData data, Activity activityRequest) {

		Activity createActivityData = ActivityHelper.createActivityData(data, activityRequest);
		Meta meta = new Meta();
		if (data.getData().equals("SYSTEM")) {
			meta.setOriginator(Originator.SYSTEM);
		}
		if (data.getData().equals("USER")) {
			meta.setOriginator(Originator.USER);
		}
		createActivityData.setMeta(meta);

		Activity activity = activityStreamEntityServiceConsumer.createActivity(data, createActivityData);

		Assert.assertNotNull(activity.getId(), "ActivityId giving null in Response!!");
		if (data.getData().equals("null")) {
			Assert.assertNotNull(activity.getMeta().getOriginator(),
					"Originator is showing null in CreateActivity response");
			Assert.assertEquals(activity.getMeta().getOriginator().toString(), "USER",
					"By default, activity is not creating with Originator as 'USER'");
		}
		if (data.getData().equals("USER")) {
			Assert.assertNotNull(activity.getMeta().getOriginator(),
					"Originator is showing null in CreateActivity response");
			Assert.assertEquals(activity.getMeta().getOriginator().toString(), "USER",
					"Activity created Originator as USER, but it is creared with Originator as "
							+ activity.getMeta().getOriginator());
		}
		if (data.getData().equals("SYSTEM")) {
			Assert.assertNotNull(activity.getMeta().getOriginator(),
					"Originator is showing null in CreateActivity response");
			Assert.assertEquals(activity.getMeta().getOriginator().toString(), "SYSTEM",
					"Activity created Originator as SYSTEM, but it is creared with Originator as "
							+ activity.getMeta().getOriginator());

		}
	}

	public static void listActivitiesByMeta(TestData data, Activity act) {
		Activity createActivityData = ActivityHelper.createActivityData(data, act);
		Meta meta = new Meta();
		meta.setOriginator(Originator.SYSTEM);
		meta.setOriginatorSubType(OriginatorSubType.CAMPAIGN);
		createActivityData.setMeta(meta);
		Activity activity = activityStreamEntityServiceConsumer.createActivity(data, createActivityData);

		WebPageHelper.sleep(10000);
		String activityId = activity.getId();

		MetaMapper metaMapper = new MetaMapper();
		List<Originator> originator = new ArrayList<Originator>();
		originator.add(Originator.SYSTEM);
		metaMapper.setOriginator(originator);

		List<OriginatorSubType> originatorSubType = new ArrayList<OriginatorSubType>();
		originatorSubType.add(OriginatorSubType.CAMPAIGN);
		metaMapper.setOriginatorSubType(originatorSubType);
		CollectionEntity<Activity> metaActivities = activityStreamEntityServiceConsumer
				.listActivitiesByMeta(metaMapper);

		List<String> activityIds = new ArrayList<String>();
		Collection<Activity> activities = metaActivities.getItems();
		for (Activity activity2 : activities) {
			activityIds.add(activity2.getId());
		}

		Assert.assertTrue(activityIds.contains(activityId), "Not listing meta activity !!");
	}

	public static void validateUpdateActivity(TestData data, Activity act) {

		Activity createActivityData = ActivityHelper.createActivityData(data, act);
		Meta meta = new Meta();
		meta.setOriginator(Originator.SYSTEM);
		meta.setOriginatorSubType(OriginatorSubType.CAMPAIGN);
		createActivityData.setMeta(meta);
		Activity activity = activityStreamEntityServiceConsumer.createActivity(data, createActivityData);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		activity.setReadUnreadStatus("READ");
		Activity updatedResponse = activityStreamEntityServiceConsumer.updateActivity(activity);

		Assert.assertEquals(updatedResponse.getReadUnreadStatus(), "READ", "ActivityReadUnRead status not updating!!");
		Assert.assertEquals(activity.getAction(), updatedResponse.getAction(),
				"Action name showing different after updating activity");
		Assert.assertEquals(activity.getActivityType(), updatedResponse.getActivityType(),
				"ActivityType name showing different after updating activity");
		Assert.assertEquals(activity.getObject(), updatedResponse.getObject(),
				"Object name showing different after updating activity");
	}

}