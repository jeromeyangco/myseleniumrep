package com.qtpselenium.zoho.project.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.qtpselenium.zoho.project.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest {
	
	//constructor
//	public BaseTest() {
//		initProperties();
//	}

	public WebDriver driver;
	public Properties prop;
	public Properties envProp;
	public ExtentReports rep = ExtentManager.getInstance();
	public ExtentTest test;
	boolean gridRun = false;//true or false
	
	
	/******************COMMON FUNCTIONS******************/
//	public void init() {
//		//initialize properties file
//		prop = new Properties();
//		try {
//			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\projectconfig.properties");
//			prop.load(fis);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public void init() {
		//initialize properties file
		if(prop==null) {
			prop=new Properties();
			envProp=new Properties();
			try {
				FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\projectconfig.properties");
				prop.load(fis);
				String env=prop.getProperty("env");
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\"+env+".properties");
				envProp.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void openBrowser2(String bType) {
		if(bType.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\JarFiles\\_drivers\\chromedriver_ver_2_41_2018-06-07_supports_67-69\\chromedriver.exe");
			driver = new ChromeDriver();
			
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			test.log(LogStatus.INFO, "BaseTest -> openBrowser() -> Browser opened successfully: " +bType);
		}
	}
	
	public void openBrowser(String bType) {
	//call method - initialize properties
	//initProperties();
		test.log(LogStatus.INFO, "BaseTest -> openBrowser() -> Opening browser: " +bType);
		if(!gridRun) {//means gridRun=false
			if(bType.equals("Mozilla")) {
//				System.setProperty("webdriver.gecko.driver", prop.getProperty("geckodriver_exe"));
				//System.setProperty("webdriver.gecko.marionette", System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
				driver = new FirefoxDriver();
				
			} else if(bType.equals("Chrome")) {
				//System.setProperty("webdriver.chrome.driver", "C:\\JarFiles\\_drivers\\chromedriver_ver_2_41_2018-06-07_supports_67-69\\chromedriver.exe");
				//System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriver_exe"));
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
				
				//<Switch OFF> this browser notification on the chrome browser
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.notifications", 2);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", prefs);
				//</Switch OFF>
				
				driver = new ChromeDriver(options);
				
			} else if(bType.equals("IE")) {
				//System.setProperty("webdriver.ie.driver", prop.getProperty("iedriver_exe"));
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\drivers\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
		} else {//GRID RUN -> means gridRun=true
			//grid
			DesiredCapabilities cap=null;
			if(bType.equals("Mozilla")) {
				cap = DesiredCapabilities.firefox();
				cap.setBrowserName("firefox");
				cap.setJavascriptEnabled(true);
				cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				
			} else if(bType.equals("Chrome")) {
				cap = DesiredCapabilities.chrome();
				cap.setBrowserName("chrome");
				cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			}
			
			try {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		test.log(LogStatus.INFO, "BaseTest -> openBrowser() -> Browser opened successfully: " +bType);
	}
	
//	public void openBrowser(String bType) {
//	//call method - initialize properties
//	//initProperties();
//		test.log(LogStatus.INFO, "BaseTest -> openBrowser() -> Opening browser: " +bType);
//		if(bType.equals("Mozilla")) {
//			System.setProperty("webdriver.gecko.driver", prop.getProperty("geckodriver_exe"));
//			driver = new FirefoxDriver();
//			
//		} else if(bType.equals("Chrome")) {
//			System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriver_exe"));
//			//driver = new ChromeDriver();
//			
//			//set chrome options
//			//Create prefs map to store all preferences
//			Map<String, Object> prefs = new HashMap<String, Object>();
//			//Put this into prefs map to switch off browser notification
//			prefs.put("profile.default_content_setting_values.notifications", 2);
//			//Create chrome options to set this prefs			
//			ChromeOptions options = new ChromeOptions();
//			options.setExperimentalOption("prefs", prefs);
//			//Now initialize chrome driver with chrome options which will switch off this browser notification on the chrome browser
//			driver = new ChromeDriver(options);
//			
//		} else if(bType.equals("IE")) {
//			System.setProperty("webdriver.ie.driver", prop.getProperty("iedriver_exe"));
//			driver = new InternetExplorerDriver();
//			
//		}
//		
//		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
//		driver.manage().window().maximize();
//		test.log(LogStatus.INFO, "BaseTest -> openBrowser() -> Browser opened successfully: " +bType);
//	}

	public void navigate(String urlKey) {
		//test.log(LogStatus.INFO, "BaseTest -> navigate() -> navigating to: " +prop.getProperty(urlKey));
		//driver.get(prop.getProperty(urlKey));
		test.log(LogStatus.INFO, "BaseTest -> navigate() -> navigating to: " +envProp.getProperty(urlKey));
		driver.get(envProp.getProperty(urlKey));
	}
	
	public void click(String locatorKey) {
		//driver.findElement(By.xpath(prop.getProperty(xpathKey))).click();
		//concept -> WebElement element = driver.findElement(By.xpath("//*[@id='abc']"));
		//concept -> element.click();
		test.log(LogStatus.INFO, "BaseTest -> click() -> Clicking on: " +locatorKey);
		getElement(locatorKey).click();
		test.log(LogStatus.INFO, "BaseTest -> click() -> Sucessfully clicked on: " +locatorKey);
	}
	
	//click on this xpath until next xpath is visible (click until 3 to 4 times)
	//logic: like a manual refresh - wait to reflect the data on the table
	public void clickAndWait(String locator_clicked, String locator_pres) {
		System.out.println("Clicking and waiting on - " +locator_clicked);
		test.log(LogStatus.INFO, "Clicking and waiting on - " +locator_clicked);
		int count=5;
		for(int i=0;i<count;i++) {
			getElement(locator_clicked).click();
			waitForSeconds(2);
			if(isElementPresent(locator_pres))
				break;
		}
	}
	
	public void type(String locatorKey, String data) {
		test.log(LogStatus.INFO, "BaseTest -> type() -> Typing in: " +locatorKey +". Data: " +data);
		getElement(locatorKey).sendKeys(data);
		test.log(LogStatus.INFO, "BaseTest -> type() -> Typed sucessfully in: " +locatorKey);
	}
	
	public void typeByActionsClass(String locatorKey, String data) {
		Actions actions = new Actions(driver);
		actions.moveToElement(getElement(locatorKey));
		actions.click();
		actions.sendKeys("");//set focus to the element
		actions.sendKeys(data);
		actions.build().perform();
	}
	
	public WebElement getElement(String locatorKey) {
		WebElement element = null;
		try {
			if(locatorKey.endsWith("_id")) {
				element = driver.findElement(By.id(prop.getProperty(locatorKey)));
			} else if(locatorKey.endsWith("_name")) {
				element = driver.findElement(By.name(prop.getProperty(locatorKey)));
			} else if(locatorKey.endsWith("_class")) {
				element = driver.findElement(By.className(prop.getProperty(locatorKey)));
			} else if(locatorKey.endsWith("_xpath")) {
				element = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
			} else {
				System.out.println("Locator not correct - " +locatorKey);
				reportFail("getElement() -> else -> " +locatorKey);
//				Assert.fail("Locator not correct - " +locatorKey);
//				test.log(LogStatus.FAIL, "Locator not correct - " +locatorKey);
//				Assert.fail("getElement -> else -> LOCATOR NOT CORRECT");
			}
		} catch (Exception e) {
			reportFail("getElement() -> Catch -> " +e.getMessage());
//			e.printStackTrace();
//			Assert.fail("Failed the test - " +e.getMessage());
//			test.log(LogStatus.FAIL, "Failed the test - " +e.getMessage());
		}
		return element;
	}
	
	
	public void switchWindow() {
		Set<String> windowIds = driver.getWindowHandles();
		System.out.println("Total Windows Opened -> " +windowIds.size());
		
		String mainWindow = "";
		String secondWindow = "";
		Iterator<String> iter = windowIds.iterator();
		
		if(windowIds.size()!=1) {
			test.log(LogStatus.INFO, "Multiple Windows");
			mainWindow = iter.next();
			secondWindow = iter.next();
			driver.switchTo().window(secondWindow);
		} else {
			test.log(LogStatus.INFO, "Single Window");
			mainWindow = iter.next();
			driver.switchTo().window(mainWindow);
		}
	}
	
	public int getLeadRowNum(String leadName) {
		System.out.println("Finding the lead name - " +leadName);
		test.log(LogStatus.INFO, "Finding the lead name - " +leadName);
		List<WebElement> leadNames = driver.findElements(By.xpath(prop.getProperty("tblLeadNameCol_xpath")));
		
		for(int i=0; i<leadNames.size(); i++) {
			System.out.println(leadNames.get(i).getText());
			if(leadNames.get(i).getText().trim().equals(leadName)) {
				test.log(LogStatus.INFO, "Lead name found at row # -> " +(i+1));
				return (i+1);
			}
		}
		
		//return -1 -> it means it is NOT FOUND (because the ROW# can never be -1)
		test.log(LogStatus.INFO, "Lead not found - " +leadName);
		return -1;
	}
	
	public void clickOnLead(String leadName) {
		test.log(LogStatus.INFO, "Finding the lead " +leadName);
		int rNum = getLeadRowNum(leadName);
		driver.findElement(By.xpath(prop.getProperty("leadpart1_xpath")+rNum+prop.getProperty("leadpart2_xpath"))).click();
		
	}
	
	public void selectDate(String d) {
		test.log(LogStatus.INFO, "Selecting the date "+d);
		
		//convert String date(input from excel) in date object
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date dateToBeSelected = sdf.parse(d);
			Date currentDate = new Date();
			
			sdf = new SimpleDateFormat("MMMM");
			String monthToBeSelected = sdf.format(dateToBeSelected);
			
			sdf = new SimpleDateFormat("yyyy");
			String yearToBeSelected = sdf.format(dateToBeSelected);

			sdf = new SimpleDateFormat("d");
			String dayToBeSelected = sdf.format(dateToBeSelected);
			
			//ie. format -> June 2016
			String monthAndYearToBeSelected = monthToBeSelected +" " +yearToBeSelected;
			
			//infinite loop
			while (true) {
				if(currentDate.compareTo(dateToBeSelected)==1) {
					//back
					click("back_xpath");
				} else if(currentDate.compareTo(dateToBeSelected)==-1) {
					//forward
					click("forward_xpath");
				}
				
				if(monthAndYearToBeSelected.equals(getText("monthAndYearDisplayed_xpath"))) {
					break;
				}
				
				driver.findElement(By.xpath("//td[text()='"+dayToBeSelected+"']")).click();
				test.log(LogStatus.INFO, "Date Selection Successful "+d);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/******************VALIDATION FUNCTIONS******************/
	public boolean verifyTitle() {
		return false;
	}
	
	public boolean isElementPresent(String locatorKey) {
		List<WebElement> elementList = null;
		try {
			if(locatorKey.endsWith("_id")) {
				elementList = driver.findElements(By.id(prop.getProperty(locatorKey)));
			} else if(locatorKey.endsWith("_name")) {
				elementList = driver.findElements(By.name(prop.getProperty(locatorKey)));
			} else if(locatorKey.endsWith("_class")) {
				elementList = driver.findElements(By.className(prop.getProperty(locatorKey)));
			} else if(locatorKey.endsWith("_xpath")) {
				elementList = driver.findElements(By.xpath(prop.getProperty(locatorKey)));
			} else {
				System.out.println("Locator not correct - " +locatorKey);
				Assert.fail("Locator not correct - " +locatorKey);
				test.log(LogStatus.FAIL, "Locator not correct - " +locatorKey);
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
			Assert.fail("Failed the test - " +e.getMessage());
//			test.log(LogStatus.FAIL, "Failed the test - " +e.getMessage());
		}
		
		if(elementList.size()==0)
			return false;
		else
			return true;
	}
	
	//return true if text is equal, return false if text is not equal
	public boolean verifyText(String locatorKey, String expectedTextKey) {
		
		String actualText = getElement(locatorKey).getText();
		String expectedText = prop.getProperty(expectedTextKey);
		if(actualText.equals(expectedText))
			return true;
		else
			return false;
	}
	
	
	/******************REPORT FUNCTIONS******************/
	public void reportPass(String msg) {
		print(msg);
		test.log(LogStatus.PASS, msg);
	}
	
	public void reportFail(String msg) {
		test.log(LogStatus.FAIL, "reportFail() -> LogStatus.FAIL -> "+msg);//for extent report
		takeScreenshot();
		Assert.fail("reportFail() -- Assert.FAIL() -- "+msg);//for testng report - will stop the execution here
	}
	
	public void takeScreenshot() {
		//Filename of the screenshot
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		
		//Store screenshot in that file
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir")+"\\screenshots\\"+screenshotFile));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		//Put screenshots file in the reports
		test.log(LogStatus.INFO, "Screenshot -> " +test.addScreenCapture(System.getProperty("user.dir")+"\\screenshots\\"+screenshotFile));
		
	}
	
	public void print(String text) {
		System.out.println("console logs: "+text);
	}
	
	public void acceptAlert() {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.alertIsPresent());
		test.log(LogStatus.INFO, "Accepting alert");
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
	}
	
	public void waitForPageToLoad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String state = (String)js.executeScript("return document.readyState");
		
		while(!state.equals("complete")) {
			waitForSeconds(2);
			state = (String)js.executeScript("return document.readyState");
		}
	}
	
	public void waitForSeconds(int timeToWait) {
		try {
			Thread.sleep(timeToWait * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getText(String locatorKey) {
		test.log(LogStatus.INFO, "Getting text from " +locatorKey);
		return getElement(locatorKey).getText();
	}
	
	
	
	/******************SPECIFIC APP FUNCTIONS******************/
	//public boolean doLogin(String username, String password) {
	public void doLogin(String username, String password) {
		test.log(LogStatus.INFO, "Logging in with - " +username +", " +password);
		click("loginLink_xpath");
		
		//add wait after clicking a link - best practice
		waitForSeconds(1);
		
		//sometimes it may fail if switch to frame while page is not load completely
		//add wait for page to load function using JavascriptExecutor
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		js.executeScript("return document.readyState");
		
		//wait for page to load
		waitForPageToLoad();
		
		//check total frames
//		int totalFrames = driver.findElements(By.tagName("iframe")).size();
//		System.out.println("Total Frames -> " +totalFrames);
//		test.log(LogStatus.INFO, "Total Frames -> " +totalFrames);
//		driver.switchTo().frame(0);
		
		type("txtLogin_xpath", username);
		type("txtPassword_xpath", password);
		click("chkKeepMeSignedIn_xpath");
		click("btnSignIn_xpath");
		
//		wait(2);
		waitForPageToLoad();
		
		if(isElementPresent("lnkCRM_xpath")) {
			test.log(LogStatus.INFO, "isElementPresent: Login Success");
//			return true;
		} else {
			test.log(LogStatus.INFO, "isElementPresent: Login Failed");
//			Assert.fail("Login Failed");
//			return false;
		}
	}
	
	
	
	
}
