package com.gaurav.TESTRUNNER;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.gaurav.tests",
        plugin = {"pretty", "html:target/cucumber-reports", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
}