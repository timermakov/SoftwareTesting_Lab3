package org.timermakov.usecase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.timermakov.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImgurTest {

    @BeforeAll
    static void prepareDrivers() {
        Utils.prepareDrivers();
    }

    @Test
    void testDriver() {
        Utils.getDrivers().forEach(this::execute);
    }

    private void execute(WebDriver driver) {
        driver.get(Utils.BASE_URL);
        driver.manage().window().maximize();

        String title = driver.getTitle();
        assertEquals("Imgur: The magic of the Internet", title);
        driver.quit();
    }
}
