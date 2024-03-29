package org.timermakov.usecase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.timermakov.Utils;
import org.timermakov.page.MainPage;
import org.timermakov.page.PostPage;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavigationBetweenPostsTest {

    @BeforeAll
    public static void prepareDrivers() {
        Utils.prepareDrivers();
    }

    @Test
    void navigatePostTest() {
        List<WebDriver> drivers = Utils.getDrivers();
        drivers.parallelStream().forEach(webDriver -> {
            webDriver.manage().window().maximize();
            MainPage mainPage = new MainPage(webDriver);
            PostPage postPage = new PostPage(webDriver);
            webDriver.get(Utils.BASE_URL);
            mainPage.goToPostPage();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            String postId = postPage.getPostId();
            postPage.goToNextPost();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            postPage.goToPreviousPost();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            String previousPostId = postPage.getPostId();
            assertEquals(postId, previousPostId);
        });
        drivers.forEach(WebDriver::quit);
    }
}