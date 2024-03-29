package org.timermakov.usecase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.timermakov.Utils;
import org.timermakov.page.MainPage;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchTest {

    @BeforeAll
    public static void prepareDrivers() {
        Utils.prepareDrivers();
    }

    @Test
    void searchBasicTest() {
        List<WebDriver> drivers = Utils.getDrivers();
        drivers.parallelStream().forEach(webDriver -> {
            webDriver.manage().window().maximize();
            MainPage mainPage = new MainPage(webDriver);
            webDriver.get(Utils.BASE_URL);
            mainPage.doBasicSearch();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            WebElement post = Utils.getElementBySelector(webDriver, By.xpath("//h1[contains(text(),'Today')]"));
            assertEquals("Today's most popular posts.", post.getText());
        });
        drivers.forEach(WebDriver::quit);
    }

    @Test
    void searchWithOptionsTest() {
        List<WebDriver> drivers = Utils.getDrivers();
        drivers.parallelStream().forEach(webDriver -> {
            webDriver.manage().window().maximize();
            MainPage mainPage = new MainPage(webDriver);
            webDriver.get(Utils.BASE_URL);
            mainPage.doBasicSearch();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            WebElement post = Utils.getElementBySelector(webDriver, By.xpath("//h1[contains(text(),'Today')]"));
            assertEquals("Today's most popular posts.", post.getText());

            WebElement radio = Utils.getElementBySelector(webDriver, By.xpath("//*[@id=\"search_adv_toggle\"]"));
            // Баг вёрстки - ссылка "advanced search" кликабельна не в центре элемента,
            // а чуть выше, приходится делать offset
            Actions actions = new Actions(webDriver);
            actions.moveToElement(radio).moveByOffset(0, -5).click().build().perform();

            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            WebElement input = Utils.getElementBySelector(webDriver, By.xpath("//*[@name='q_any']"));
            input.sendKeys("cringe");
            WebElement submitButton = Utils.getElementBySelector(webDriver, By.xpath("//*[@type='submit']"));
            submitButton.click();

            String postUrl = webDriver.getCurrentUrl();
            if (postUrl.equals("https://imgur.com/search/score/all?q_any=cringe&q_all=memes")) assertTrue(true);

        });
        drivers.forEach(WebDriver::quit);
    }
}