package com.spire.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import redis.clients.jedis.Jedis;

import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

/**
 * 
 * @author Manikanta
 *
 */

public class DBHelper {

	String dbHost = ReadingServiceEndPointsProperties
			.getServiceEndPoint("DBHOST");
	String user = ReadingServiceEndPointsProperties
			.getServiceEndPoint("DBUSER");
	String password = ReadingServiceEndPointsProperties
			.getServiceEndPoint("DBPASSWORD");
	String schemaName = ReadingServiceEndPointsProperties
			.getServiceEndPoint("DBSCHEMA");

	/**
	 * @param dbHost
	 * @param user
	 * @param password
	 * @param schemaName
	 * @param tableName
	 * @return totalCountInDB
	 * @throws SQLException
	 */
	public void deleteAutomationData(String tableName, String whereCondition) {

		System.out.println("Connecting to DB Host: " + dbHost + " User: "
				+ user + ", Password: " + password + ", SchemaName: "
				+ schemaName + ", TableName: " + tableName);

		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://" + dbHost
					+ ":3306/" + schemaName, user, password);

			stmt = con.createStatement();

			String query = "delete from " + schemaName + "." + tableName
					+ " where " + whereCondition;
			System.out.println(query);
			stmt.execute(query);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void flushRedis() {
		String redisHost = null;
		if (dbHost.equals("192.168.2.156")) {
			redisHost = "192.168.2.69";
		} else {
			redisHost = "192.168.2.75";
		}

		Jedis jedis = new Jedis(redisHost);
		jedis.auth("abc");
		jedis.flushAll();
		Logging.log("Redis is flushed and redisHost is >> " + redisHost);
	}

	public static void main(String args[]) {

		DBHelper obj = new DBHelper();
		obj.flushRedis();
		// obj.deleteAutomationData("role_a", "role_name=\"AutomationTest\"");
	}
}
