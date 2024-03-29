package org.timermakov.model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.timermakov.Utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.time.Duration;

import static org.timermakov.Utils.CORRECT_USERNAME;
import static org.timermakov.Utils.CORRECT_PASSWORD;

public class MainPage extends Page {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void doLogin() {
        tryLogin(CORRECT_USERNAME, CORRECT_PASSWORD);
    }

    public void doWrongLogin() {
        tryLogin(CORRECT_USERNAME, Utils.WRONG_PASSWORD);
    }

    public void doLogout() {
        WebElement username = Utils.getElementBySelector(driver, By.xpath("//div[@class='Dropdown NavbarUserMenu']//div[@class='Dropdown-title']//span"));
        username.click();
        WebElement signOut = Utils.getElementBySelector(driver, By.xpath("//img[@class='sign-out']"));
        signOut.click();
        //Utils.waitUntilPageLoads(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));        

        WebElement submitButton = Utils.getElementBySelector(driver, By.xpath("//button[@type='submit']"));
        submitButton.click();
    }

    public void doBasicSearch() {
        //WebElement consent = Utils.getElementBySelector(driver, By.xpath("//button[@class='fc-button fc-cta-consent fc-primary-button']"));
        //if (consent != null) consent.click();
        WebElement input = Utils.getElementBySelector(driver, By.xpath("//input[@name='q']"));
        input.click();
        input.sendKeys("memes");

        WebElement searchButton = Utils.getElementBySelector(driver, By.xpath("//button[@type='submit']"));
        searchButton.click();
    }

    public String goToPostPage() {
        //WebElement consent = Utils.getElementBySelector(driver, By.xpath("//button[@class='fc-button fc-cta-consent fc-primary-button']"));
        //if (consent != null) consent.click();
        WebElement post = Utils.getElementBySelector(driver, By.xpath("//div[@id[starts-with(., 'Col-Child')]]"));
        post.click();
        String postUrl = driver.getCurrentUrl();
        String[] parts = postUrl.split("/gallery/");
        String postId = parts[1];
        return postId;
    }

    public String goToCategory() {
        //WebElement consent = Utils.getElementBySelector(driver, By.xpath("//button[@class='fc-button fc-cta-consent fc-primary-button']"));
        //if (consent != null) consent.click();
        WebElement categoryName = Utils.getElementBySelector(driver, By.xpath("//div[@class='Tag-name']"));
        String categoryNameStr = categoryName.getText();
        WebElement post = Utils.getElementBySelector(driver, By.xpath("//a[@class='Tag ']"));
        post.click();
        return categoryNameStr;
    }

    public String createNewPost() {
        //WebElement consent = Utils.getElementBySelector(driver, By.xpath("//button[@class='fc-button fc-cta-consent fc-primary-button']"));
        //if (consent != null) consent.click();
        WebElement newPostButton = Utils.getElementBySelector(driver, By.xpath("//*[@class='Button upload']"));
        newPostButton.click();

        WebElement uploadImageByInput = Utils.getElementBySelector(driver, By.xpath("//input[@placeholder='Paste image or URL']"));
        uploadImageByInput.click();

        String url = "https://img.freepik.com/free-photo/close-up-of-dumbbells-in-row_1203-1652.jpg";
        StringSelection stringSelection = new StringSelection(url);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        uploadImageByInput.sendKeys(Keys.CONTROL+"v");

        Utils.waitUntilPageLoads(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        return driver.getCurrentUrl();
    }


    private void tryLogin(CharSequence login, CharSequence password) {
        //WebElement consent = Utils.getElementBySelector(driver, By.xpath("//button[@class='fc-button fc-cta-consent fc-primary-button']"));
        //if (consent != null) consent.click();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebElement loginButton = Utils.getElementBySelector(driver, By.xpath("//a[@class='Navbar-signin']"));
        loginButton.click();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement usernameInput = Utils.getElementBySelector(driver, By.xpath("//input[@name='username']"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement passwordInput = Utils.getElementBySelector(driver, By.xpath("//input[@name='password']"));
        usernameInput.clear();
        passwordInput.clear();
        WebElement authButton = Utils.getElementBySelector(driver, By.xpath("//button[@name='submit']"));
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        //Utils.waitUntilPageLoads(driver, Duration.ofSeconds(5));
        
        usernameInput.sendKeys(login);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //Utils.waitUntilPageLoads(driver, Duration.ofSeconds(5));
        passwordInput.sendKeys(password);
        authButton.click();
    }
}

