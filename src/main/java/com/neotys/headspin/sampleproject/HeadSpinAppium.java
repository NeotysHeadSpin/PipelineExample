package com.neotys.headspin.sampleproject;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.neotys.selenium.proxies.NLRemoteWebDriver;
import com.neotys.selenium.proxies.NLWebDriverFactory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import io.appium.java_client.AppiumDriver;

public class HeadSpinAppium {
	AppiumDriver<MobileElement> wb;
	NLRemoteWebDriver driver;
	String openSearch = "//*[@id=\'open-search\']";
	String searchField = "//*[@id=\'search-input-mobile\']";
	String searchSubmit = "//*[@id=\'search-button-mobile\']";

	String applicationURL="";


	@BeforeMethod @Before
	public void beforeMethod() throws Exception {
		//Replace <<cloud name>> with your perfecto cloud name (e.g. demo) or pass it as maven properties: -DcloudName=<<cloud name>>
		String cloudName = "<<cloud name>>";
		//Replace <<security token>> with your perfecto security token or pass it as maven properties: -DsecurityToken=<<SECURITY TOKEN>>  More info: https://developers.perfectomobile.com/display/PD/Generate+security+tokens
		String projectPath="";
		applicationURL="<<applicationURL>>";
		applicationURL=Utils.fetchApplicationURL(applicationURL);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("sessionDescription", "NeoLoad test");
		capabilities.setCapability("deviceOrientation", "portrait");
		capabilities.setCapability("captureScreenshots", true);
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "HD1907");
        capabilities.setCapability("udid", "00ac5958dd8d9aed");
		capabilities.setCapability("headspin:capture", true);
		capabilities.setCapability("appPackage", "com.android.chrome");
		capabilities.setCapability("appActivity", "com.google.android.apps.chrome.Main");



		boolean session=false;
		for(int i=0;i<5;i++)
		{
			try {
				session=createSession(capabilities,cloudName,projectPath);
				if(session)
					break;
			}
			catch (SessionNotCreatedException e)
			{
				e.printStackTrace();
			}
		}
		if(!session)
			throw new RuntimeException("Driver not created with capabilities: " + capabilities.toString());



	}

	public boolean createSession(Capabilities capabilities, String cloudname,String projectname) throws Exception,SessionNotCreatedException {
		boolean result=false;
		wb = new AndroidDriver<MobileElement>(new URL("https://" + Utils.fetchCloudName(cloudname)  + "wd/hub"), capabilities);
		driver = (NLRemoteWebDriver) NLWebDriverFactory.newNLWebDriver(wd, "KonaKart Android", projectname);

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		System.out.println("Session created, session id :"+driver.getSessionId());
		result=true;
		return result;
	}

	@Test @org.junit.Test
	public void appiumTest() throws Exception {

		//----enable vitals metrics---
		driver.startTransaction("Navigate to Konakart.com");
		driver.get("http://"+applicationURL);
		driver.stopTransaction();


		driver.startTransaction("Search for Comptuer");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(openSearch)));
		WebElement element=driver.findElement(By.xpath((openSearch)));
		element.click();


		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchField)));
		element=driver.findElement(By.xpath((searchField)));
		element.clear();
		element.sendKeys("book");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchSubmit)));
		element=driver.findElement(By.xpath((searchSubmit)));
		element.click();
		driver.stopTransaction();


  	    driver.startTransaction("Search for phone");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(openSearch)));
		element=driver.findElement(By.xpath((openSearch)));
		element.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchField)));
		element=driver.findElement(By.xpath((searchField)));
		element.clear();
		element.sendKeys("phone");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchSubmit)));
		element=driver.findElement(By.xpath((searchSubmit)));
		element.click();
		driver.stopTransaction();


		driver.startTransaction("Search for game");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(openSearch)));
		element=driver.findElement(By.xpath((openSearch)));
		element.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchField)));
		element=driver.findElement(By.xpath((searchField)));
		element.clear();
		element.sendKeys("game");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchSubmit)));
		element=driver.findElement(By.xpath((searchSubmit)));
		element.click();
		driver.stopTransaction();



		driver.close();
		driver.quit();
	}




}

