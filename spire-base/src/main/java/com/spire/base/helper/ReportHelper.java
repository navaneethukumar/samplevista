package com.spire.base.helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.spire.base.bean.ReportBean;
import com.spire.base.database.DBModule;

public class ReportHelper {
	
	public static void insertData(List<ReportBean> resultList){
		
		Connection conncetion=null;
		
		try {
			
			conncetion = DBModule.getStagingConnection();
			
			int productId= getProductId(conncetion);
			int suiteId=getSuiteId(conncetion, productId);
			insertTestResults(resultList, productId, suiteId, conncetion);	
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(conncetion!=null)
					conncetion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		
	}
	
	public static void insertTestResults(List<ReportBean> resultList,int productId,int suiteId,Connection conncetion) throws Exception{
		
		Statement statement = conncetion.createStatement();
		
		for (int i=0;i<resultList.size();i++) {
			
			ReportBean report=resultList.get(i);
			String query="INSERT INTO TEST_DETAILS (ID,SUITE_ID,TEST_NAME,TEST_TYPE,TOTAL_TESTCASES,PASSED_TESTS,FAILED_TESTS,"
					+ "PASS_PERCENTAGE,TEST_SKIPPED,TIME_TAKEN) VALUES (null, "+suiteId+", '"+report.getTestName()+"', '"+
					report.getTestType()+"', "+report.getTotalTestCases()+", "+report.getPassedTestCases()+", "+report.getFailedTestCases()+
					", '"+report.getPassePercentage()+"', "+report.getTestSkipped()+", "+report.getTimeTaken()+")";
					
			statement.addBatch(query);
		}
		statement.executeBatch();
		statement.close();
		
	}
	
	public static int getProductId(Connection conncetion) throws Exception{
	
		ResultSet rs=conncetion.createStatement().executeQuery("select ID from PRODUCT_DETAILS where name = 'crm'");
		
		while (rs.next()) {
			return rs.getInt(1);
		}
		
		return -1;
		
	}
	
	public static int getSuiteId(Connection conncetion,int productId) throws Exception{
		
		conncetion.createStatement().execute("INSERT INTO SUITE_DETAILS VALUES (NULL, "+productId+");");
		
		ResultSet rs=conncetion.createStatement().executeQuery("select max(ID) from suite_details");
		
		while (rs.next()) {
			return rs.getInt(1);
		}
		
		return -1;
			
	}
	
	public static void main(String[] args) throws Exception {

		
		List<ReportBean> resultList = new ArrayList<ReportBean>();
		
		for(int i=0;i<5;i++){
			ReportBean report = new ReportBean();
			
			report.setExcludedGroups("x");
			report.setTestName("raghav");
			report.setTestType("SANITY");
			report.setTotalTestCases(10);
			report.setPassedTestCases(4);
			report.setFailedTestCases(6);
			report.setPassePercentage("60");
			report.setTestSkipped(0);
			report.setTimeTaken(1250);
			
			resultList.add(report);
		}
		
		insertData(resultList);
		
	}
	
	

}
