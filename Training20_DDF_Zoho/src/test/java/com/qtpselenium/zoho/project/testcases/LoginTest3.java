package com.qtpselenium.zoho.project.testcases;
import java.util.Hashtable;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.SkipException;
//www.sejsoft.com/downloads/jars.rar
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qtpselenium.zoho.project.base.BaseTest;
import com.qtpselenium.zoho.project.util.DataUtil;
import com.qtpselenium.zoho.project.util.Xls_Reader2;
import com.relevantcodes.extentreports.LogStatus;

public class LoginTest3 extends BaseTest {
	
	String testCaseName = "LoginTest";
	SoftAssert softAssert;
	Xls_Reader2 xls;
	
	
	@BeforeTest
	public void openConnection(){
		// db connection
		System.out.println("connected to database");
	}
	
	@AfterTest
	public void closeConnection(){
		System.out.println("closing the database connection");
	}
	
//	@BeforeMethod
//	public void openBrowser(){
//		// selenium
//		System.out.println("Opening browser");
//	}
	
	@AfterMethod
	public void closeBrowser(){
		System.out.println("Closing browser");

	}
	
	@Test(alwaysRun=true,dataProvider="getData") // annotation - represents test case
	public void testLogin(Hashtable<String, String> data){
		
		System.out.println("=====test only=====");
		
//		test = rep.startTest("Login Test");
//		test.log(LogStatus.INFO, "Starting the Login Test");
//		test.log(LogStatus.INFO, "Data -> " +data.toString());
		
//		if(!DataUtil.isRunnable(testCaseName, xls) || data.get("Runmode").equals("N")) {
//			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
//			throw new SkipException("kipping the test as runmode is N");
//		}
		
		//start run here
		//openBrowser(prop.getProperty("browser"));
//		openBrowser(data.get("Browser"));
		
		// selenium code
		System.out.println("Executing logintest");
		System.setProperty("webdriver.chrome.driver", "C:\\JarFiles\\_drivers\\chromedriver_ver_2_41_2018-06-07_supports_67-69\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://google.com");
		
	}
	
	
	@DataProvider(parallel=true)
	public Object[][] getData() {
		super.init();
		xls = new Xls_Reader2(prop.getProperty("xlspath"));
		return DataUtil.getTestData(xls, testCaseName);
	}
	
	
//	@Test
//	public void testRegister(){
//		System.out.println("Testing the registration");
//	}

}
