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

public class LoginTest2 extends BaseTest {

	String testCaseName = "LoginTest";
	SoftAssert softAssert;
	Xls_Reader2 xls;
	
	@Test(dataProvider="getData")
	public void doLoginTest(Hashtable<String, String> data) {
		test = rep.startTest("Login Test");
		test.log(LogStatus.INFO, "Starting the Login Test");
		test.log(LogStatus.INFO, "Data -> " +data.toString());
		
		if(!DataUtil.isRunnable(testCaseName, xls) || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("kipping the test as runmode is N");
		}
		
		//start run here
		openBrowser(prop.getProperty("browser"));
		navigate("appurl");
		
		boolean elementpresent = isElementPresent("loginLink_xpath");
		System.out.println("ELEMENT PRESENCE === " +elementpresent);
		
		if(elementpresent)
			System.out.println("ELEMENT PRESENT - TRUE");
		else
			System.out.println("ELEMENT PRESENT - FALSE");

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
		
		rep.endTest(test);
		rep.flush();
	}
	
	@DataProvider
	public Object[][] getData() {
		super.init();
		xls = new Xls_Reader2(prop.getProperty("xlspath"));
		return DataUtil.getTestData(xls, testCaseName);
		
	} 
}
