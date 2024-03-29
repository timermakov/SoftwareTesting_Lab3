package org.timermakov.usecase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.timermakov.Utils;
import org.timermakov.page.MainPage;
import org.timermakov.page.PostPage;
import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTest {

    @BeforeAll
    public static void prepareDrivers() {
        Utils.prepareDrivers();
    }

    @Test
    public void testCategory() {
        List<WebDriver> drivers = Utils.getDrivers();
        drivers.parallelStream().forEach(webDriver -> {
            webDriver.manage().window().maximize();
            MainPage mainPage = new MainPage(webDriver);
            PostPage postPage = new PostPage(webDriver);
            webDriver.get(Utils.BASE_URL);
            String postId = mainPage.goToCategory();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            WebElement categoryName = Utils.getElementBySelector(webDriver, By.xpath("//h1[@class='Cover-name']"));

            assertEquals(postId.toLowerCase(), categoryName.getText().toLowerCase());
        });
        drivers.forEach(WebDriver::quit);
    }

}
