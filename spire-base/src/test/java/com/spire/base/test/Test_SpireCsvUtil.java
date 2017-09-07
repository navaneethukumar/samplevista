package com.spire.base.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.TestPlan;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;

/**
 * Created by Vinay Kumar on 26-05-2015.
 */
public class Test_SpireCsvUtil extends TestPlan{

    @DataProvider(name = "candidateInfo")
    public static Iterator<Object[]> getCandidateInfo(Method method) {
        Iterator<Object[]> objectsFromCsv =null;
        try{
        String fileName = "Test.csv";
        LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
        LinkedHashMap<String,String> methodFilter=new LinkedHashMap<String, String>();
        methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
        
        entityClazzMap.put("SpireTestObject", SpireTestObject.class);
        entityClazzMap.put("CandidateInfo", CandidateInfo.class);

            objectsFromCsv= SpireCsvUtil.getObjectsFromCsv(Test_SpireCsvUtil.class, entityClazzMap, fileName, null,methodFilter);
        }catch (Exception e){
            e.printStackTrace();
        }
        return objectsFromCsv;
    }


    @Test(groups="test",dataProvider = "candidateInfo")
    public void verifyCandidateInfo(String empId, String empName, SpireTestObject testObject, CandidateInfo candidateInfo){
        try{
        System.out.println(testObject.getTestCaseId() + " , " + testObject.getTestTitle());
        System.out.println("" + candidateInfo);
        System.out.println(empId + " " + empName);}
        catch (Exception e ){
            e.printStackTrace();
        }
    }



}
