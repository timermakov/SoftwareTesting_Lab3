package org.timermakov.model;

import org.openqa.selenium.WebDriver;

public class NewPostPage extends Page {

    public NewPostPage(WebDriver driver) {
        super(driver);
    }

    public String getNewPostUrl() {
        //WebElement consent = Utils.getElementBySelector(driver, By.xpath("//button[@class='fc-button fc-cta-consent fc-primary-button']"));
        //if (consent != null) consent.click();+
        return driver.getCurrentUrl();
    }

}
