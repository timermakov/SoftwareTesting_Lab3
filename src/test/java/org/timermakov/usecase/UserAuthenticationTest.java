package org.timermakov.usecase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.timermakov.Utils;
import org.timermakov.page.MainPage;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
            mainPage.doLogin();
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
            mainPage.doLogin();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            mainPage.doLogout();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            assertEquals("Sign in", Utils.getElementBySelector(webDriver, By.xpath("//a[@class='Navbar-signin']")).getText());
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
            mainPage.doWrongLogin();
            assertThrows(TimeoutException.class, () -> Utils.getElementBySelector(webDriver, By.xpath("//div[@class='Dropdown NavbarUserMenu']//div[@class='Dropdown-title']//span")));
        });
        drivers.forEach(WebDriver::quit);
    }
}
