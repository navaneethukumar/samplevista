package com.spire.talent.consumers;

import javax.ws.rs.core.Response;

import org.testng.Assert;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;

import spire.commons.userservice.bean.LoginRequestBean;
import spire.commons.userservice.bean.LoginResponseBean;

public class UserHelper {
	
	
	public static String userId = "tester@logica.com";
    public static String password = "spire@123";
    public static Gson gson = new Gson();
    public static UserLoginEntityServiceConsumer userLoginEntityServiceConsumer = new UserLoginEntityServiceConsumer();
	public static void authentication(){
		
		LoginRequestBean loginRequestBean = new LoginRequestBean();
	
		
		loginRequestBean.setUserId(userId);
		loginRequestBean.setPassword(password);
		Response response = userLoginEntityServiceConsumer.getToken(loginRequestBean);
		
		if(response.getStatus()==200){
			LoginResponseBean loginResponse = response.readEntity(LoginResponseBean.class);
						
			Logging.log("userId >>>>"+loginResponse.getUserId());
			Logging.log("tenantId  >>>>"+loginResponse.getTenantId());
			Logging.log("realmName  >>>>"+loginResponse.getRealmName());
			Logging.log("Authorization  >>>>"+loginResponse.getTokenId());
			
			
			
		}else{
			Assert.fail("Authemmntication fail and status code is "+response.getStatus());
		}
		
	}
	
	public static void main(String[] args){
		
		authentication();
		
	}

}
