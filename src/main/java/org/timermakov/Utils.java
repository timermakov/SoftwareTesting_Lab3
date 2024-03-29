package org.timermakov;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static final String CHROME_DRIVER_NAME = "webdriver.chrome.driver";
    public static final String CHROME_DRIVER_PATH = "C:\\drivers\\chrome\\chromedriver.exe\\";
    public static final String CHROME_EXE = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
    public static final String FIREFOX_DRIVER_NAME = "webdriver.gecko.driver";
    public static final String FIREFOX_DRIVER_PATH = "C:\\drivers\\firefox\\geckodriver.exe\\";
    public static final String FIREFOX_EXE = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    public static final String BASE_URL = "https://imgur.com/";

    public static final String CORRECT_USERNAME = "timermakov";
    public static final String CORRECT_PASSWORD = "LABITMO1327!@#";
    public static final String WRONG_PASSWORD = "WRONGPASSWORD";


    public static List<WebDriver> getDrivers() {
        List<WebDriver> drivers = new ArrayList<>();
        try {
            List<String> properties = Files.readAllLines(Paths.get("imgur.properties"));
            for (String property : properties) {
                if (property.startsWith("WEB_DRIVER")) {
                    switch (property.toLowerCase().split("=")[1]) {
                        case "chrome" -> {
                            drivers.add(getChromeDriver());
                            return drivers;
                        }
                        case "firefox" -> {
                            drivers.add(getFirefoxDriver());
                            return drivers;
                        }
                        case "both" -> {
                            drivers.add(getChromeDriver());
                            drivers.add(getFirefoxDriver());
                            return drivers;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Web driver is not specified");
    }

    private static ChromeDriver getChromeDriver() {
        return startChromeDriver(new ChromeOptions());
    }

    protected static ChromeDriver startChromeDriver(ChromeOptions options) {
        options.setBinary(CHROME_EXE);
        System.setProperty(CHROME_DRIVER_NAME, CHROME_DRIVER_PATH);
        options.addArguments("--disable-blink-features=AutomationControlled", "--disable-extensions");
        return new ChromeDriver(options);
    }

    private static FirefoxDriver getFirefoxDriver() {
        return startFirefoxDriver(new FirefoxOptions());
    }

    protected static FirefoxDriver startFirefoxDriver(FirefoxOptions options) {
        options.setBinary(FIREFOX_EXE);
        System.setProperty(FIREFOX_DRIVER_NAME, FIREFOX_DRIVER_PATH);
        options.addArguments("--disable-blink-features=AutomationControlled", "--disable-extensions");
        return new FirefoxDriver(options);
    }

    public static void prepareDrivers() {
        System.setProperty(CHROME_DRIVER_NAME, CHROME_DRIVER_PATH);
        System.setProperty(FIREFOX_DRIVER_NAME, FIREFOX_DRIVER_PATH);
    }

    public static WebElement getElementBySelector(WebDriver driver, By selector) {
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return driverWait.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    public static void waitUntilPageLoads(WebDriver driver, Duration timeout) {
        WebDriverWait waitDriver = new WebDriverWait(driver, timeout);
        waitDriver.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

}
