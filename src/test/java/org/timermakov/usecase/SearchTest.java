package org.timermakov;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchTest {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("http://imgur.com");
            WebElement postPreview = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'post')]")));
            String expectedTitle = postPreview.getAttribute("data-title");
            postPreview.click();

            // Verify that the title matches
            // ...

        } finally {
            driver.quit();
        }
    }
}
