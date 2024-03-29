package org.timermakov.usecase;

import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.timermakov.Utils;
import org.timermakov.model.MainPage;
import org.timermakov.model.PostPage;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NavigationTest {

    @BeforeAll
    public static void prepareDrivers() {
        Utils.prepareDrivers();
    }

    @Test
    public void testNavigation() {
        List<WebDriver> drivers = Utils.getDrivers();
        drivers.parallelStream().forEach(webDriver -> {
            webDriver.manage().window().maximize();
            MainPage mainPage = new MainPage(webDriver);
            PostPage postPage = new PostPage(webDriver);
            webDriver.get(Utils.BASE_URL);
            String postId = mainPage.goToPostPage();
            //Utils.waitUntilPageLoads(webDriver, Duration.ofSeconds(5));
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            String selectedPostId = postPage.getPostId();
            assertEquals(postId, selectedPostId);
        });
        drivers.forEach(WebDriver::quit);
    }

}