package org.gaurav.listeners;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.gaurav.base.basepage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class AllureTestListener implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }


    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] attachScreenshotPNG(byte[] screenshot) {
        return screenshot;
    }

    // Text attachments for Allure
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    // Text attachments for Allure
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    // HTML attachments for Allure
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("I am in onStart method " + iTestContext.getName());
        iTestContext.setAttribute("WebDriver", basepage.getDriver());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("I am in onFinish method " + iTestContext.getName());
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
       // System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");
        String methodName = iTestResult.getMethod().getMethodName();
        String scenarioName = iTestResult.getMethod().getConstructorOrMethod().getMethod().getName();
        System.out.println("Test failed: " + methodName + " (Scenario: " + scenarioName + ")");

        Object testClass = iTestResult.getInstance();
        WebDriver driver = basepage.getDriver();

        // Save the screenshot to a folder
        if (driver instanceof WebDriver) {
            saveScreenshotToFolder(driver, getTestMethodName(iTestResult));
        }

        // Allure ScreenShotRobot and SaveTestLog
        if (driver instanceof WebDriver) {
            System.out.println("Screenshot captured for test case:" + getTestMethodName(iTestResult));
            byte[] screenshot =((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            attachScreenshotPNG(screenshot);
            //saveScreenshotPNG(driver);
        }

        // Save a log on allure.
        saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");
    }


    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

    private void saveScreenshotToFolder(WebDriver driver, String testName) {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        String screenshotName = testName.replaceAll(" ", "_");
        String screenshotPath = "target/screenshots/" + screenshotName + ".png";
        try {
            Path path = Paths.get(screenshotPath);
            Files.createDirectories(path.getParent());
            Files.write(path, screenshot);
            byte[] fileContent = Files.readAllBytes(path);
            String base64Content = Base64.getEncoder().encodeToString(fileContent);
            Allure.addAttachment("Screenshot", "image/png", base64Content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
