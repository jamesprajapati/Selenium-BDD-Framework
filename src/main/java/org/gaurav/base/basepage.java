package org.gaurav.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.io.IOException;

public class basepage {

    public WebDriver driver;
    public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();


    public WebDriver initialize_driver() {

        // Set up WebDriver and enable headless mode
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);

        // Create the driver instance
        driver = new FirefoxDriver(options);

        // Other driver configurations
        driver.manage().deleteAllCookies();
        // Maximize window (instead of fullscreen, which is not applicable in headless mode)
        driver.manage().window().maximize();

        tdriver.set(driver);
        return getDriver();
    }

    public static synchronized WebDriver getDriver() {
        return tdriver.get();
    }

    public String getScreenshot() {
        File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
        File destination = new File(path);
        try {
            FileUtils.copyFile(src, destination);
        } catch (IOException e) {
            System.out.println("Capture Failed " + e.getMessage());
        }
        return path;
    }

}
