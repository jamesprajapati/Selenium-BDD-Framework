package org.gaurav.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isLogoDisplayed() {
        String FlipkartLogo ="img[src=\"https://static-assets-web.flixcart.com/batman-returns/batman-returns/p/images/fkheaderlogo_exploreplus-44005d.svg\"][title=\"Flipkart\"]";
        return driver.findElement(By.cssSelector(FlipkartLogo)).isDisplayed();
    }
}