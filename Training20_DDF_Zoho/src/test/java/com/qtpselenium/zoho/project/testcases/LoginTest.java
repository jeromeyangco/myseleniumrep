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

public class LoginTest extends BaseTest {

	String testCaseName = "LoginTest";
	SoftAssert softAssert;
	Xls_Reader2 xls;
	
	@Test(alwaysRun=true,dataProvider="getData")
	public void doLoginTest(Hashtable<String, String> data) {
		test = rep.startTest("Login Test");
		test.log(LogStatus.INFO, "Starting the Login Test");
		test.log(LogStatus.INFO, "Data -> " +data.toString());
		
		if(!DataUtil.isRunnable(testCaseName, xls) || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("kipping the test as runmode is N");
		}
		
		//start run here
		//openBrowser(prop.getProperty("browser"));
		openBrowser(data.get("Browser"));

		//test only
		//System.setProperty("webdriver.chrome.driver", "C:\\JarFiles\\_drivers\\chromedriver_ver_2_41_2018-06-07_supports_67-69\\chromedriver.exe");
		//openBrowser2("Chrome");
		
		navigate("appurl");
		
		//it will call the specific function - login
		//boolean actualResult = doLogin(data.get("Username"), data.get("Password"));
		
		//***comment for now
		//doLogin(data.get("Username"), data.get("Password"));
		
//		boolean expectedResult = false;
//		if(data.get("ExpectedResult").equals("Y"))
//			expectedResult = true;
//		else
//			expectedResult = false;
//		
//		if(expectedResult!=actualResult) {
//			reportFail("Login Test Failed");//will stop execution once failed on this line
//		} else {
//			reportPass("Login Test Passed");
//		}
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
		
		//When you are running in GRID -> HUB -> node1 node2 node3 ...
		//node 1 (if this node is not quit (means testing is still going on),
		//it will NOT log again if you do not tell selenium it can now run another test case)
		//you have to quit the driver so that the node get released for the other test case
		if(driver!=null)
			driver.quit();
	}
	
	@DataProvider(parallel=true)
	public Object[][] getData() {
		super.init();
		xls = new Xls_Reader2(prop.getProperty("xlspath"));
		return DataUtil.getTestData(xls, testCaseName);
	}
}
