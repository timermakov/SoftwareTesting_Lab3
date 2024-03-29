package org.timermakov.usecase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.timermakov.Utils;
import org.timermakov.page.MainPage;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class UserAuthenticationTest {

    @BeforeAll
    public static void prepareDrivers() {
        Utils.prepareDrivers();
    }

    @Test
    void loginTest() {
        List<WebDriver> drivers = Utils.getDrivers();
        drivers.parallelStream().forEach(webDriver -> {
            webDriver.manage().window().maximize();
            MainPage mainPage = new MainPage(webDriver);
            webDriver.get(Utils.BASE_URL);
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            try {
                mainPage.doLogin();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            WebElement username = Utils.getElementBySelector(webDriver, By.xpath("//div[@class='Dropdown NavbarUserMenu']//div[@class='Dropdown-title']//span"));
            assertEquals(Utils.CORRECT_USERNAME, username.getText());
            webDriver.quit();
        });
        drivers.forEach(WebDriver::quit);
    }

    @Test
    void logoutTest() {
        List<WebDriver> drivers = Utils.getDrivers();
        drivers.parallelStream().forEach(webDriver -> {
            webDriver.manage().window().maximize();
            MainPage mainPage = new MainPage(webDriver);
            webDriver.get(Utils.BASE_URL);
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            try {
                mainPage.doLogin();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            mainPage.doLogout();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            String signOutText = Utils.getElementBySelector(webDriver, By.xpath("//*[@id=\"text-content\"]/h1")).getText();

            assertEquals("You have been signed out", signOutText);
        });
        drivers.forEach(WebDriver::quit);
    }

    @Test
    void wrongLoginTest() {
        List<WebDriver> drivers = Utils.getDrivers();
        drivers.parallelStream().forEach(webDriver -> {
            webDriver.manage().window().maximize();
            MainPage mainPage = new MainPage(webDriver);
            webDriver.get(Utils.BASE_URL);
            try {
                mainPage.doWrongLogin();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

            String errorText = Utils.getElementBySelector(webDriver, By.xpath("//p[@class='error']")).getText();
            assertEquals("Your login information was incorrect.", errorText);
        });
        drivers.forEach(WebDriver::quit);
    }
}
