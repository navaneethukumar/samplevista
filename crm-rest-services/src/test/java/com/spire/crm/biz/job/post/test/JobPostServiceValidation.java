package com.spire.crm.biz.job.post.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import org.testng.Assert;

import spire.commons.config.entities.CollectionEntity;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.crm.jobpost.core.CandidateResponseInfo;
import spire.crm.jobpost.core.Company;
import spire.crm.jobpost.core.JobLocation;
import spire.crm.jobpost.core.JobPost;
import spire.talent.common.beans.Link;
import spire.talent.common.beans.Link.LinkType;
import spire.talent.entity.demandservice.beans.ReqSkillMapBean;

import com.spire.base.controller.ContextManager;

public class JobPostServiceValidation {
	private static Logger logger = LoggerFactory.getLogger(JobPostServiceValidation.class);
	static String SERVICE_NAME = (String) ContextManager.getGlobalContext().getAttribute("SERVICE_NAME");

	public static void validate_VerifyGetAllServices(CollectionEntity<String> collectionEntity) {
		try {
			JobPost jobPost = new JobPost();
			jobPost.setJobTitle("");
			jobPost.setJobDescriptionUrl("www.spire2grow.com");
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyGetAllServices -> failed", e);
		}
	}

	public static JobPost getJobPostDetails() throws ParseException {
		Short open = 0;
		int compen = 10;
		logger.info("Creating jobpost object --");
		JobPost jobDetails = new JobPost();
		jobDetails.setJobTitle("Software Enggs");
		
		jobDetails.setTotalOpenPositions(open);
		jobDetails.setJobDescriptionUrl("www.spire2grow.com");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date formatedDate = sdf.parse("2013-09-29");
		java.sql.Date sqlDate = new java.sql.Date(formatedDate.getTime());
		jobDetails.setOpenDate(sqlDate);
		jobDetails.setCloseDate(sqlDate);
		jobDetails.setMaxComp(compen);
		jobDetails.setMinComp(compen);
		JobLocation jobLocation = new JobLocation();
		jobLocation.setLocation_country("IN");
		jobLocation.setLocation_stateprovince("IN-KA");
		jobLocation.setLocation_city("Bangalore");
		jobLocation.setLocation_postalcode("560037");
		jobDetails.setJobLocation(jobLocation);

		jobDetails.setEducation("BACHELORSDEGREE");
		jobDetails.setIndustry("1");// industry
		jobDetails.setClassification_time("FULLTIME");
		jobDetails.setClassification_type("PERMANENT");

		// change everytime
		jobDetails.setDisplayId("Req-MK" + UUID.randomUUID().toString());
		jobDetails.setId("6001:7001:3596101ea95344d1d834be8cbd4abdfa");

		jobDetails.setJobDescription("Automation going on..");
		Company companyInfo = new Company();
		companyInfo.setCompany_name("Spire Technologies");
		companyInfo.setCompany_city("Bangalore");
		
		companyInfo.setCompany_streetaddress("cessna business park");
		companyInfo.setCompany_stateprovince("IN-KA");
		companyInfo.setCompany_postalcode("560037");
		companyInfo.setCompany_phone("9741134930");
		
		
		jobDetails.setCompanyInfo(companyInfo);

		CandidateResponseInfo candidateResponseInfo = new CandidateResponseInfo();
		candidateResponseInfo.setRecruiter_email("karthic.v@spire2grow.com");
		candidateResponseInfo.setRecruiter_name("Karthic");
		candidateResponseInfo.setCandidate_response_email("jagadeesh.jayachandra@spire2grow.com");
		candidateResponseInfo.setCandidate_response_url("http://www.spire2grow.com");
		candidateResponseInfo.setContact_email("jagadeesh.jayachandra@spire2grow.com");
		candidateResponseInfo.setContact_name("Jagadeesh");
		jobDetails.setResponseInfo(candidateResponseInfo);

		spire.talent.common.beans.CollectionEntity<ReqSkillMapBean> collectionEntity = new spire.talent.common.beans.CollectionEntity<ReqSkillMapBean>();
		List<ReqSkillMapBean> reList = new ArrayList<ReqSkillMapBean>();
		List<Link> links = new ArrayList<Link>();
		Link link = new Link();
		link.setHref(null);
		link.setRel(LinkType.SELF);
		links.add(link);
		ReqSkillMapBean reqSkillMapBean = new ReqSkillMapBean();
		reqSkillMapBean.setSkill("java");
		reList.add(reqSkillMapBean);
		collectionEntity.setItems(reList);
		jobDetails.setReqSkillMapBean(collectionEntity);
		jobDetails.setFunction("11000000");// function
		jobDetails.setCreatedBy("6000:6003:c685dde4c6bc42949d91035c340e33eb");
		jobDetails.setUpdatedBy("6000:6003:c685dde4c6bc42949d91035c340e33eb");
		
		jobDetails.setClassification_type("PERMANENT");
		jobDetails.setClassification_time("FULLTIME");
		jobDetails.setTravel_percentage("0");
		jobDetails.setFunction("11000000");
		jobDetails.setEducation("BACHELORSDEGREE");
		Company company = new Company();
		company.setCompany_city("Bangalore");
		company.setCompany_name("Spire Technologies");
		company.setCompany_streetaddress("cessna business park");
		company.setCompany_stateprovince("IN-KA");
		company.setCompany_postalcode("560037");
		jobDetails.setCompanyInfo(companyInfo);
		return jobDetails;

	}

}
