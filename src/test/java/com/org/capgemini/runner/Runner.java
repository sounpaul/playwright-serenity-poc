package com.org.capgemini.runner;


import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberSerenityRunner;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberSerenityRunner.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {
                "com.org.capgemini.hooks",
                "com.org.capgemini.stepdefinition"
        },
        tags = "@PlaywrightDocSearch",
        plugin = {"pretty", "json:build/reports/cucumber-reports/test-results.json"},
        monochrome = false)
public class Runner {
}
