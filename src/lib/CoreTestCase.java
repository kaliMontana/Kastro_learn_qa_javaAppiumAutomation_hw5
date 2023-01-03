package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {
	protected AppiumDriver driver;
	private static String AppiumUrl = "http://localhost:4723/wd/hub";


	@Override
	public void setUp() throws Exception {
		super.setUp();

		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "and80");
		capabilities.setCapability("platformVersion", "8.0");
		capabilities.setCapability("automationName", "Appium");
		capabilities.setCapability("appPackage", "org.wikipedia");
		capabilities.setCapability("appActivity", "org.wikipedia.main.MainActivity");
		capabilities.setCapability("app", "D:\\learn_qa\\Kastro_learn_qa_javaAppiumAutomation_hw2\\apks\\org.wikipedia.apk");

		driver = new AndroidDriver(new URL(AppiumUrl), capabilities);
	}

	@Override
	public void tearDown() throws Exception {
		driver.quit();

		super.tearDown();
	}
}
