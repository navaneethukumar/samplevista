package com.spire.crm.restful.biz.consumers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import spire.commons.acl.Permission;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

/**
 * 
 * http://192.168.2.75:8182/spire-biz/api/v1/swagger.json
 * 
 * @author Manikanta
 *
 */
public class AuthorizationServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(AuthorizationServiceConsumer.class);
	Response response = null;
	String endPointURL = ReadingServiceEndPointsProperties
			.getServiceEndPoint("SPIRE_BIZ")+"/acl";
	public static Gson gson = new Gson();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>> http://192.168.2.69:8182/spire-biz/api/v1/acl
	 * 
	 * @param URL
	 */
	public AuthorizationServiceConsumer() {
	}

	public AuthorizationServiceConsumer(String username, String password) {
		getUserToken(username, password);
	}

	/**
	 * get all Roles
	 * 
	 * @return
	 */
	public Response getAllRoles() {
		Logging.log("get All Roles");
		String serviceEndPoint = this.endPointURL + "/roles";
		Logging.log("endPointURL  >>>" + endPointURL);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * Add New Role
	 * 
	 * @return
	 */
	public Response addNewRole(String roleName) {
		Logging.log(" Add new Role >>> " + roleName);
		String serviceEndPoint = this.endPointURL + "/roles/" + roleName;
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, DEFAULT_HEADERS,
				Entity.entity(roleName, MediaType.APPLICATION_JSON));
		return response;
	}

	/**
	 * Add delete Role
	 * 
	 * @return
	 */
	public Response deleteRole(String roleName) {
		Logging.log(" Delete existing Role >>> " + roleName);
		String serviceEndPoint = this.endPointURL + "/roles/" + roleName;
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Response response = executeDELETE(serviceEndPoint, DEFAULT_HEADERS);
		return response;
	}

	/**
	 * Add New Permissions
	 * 
	 * @return
	 */
	public Response addPermissions(Permission permission) {
		Logging.log(" add Permissions>>> " + gson.toJson(permission));
		String serviceEndPoint = this.endPointURL + "/permissions";
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, DEFAULT_HEADERS,
				Entity.entity(permission, MediaType.APPLICATION_JSON));
		return response;
	}

	/**
	 * get all Permissions
	 * 
	 * @return
	 */
	public Response getAllPermissions() {
		Logging.log("Get ALl the Permissions ");
		String serviceEndPoint = this.endPointURL + "/permissions";
		Logging.log("endPointURL  >>>" + endPointURL);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * Add delete Permission
	 * 
	 * @return
	 */
	public Response deletePermission(String permissionName) {
		Logging.log(" Delete existing permissions >>> " + permissionName);
		String serviceEndPoint = this.endPointURL + "/permissions/"
				+ permissionName;
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Response response = executeDELETE(serviceEndPoint, DEFAULT_HEADERS);
		return response;
	}

	/**
	 * Add Permissions to Role
	 * 
	 * @return
	 */
	public Response addPermissionsToRole(String roleName, String permissionsName) {
		Logging.log(" add Permissions to Role >>> " + roleName);
		String serviceEndPoint = this.endPointURL + "/roles/" + roleName
				+ "/permissions/" + permissionsName;
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, DEFAULT_HEADERS,
				Entity.entity(roleName, MediaType.APPLICATION_JSON));
		return response;
	}

	/**
	 * Add remove Permission from role
	 * 
	 * @return
	 */
	public Response removePermissionsfromRole(String roleName,
			String permissionName) {
		Logging.log(" remove Permissions from Role >>> " + permissionName);

		String serviceEndPoint = this.endPointURL + "/roles/" + roleName
				+ "/permissions/" + permissionName;
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Response response = executeDELETE(serviceEndPoint, DEFAULT_HEADERS);
		return response;
	}

	/**
	 * get all Permissions
	 * 
	 * @return
	 */
	public Response getAllPermissionsByUserID(String userID) {
		Logging.log("get All Permissions By User ID");
		String serviceEndPoint = this.endPointURL + "/users" + userID
				+ "/Permissions";
		Logging.log("endPointURL  >>>" + endPointURL);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * Add Role to user
	 * 
	 * @return
	 */
	public Response addRoleToUser(String userId,String roleName) {
		Logging.log(" add Permissions to Role >>> " + roleName);
		String serviceEndPoint = this.endPointURL + "/users/"+userId+"/roles/"+roleName;
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, DEFAULT_HEADERS,
				Entity.entity(null, MediaType.APPLICATION_JSON));
		return response;
	}

	/**
	 * remove Role to user
	 * 
	 * @return
	 */
	public Response removeRoleToUser(String roleName) {
		Logging.log(" add Permissions to Role >>> " + roleName);
		String serviceEndPoint = this.endPointURL + "/users/";
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, DEFAULT_HEADERS,
				Entity.entity(roleName, MediaType.APPLICATION_JSON));
		return response;
	}

	/**
	 * get all roles
	 * 
	 * @return
	 */
	public Response getAllRolesByUserID(String userID) {
		Logging.log("get All Roles By User ID");
		String serviceEndPoint = this.endPointURL + "/users/" + userID
				+ "/roles";
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

}
