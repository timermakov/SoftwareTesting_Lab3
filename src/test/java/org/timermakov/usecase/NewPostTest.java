package org.timermakov.usecase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.timermakov.Utils;
import org.timermakov.page.MainPage;
import org.timermakov.page.NewPostPage;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewPostTest {

    @BeforeAll
    public static void prepareDrivers() {
        Utils.prepareDrivers();
    }

    @Test
    public void newPostTest() {
        List<WebDriver> drivers = Utils.getDrivers();
        drivers.parallelStream().forEach(webDriver -> {
            webDriver.manage().window().maximize();
            MainPage mainPage = new MainPage(webDriver);
            NewPostPage newPostPage = new NewPostPage(webDriver);
            webDriver.get(Utils.BASE_URL);
            String createPostGetUrl = mainPage.createNewPost();

            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            webDriver.get(createPostGetUrl);
            String openedNewPostUrl = newPostPage.getNewPostUrl();

            assertEquals(createPostGetUrl.toLowerCase(), openedNewPostUrl.toLowerCase());

        });
        drivers.forEach(WebDriver::quit);
    }

}
