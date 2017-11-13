package com.build.qa.build.selenium.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseFramework {
	protected WebDriver driver;
	protected Wait<WebDriver> wait;
	private static final Logger LOG = LoggerFactory.getLogger(BaseFramework.class);
	private static final String CONFIG_FILE = "./conf/automation.properties";
	private static final String DRIVER_FIREFOX = "firefox";
	private static final String DRIVER_CHROME = "chrome";
	private static final String DRIVER_IE = "ie";
	private static final String DRIVER_SAFARI = "safari";
	private static Properties configuration;

	@Rule
	public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

	@BeforeClass
	public static void beforeClass() throws IOException {
		configuration = new Properties();
		FileInputStream input;

		LOG.info("Loading in configuration file.");
		input = new FileInputStream(new File(CONFIG_FILE));
		configuration.loadFromXML(input);
		input.close();
	}

	@Before
	public void setUpBefore() {

		String driversPath = "BrowserDrivers";

		String extension = "";
		String osName = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
		if (osName.contains("windows")) {
			extension = ".exe";
		}

		DesiredCapabilities capabilities;
		// Which driver to use?
		if (DRIVER_CHROME.equalsIgnoreCase(configuration.getProperty("BROWSER"))) {
			System.setProperty("webdriver.chrome.driver", driversPath + "/chromedriver" + extension);
			capabilities = DesiredCapabilities.chrome();
			driver = new ChromeDriver(capabilities);
		} else if (DRIVER_FIREFOX.equalsIgnoreCase(configuration.getProperty("BROWSER"))) {
			System.setProperty("webdriver.gecko.driver", driversPath + "/geckodriver" + extension);
			capabilities = DesiredCapabilities.firefox();
			driver = new FirefoxDriver(capabilities);
		} else if (DRIVER_IE.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", driversPath + "/IEdriverServer.exe");
			capabilities = DesiredCapabilities.internetExplorer();
			driver = new InternetExplorerDriver(capabilities);
		} else if (DRIVER_SAFARI.equalsIgnoreCase("Safari")) {
			capabilities = DesiredCapabilities.safari();
			driver = new SafariDriver(capabilities);
		}
		// Define fluent wait
		wait = new FluentWait<WebDriver>(driver).withTimeout(15, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);
	}

	protected WebDriver getDriver() {
		return driver;
	}

	protected String getConfiguration(String config) {
		return configuration.getProperty(config);
	}

	@After
	public void tearDownAfter() {
		LOG.info("Quitting driver.");
		driver.quit();
		driver = null;
	}
}
