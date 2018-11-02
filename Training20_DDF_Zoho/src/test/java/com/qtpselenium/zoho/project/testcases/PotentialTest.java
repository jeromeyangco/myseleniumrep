package com.qtpselenium.zoho.project.testcases;

import java.util.Hashtable;

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

public class PotentialTest extends BaseTest {
	SoftAssert softAssert;
	Xls_Reader2 xls;
	
	@Test(priority=1,dataProvider="getData")
	public void createPotentialTest(Hashtable<String,String> data) {
		test = rep.startTest("CreatePotentialTest");
		test.log(LogStatus.INFO, data.toString());
		
		if(!DataUtil.isRunnable("CreatePotentialTest", xls) ||  data.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}
		
		openBrowser(data.get("Browser"));
		navigate("appurl");
		doLogin(prop.getProperty("username"), prop.getProperty("password"));
		click("lnkCRM_xpath");
		switchWindow();
		waitForSeconds(5);
		
		click("potentials_xpath");
		click("newPotential_xpath");
		type("potentialName_xpath",data.get("PotentialName"));
		type("accountName_xpath",data.get("AccountName"));
		type("stage_xpath",data.get("Stage"));
		selectDate(data.get("ClosingDate"));
		click("savePotentialButton_xpath");
		//validate - you
		
		
		reportPass("Potential created successfully");
		takeScreenshot();
	}
	
	@Test(priority=2,dependsOnMethods={"createPotentialTest"})
	public void deletePotentialAccountTest() {
		test = rep.startTest("deletePotentialAccountTest");
		
		// - you -> continue to code
		//
		//
		
		reportPass("Test Passed");
		
	}
	
	@DataProvider
	public Object[][] getData() {
		super.init();
		xls = new Xls_Reader2(prop.getProperty("xlspath"));
		return DataUtil.getTestData(xls, "CreatePotentialTest");
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

		if(driver!=null)
			driver.quit();
	}
	
}
