package com.spire.crm.biz.authorization.test;

/**
 * @author Manikanta.Y
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.testng.Assert;

import spire.commons.acl.Permission;
import spire.commons.acl.Role;
import spire.crm.labels.biz.entities.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.biz.consumers.AuthorizationServiceConsumer;

public class AuthorizationSanityValidation {

	public static Gson gson = new Gson();
	public static ObjectMapper mapper = new ObjectMapper();
	String userid = ReadingServiceEndPointsProperties
			.getServiceEndPoint("userId");

	public void verifyScenarios(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		AuthorizationServiceConsumer client = new AuthorizationServiceConsumer();
		String roleName = "AutomationRole" + UUID.randomUUID();
		Response response = client.addNewRole(roleName);

		if (response.getStatus() == 200) {
			ResponseEntity createRoleResponse = response
					.readEntity(ResponseEntity.class);

			Assert.assertEquals(200, createRoleResponse.getCode());
			Assert.assertEquals("successfully create [" + roleName + "]",
					createRoleResponse.getMessage());

			data.setData(roleName);
			testCaseredirector(testObject, data);

		} else {
			Assert.fail("Add new role is failed and status code is >> "
					+ response.getStatus());
		}

	}

	private void testCaseredirector(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		String roleName = data.getData();
		Permission permission = null;
		switch (testObject.getTestTitle()) {
		case "verifyCreateRole_Sanity":
			verifyRoleInList(roleName, false, null);
			break;
		case "verifyDeleteRole_Sanity":
			verifydeleteRole(roleName);
			verifyRoleNotInList(roleName);
			break;
		case "verifyCreatePermission_Sanity":
			createPermission();
			break;
		case "verifyAddPermissionToRole_Sanity":
			permission = createPermission();
			addPermissionToRole(roleName, permission.getPermissionName());

			List<Permission> permissions = new ArrayList<Permission>();
			permissions.add(permission);
			verifyRoleInList(roleName, true, permissions);
			break;

		case "verifyremovePermissionfromRole_Sanity":

			permission = createPermission();
			addPermissionToRole(roleName, permission.getPermissionName());

			List<Permission> permissions01 = new ArrayList<Permission>();
			permissions01.add(permission);
			removePermission(roleName, permission.getPermissionName());
			permissionNotunderRole(roleName, permission);
			break;
		case "verifyAddRoleToUser_Sanity":
			addRoleToUser(roleName, userid);
			verifyRoleUnderUser(roleName, userid);
		default:
			break;
		}

	}

	private void verifyRoleUnderUser(String roleName, String userid2)
			throws JsonParseException, JsonMappingException, IOException {

		AuthorizationServiceConsumer client = new AuthorizationServiceConsumer();
		Response response = client.getAllRolesByUserID(userid);

		if (response.getStatus() == 200) {
			String strResponse = response.readEntity(String.class);
			Map<String, Role> getAllRolesResponse = null;

			getAllRolesResponse = mapper.readValue(strResponse,
					new TypeReference<Map<String, Role>>() {
					});
			Role automationRole = getAllRolesResponse.get(roleName);
			Assert.assertNotNull(automationRole,
					"Expected Role is not present in list");

		} else {

			Assert.fail("Get All the roles is failed and status code is >> "
					+ response.getStatus());
		}

	}

	private void addRoleToUser(String roleName, String userid) {

		AuthorizationServiceConsumer client = new AuthorizationServiceConsumer();
		Response response = client.addRoleToUser(userid, roleName);

		if (response.getStatus() == 200) {

			ResponseEntity addPermissionResponse = response
					.readEntity(ResponseEntity.class);
			Logging.log("Response of add role  to user >>> "
					+ gson.toJson(addPermissionResponse));
			Assert.assertEquals(200, addPermissionResponse.getCode());

			Assert.assertEquals("successfully assign [" + roleName + ", "
					+ userid + "]", addPermissionResponse.getMessage());

		} else {

			Assert.fail("add roles to the user is failed and status code is >> "
					+ response.getStatus());
		}

	}

	private void permissionNotunderRole(String roleName, Permission permission)
			throws JsonParseException, JsonMappingException, IOException {

		AuthorizationServiceConsumer client = new AuthorizationServiceConsumer();
		Response response = client.getAllRoles();

		if (response.getStatus() == 200) {
			String strResponse = response.readEntity(String.class);
			Logging.log("getAllRoles Response is   >>>" + strResponse);
			Map<String, Role> getAllRolesResponse = null;

			getAllRolesResponse = mapper.readValue(strResponse,
					new TypeReference<Map<String, Role>>() {
					});
			Role automationRole = getAllRolesResponse.get(roleName);
			Assert.assertNotNull(automationRole,
					"Expected Role is not present in list");
			List<Permission> permissions = automationRole.getPermissions();
			Boolean permissionFlag = true;
			for (Permission permission2 : permissions) {

				if (permission2.getPermissionName().equals(
						permission.getPermissionName())) {
					permissionFlag = false;
				}

			}
			Assert.assertTrue(permissionFlag,
					"Removed permission is present under the role ");

		} else {

			Assert.fail("Get All the roles is failed and status code is >> "
					+ response.getStatus());
		}

	}

	private void removePermission(String roleName, String permissionName) {

		AuthorizationServiceConsumer client = new AuthorizationServiceConsumer();
		Response response = client.removePermissionsfromRole(roleName,
				permissionName);

		if (response.getStatus() == 200) {

			ResponseEntity addPermissionResponse = response
					.readEntity(ResponseEntity.class);
			Logging.log("Response of add permission  to role >>> "
					+ gson.toJson(addPermissionResponse));
			Assert.assertEquals(200, addPermissionResponse.getCode());
			Assert.assertEquals("successfully remove [" + permissionName + ", "
					+ roleName + "]", addPermissionResponse.getMessage());

		} else {
			Assert.fail("Add permission to role is failed and status code is >> "
					+ response.getStatus());
		}

	}

	private void addPermissionToRole(String roleName, String permissionName) {

		AuthorizationServiceConsumer client = new AuthorizationServiceConsumer();
		Response response = client.addPermissionsToRole(roleName,
				permissionName);

		if (response.getStatus() == 200) {

			ResponseEntity addPermissionResponse = response
					.readEntity(ResponseEntity.class);
			Logging.log("Response of add permission  to role >>> "
					+ gson.toJson(addPermissionResponse));
			Assert.assertEquals(200, addPermissionResponse.getCode());
			Assert.assertEquals("successfully assign [" + permissionName + ", "
					+ roleName + "]", addPermissionResponse.getMessage());

		} else {
			Assert.fail("Add permission to role is failed and status code is >> "
					+ response.getStatus());
		}

	}

	private Permission createPermission() throws JsonParseException,
			JsonMappingException, IOException {

		AuthorizationServiceConsumer client = new AuthorizationServiceConsumer();
		String permissionName = "AutomationPermission" + UUID.randomUUID();
		Permission permission = new Permission();
		permission.setPermissionName(permissionName);

		String permissionExpression = "Campaign:Create,Update,View,Delete:*:*";
		permission.setPermissionExpression(permissionExpression);

		Response response = client.addPermissions(permission);

		if (response.getStatus() == 200) {
			ResponseEntity createPermissionResponse = response
					.readEntity(ResponseEntity.class);
			Logging.log("Response of create permission >>> "
					+ gson.toJson(createPermissionResponse));
			Assert.assertEquals(200, createPermissionResponse.getCode());
			Assert.assertEquals("successfully create [" + permissionName + "]",
					createPermissionResponse.getMessage());

			verifyPermissionInList(permission);
		} else {
			Assert.fail("create new permission is failed and status code is >> "
					+ response.getStatus());
		}
		return permission;

	}

	private void verifyPermissionInList(Permission permission)
			throws JsonParseException, JsonMappingException, IOException {

		AuthorizationServiceConsumer client = new AuthorizationServiceConsumer();
		Response response = client.getAllPermissions();

		if (response.getStatus() == 200) {

			String strResponse = response.readEntity(String.class);
			Map<String, Permission> getAllPermissions = mapper.readValue(
					strResponse, new TypeReference<Map<String, Permission>>() {
					});
			Permission createdpermission = getAllPermissions.get(permission
					.getPermissionName());

			Assert.assertEquals(createdpermission.getPermissionName(),
					permission.getPermissionName(),
					"Found discrepancy inPermission name ");
			Assert.assertEquals(createdpermission.getPermissionExpression(),
					permission.getPermissionExpression(),
					"Found discrepancy inPermission expression ");

			Assert.assertEquals("179740ba-3b51-48f8-beba-ebaa8f51b124",
					createdpermission.getFlowId(),
					"Found discrepancy in Flow ID");

		} else {
			Assert.fail("get all permission is failed and status code is >> "
					+ response.getStatus());
		}

	}

	private void verifyRoleNotInList(String roleName)
			throws JsonParseException, JsonMappingException, IOException {

		AuthorizationServiceConsumer client = new AuthorizationServiceConsumer();
		Response response = client.getAllRoles();

		if (response.getStatus() == 200) {
			String strResponse = response.readEntity(String.class);
			Logging.log("getAllRoles Response is   >>>" + strResponse);
			Map<String, Role> getAllRolesResponse = null;

			getAllRolesResponse = mapper.readValue(strResponse,
					new TypeReference<Map<String, Role>>() {
					});
			Role automationRole = getAllRolesResponse.get(roleName);

			System.out.println(automationRole);
			Assert.assertNull(automationRole);
		} else {

			Assert.fail("Get All the roles is failed and status code is >> "
					+ response.getStatus());
		}

	}

	private void verifydeleteRole(String roleName) {

		AuthorizationServiceConsumer client = new AuthorizationServiceConsumer();
		Response response = client.deleteRole(roleName);

		if (response.getStatus() == 200) {
			ResponseEntity deleteRoleResponse = response
					.readEntity(ResponseEntity.class);

			Assert.assertEquals(200, deleteRoleResponse.getCode());
			Assert.assertEquals("successfully deleted [" + roleName + "]",
					deleteRoleResponse.getMessage());
		} else {
			Assert.fail("delete added role is failed and status code is >> "
					+ response.getStatus());
		}

	}

	private void verifyRoleInList(String roleName, Boolean verifyPermissions,
			List<Permission> inPutpermissions) throws JsonParseException,
			JsonMappingException, IOException {

		AuthorizationServiceConsumer client = new AuthorizationServiceConsumer();
		Response response = client.getAllRoles();

		if (response.getStatus() == 200) {
			String strResponse = response.readEntity(String.class);
			Logging.log("getAllRoles Response is   >>>" + strResponse);
			Map<String, Role> getAllRolesResponse = null;

			getAllRolesResponse = mapper.readValue(strResponse,
					new TypeReference<Map<String, Role>>() {
					});
			Role automationRole = getAllRolesResponse.get(roleName);
			Assert.assertEquals(roleName, automationRole.getRoleName(),
					"Found discrepancy in role name ");
			Assert.assertEquals(userid, automationRole.getCreatedBy(),
					"Found discrepancy in Created By");
			Assert.assertEquals(userid, automationRole.getUpdatedBy(),
					"Found discrepancy in Updated By");
			Assert.assertEquals("179740ba-3b51-48f8-beba-ebaa8f51b124",
					automationRole.getFlowId(), "Found discrepancy in Flow ID");
			if (!verifyPermissions) {

				Assert.assertTrue(automationRole.getPermissions().isEmpty(),
						"Found discrepancy in Permissions");
			} else {
				Boolean permissionsFlag = false;
				List<Permission> permissions = automationRole.getPermissions();

				for (Permission inputPermission : inPutpermissions) {

					for (Permission permission : permissions) {
						if (inputPermission.getPermissionName().equals(
								permission.getPermissionName())) {
							permissionsFlag = true;
							Assert.assertEquals(
									inputPermission.getPermissionName(),
									permission.getPermissionName(),
									"Found discrepancy in Permission Name ");
							Assert.assertEquals(userid,
									permission.getCreatedBy(),
									"Found discrepancy in Created By");
							Assert.assertEquals(userid,
									permission.getUpdatedBy(),
									"Found discrepancy in Updated By");
							Assert.assertEquals(
									"179740ba-3b51-48f8-beba-ebaa8f51b124",
									permission.getFlowId(),
									"Found discrepancy in Flow ID");
						}

					}
				}

				Assert.assertTrue(permissionsFlag,
						"Expected Permission not found");

			}

		} else {
			Assert.fail("Get All the roles is failed and status code is >> "
					+ response.getStatus());
		}

	}

	public void tenantAdminSetUP() throws JsonParseException,
			JsonMappingException, IOException {

		AuthorizationServiceConsumer client = new AuthorizationServiceConsumer();
		Response response = client.getAllRoles();

		if (response.getStatus() == 200) {
			String strResponse = response.readEntity(String.class);
			Logging.log("getAllRoles Response is   >>>" + strResponse);
			Map<String, Role> getAllRolesResponse = null;

			getAllRolesResponse = mapper.readValue(strResponse,
					new TypeReference<Map<String, Role>>() {
					});
			Role automationRole = getAllRolesResponse.get("TenantAdmin");
			try {

				if (automationRole.getRoleName() != "TenantAdmin") {

					client.addNewRole("TenantAdmin");

				}
			} catch (NullPointerException e) {

				client.addNewRole("TenantAdmin");
			}

		}

		Response response2 = client.getAllRolesByUserID(userid);

		if (response2.getStatus() == 200) {

			String strResponse = response2.readEntity(String.class);
			Map<String, Role> getAllRolesResponse = null;

			getAllRolesResponse = mapper.readValue(strResponse,
					new TypeReference<Map<String, Role>>() {
					});

			Role automationRole = getAllRolesResponse.get("TenantAdmin");
			if (automationRole == null) {
				client.addRoleToUser(userid, "TenantAdmin");
			}
		}

	}

}
