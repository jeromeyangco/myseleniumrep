package com.qtpselenium.zoho.project.testcases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qtpselenium.zoho.project.base.BaseTest;
import com.qtpselenium.zoho.project.util.DataUtil;
import com.qtpselenium.zoho.project.util.Xls_Reader2;
import com.relevantcodes.extentreports.LogStatus;

public class LeadTest extends BaseTest {
	
	String testCaseName = "CreateLeadTest";
//	String testCaseName = "DeleteLeadAccountTest";
	SoftAssert softAssert;
	Xls_Reader2 xls;
	
	@Test(priority=1,dataProvider="getData")
	public void createLeadTest(Hashtable<String, String> data) {
		test = rep.startTest("Create Lead Test");
		test.log(LogStatus.INFO, "Starting the Create Lead Test");
		test.log(LogStatus.INFO, "Data -> " +data.toString());
		
		if(!DataUtil.isRunnable(testCaseName, xls) || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}
		
		openBrowser(data.get("Browser"));
		navigate("appurl");
		doLogin(prop.getProperty("username"), prop.getProperty("password"));
//		doLogin(envProp.getProperty("username"), envProp.getProperty("password"));
		
		click("lnkCRM_xpath");
		switchWindow();
		waitForSeconds(5);
		click("lnkLeads_xpath");
		click("btnCreateLead_xpath");
		typeByActionsClass("txtCompany_xpath", data.get("LeadCompany"));
		typeByActionsClass("txtLastName_xpath", data.get("LeadLastName"));
		click("btnSaveLeads_xpath");
		waitForSeconds(5);
		click("lnkLeads_xpath");
		
		//validate lead is created
		int rNum = getLeadRowNum(data.get("LeadLastName"));
		System.out.println("ROW NUM = " +rNum);
		if(rNum==-1) {
			reportFail("Lead not found on the table - " +data.get("LeadLastName"));
		} else {
			reportPass("Lead found on the table - " +data.get("LeadLastName"));
			takeScreenshot();
		}
	}
	
	//execution - passed
	@Test(priority=2, dataProvider="getData")
	public void convertLeadTest(Hashtable<String, String> data) {
		test = rep.startTest("CreateLeadTest");
		
		if(!DataUtil.isRunnable(testCaseName, xls) || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}
		
		openBrowser(data.get("Browser"));
		navigate("appurl");
		//doLogin(prop.getProperty("username"), prop.getProperty("password"));
		doLogin(prop.getProperty("username"), prop.getProperty("password"));
		click("lnkCRM_xpath");
		switchWindow();
		waitForSeconds(5);
		click("lnkLeads_xpath");
		
		//int rNum = getLeadRowNum(data.get("LeadLastName"));
		clickOnLead(data.get("LeadLastName"));
		click("btnConvert_xpath");
		click("btnConvertAndSave_xpath");
		//assignment -> validate that the lead is successfully converted
		//should NOT present on the Lead tab
		//and should Display on the Accounts tab -> but for now it is not working (bug by the website)
		//BUT if you perform a Refresh -> it will come-up on the Account tab
		
	}
	
	@Test(priority=3, dataProvider="getDataDeleteLead")
	public void deleteLeadAccountTest(Hashtable<String, String> data) {
		test = rep.startTest("DeleteLeadAccountTest");
		
		if(!DataUtil.isRunnable(testCaseName, xls) || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}
		
		openBrowser(data.get("Browser"));
		navigate("appurl");
		doLogin(prop.getProperty("username"), prop.getProperty("password"));
		click("lnkCRM_xpath");
		switchWindow();
		waitForSeconds(5);
		click("lnkLeads_xpath");
		
		//int rNum = getLeadRowNum(data.get("LeadLastName"));
		clickOnLead(data.get("LeadLastName"));
		waitForPageToLoad();
		
		//delete
		click("btnLeadsMenu_xpath");
		waitForSeconds(3);
		click("btnLeadsMenu_Delete_xpath");
		
		//issue - unable to accept the alert button
		acceptAlert();
		waitForSeconds(3);
		
		int rNum = getLeadRowNum(data.get("Wilson3"));
		if(rNum!=-1)
			reportFail("Could not delete the lead");
		
		reportPass("Lead deleted successfully");
		takeScreenshot();
		
	}
	
	
	@BeforeMethod
	public void init() {
		softAssert = new SoftAssert();
	}
	
	@AfterMethod
	public void quit() {
		try {
			softAssert.assertAll();
//			test.log(LogStatus.FAIL, "ERROR ENCOUNTERED");
		} catch(Error e) {
			test.log(LogStatus.FAIL, e.getMessage());
		}
		
		if(rep!=null) {
			rep.endTest(test);
			rep.flush();
		}

		//can create prop. -> flag if user want to quit the browser or not
//		if(driver!=null)
//			driver.quit();
	}
	
	
	@DataProvider(parallel=true)
	public Object[][] getData() {
		super.init();
		xls = new Xls_Reader2(prop.getProperty("xlspath"));
//		return DataUtil.getTestData(xls, testCaseName);
		return DataUtil.getTestData(xls, "CreateLeadTest");
	}
	
	@DataProvider(parallel=true)
	public Object[][] getDataDeleteLead() {
		super.init();
		xls = new Xls_Reader2(prop.getProperty("xlspath"));
		return DataUtil.getTestData(xls, "DeleteLeadAccountTest");
	}

}
