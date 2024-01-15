package com.gaurav.tests;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.gaurav.pages.HomePage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.gaurav.listeners.AllureTestListener;
import org.gaurav.base.basepage;

import java.io.ByteArrayInputStream;

@Listeners({AllureTestListener.class})
public class FlipkartHomePageStepDefinitions {
    public basepage baseobject;
    public WebDriver driver;
    public HomePage homePage;

    @Before
    public void setUp() {
        // Setup WebDriver using WebDriverManager
        baseobject = new basepage();
        driver = baseobject.initialize_driver();
        homePage = new HomePage(driver);
    }

    @Given("^I navigate to the Flipkart site$")
    @Step("Navigate to the Flipkart site")
    public void navigateToFlipkartSite() {
        homePage.navigateTo("https://www.flipkart.com/");
    }

    @When("^I perform some verification on the Flipkart site$")
    @Step("Perform verification on the Flipkart site")
    public void performVerification() {
        // Perform any verification steps, for example, checking the title and logo
        String pageTitle = homePage.getPageTitle();
        Assert.assertTrue(pageTitle.contains("Online Shopping Site for Mobiles++, Electronics, Furniture, Grocery, Lifestyle, Books & More. Best Offers!"));

    }

    @When("^I verified the Flipkart Site logo$")
    @Step("perform Logo Verification")
    public void logoVerification(){
        Assert.assertTrue(homePage.isLogoDisplayed());
    }

    @Then("^I should close the browser$")
    @Step("Close the browser")
    public void closeBrowser() {
        //TearDown();
    }


    @After
    public void TearDown(Scenario scenario){
          if(scenario.isFailed()){
              byte[] screenshot = ( (TakesScreenshot)driver) . getScreenshotAs (OutputType. BYTES) ;
              Allure.addAttachment("Fai1ed Screenshot", new ByteArrayInputStream(screenshot));
          }
    }





}
