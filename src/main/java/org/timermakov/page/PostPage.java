package org.timermakov.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.timermakov.Utils;

public class PostPage extends Page {
    public PostPage(WebDriver driver) {
        super(driver);
    }

    public String getPostId() {
        String postUrl = Utils.getElementBySelector(driver, By.xpath("//*[@id[starts-with(., 'CommentsList-')]]")).getAttribute("id");
        String[] parts = postUrl.split("-");
        String postId = parts[1];
        return postId;
    }

    public void goToNextPost() {
        Utils.getElementBySelector(driver, By.xpath("//a[@class='Navigation Navigation-next']")).click();
    }

    public void goToPreviousPost() {
        Utils.getElementBySelector(driver, By.xpath("//a[@class='Navigation Navigation-prev']")).click();
    }
}