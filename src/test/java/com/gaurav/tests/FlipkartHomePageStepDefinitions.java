package com.gaurav.tests;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.gaurav.pages.HomePage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
public class FlipkartHomePageStepDefinitions {
    private WebDriver driver;
    private HomePage homePage;

    @Before
    public void setUp() {
        // Setup WebDriver using WebDriverManager
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        homePage = new HomePage(driver);
    }

    @Given("^I navigate to the Flipkart site$")
    @Step("Navigate to the Flipkart site")
    public void navigateToFlipkartSite() {
        homePage.navigateTo("https://www.flipkart.com/");
        captureScreenshot();
    }

    @When("^I perform some verification on the Flipkart site$")
    @Step("Perform verification on the Flipkart site")
    public void performVerification() {
        // Perform any verification steps, for example, checking the title and logo
        String pageTitle = homePage.getPageTitle();
        Assert.assertTrue(pageTitle.contains("Online Shopping Site for Mobiles, Electronics, Furniture, Grocery, Lifestyle, Books & More. Best Offers!"));
        Assert.assertTrue(homePage.isLogoDisplayed());
        captureScreenshot();
    }

    @Then("^I should close the browser$")
    @Step("Close the browser")
    public void closeBrowser() {
        captureScreenshot(); // Capture a screenshot before closing
        tearDown();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] captureScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
