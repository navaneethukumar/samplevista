package com.spire.crm.createNewBranch;

/**
 * @author Manaswini
 */

import org.testng.annotations.Test;

import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.common.Report;
import com.spire.common.SendEmail;
import com.spire.crawl.helper.CRMTestPlan;
import com.spire.crm.pageUtils.GitPageUtil;

@Test(groups = { "Sanity" }/*, retryAnalyzer = TestRetryAnalyzer.class*/)
public class CreateNewBranchTestPlan extends CRMTestPlan  {
	
	String SourceBranch=null;
	String DestinationBranch=null;
	String emailList=null;
	String userName="raghavender.nagabandi@spire2grow.com";
	String password="lasya279#";
	String repositories=null;
	
	@Test(groups = { "validateCreateNewBranch", "Sanity" })
	public  void validateCreateNewBranch() throws InterruptedException{
		
		SourceBranch=ContextManager.getGlobalContext().getSourceBranchName();
		DestinationBranch=ContextManager.getGlobalContext().getNewBranch();
		repositories=ContextManager.getGlobalContext().getRepositories();
		emailList=ContextManager.getGlobalContext().getEmailToList();
		
		StringBuilder builder=Report.createHeader();
		   
		builder = Report.createBody(builder);
		
		GitPageUtil gitPageUtil= new GitPageUtil(null,false,"http://gitlab.spire2grow.com/");
		initializeDriver(gitPageUtil.driver, null);
		gitPageUtil.login(userName,password);
		
		String[] urls=repositories.split(",");
		for (int i = 0; i < urls.length; i++) {
			
			builder.append("<tr>");
			String repoURL=urls[i];
			gitPageUtil= new GitPageUtil(gitPageUtil.driver,false,repoURL);
			gitPageUtil.createNewBranch(SourceBranch,DestinationBranch);
			Logging.log("New branch created for repository :: "+repoURL);
			builder.append("<td style = \"padding: 6px; border: 1px solid #ccc; text-align: left;\"> "+ (i+1));
			builder.append("<td style = \"padding: 6px; border: 1px solid #ccc; text-align: left;\"> "+ repoURL);
			builder.append("<td style = \"padding: 6px; border: 1px solid #ccc; text-align: left;\"> "+ SourceBranch);
			builder.append("<td style = \"padding: 6px; border: 1px solid #ccc; text-align: left;\"> "+ DestinationBranch);
			builder.append("</tr>");
			
		}
		StringBuilder body = Report.closeTable(builder);	
		SendEmail.sendEmail(emailList, body,"Git New Branch Created");
		
		
	}
}
