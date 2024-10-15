package com.org.capgemini.hooks;

import com.org.capgemini.setup.Init;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Hooks {

    EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
    private static Map<String, Integer> scenarioCounterMap = new HashMap<>();
    private static String previousScenarioOutline = "";

    @BeforeAll
    public static void beforeAll() {
        Thread.currentThread().setName("playwright-poc");
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "-");
        if (!scenarioName.equals(previousScenarioOutline)) {
            previousScenarioOutline = scenarioName;
            scenarioCounterMap.put(scenarioName, 1);  // Reset counter for new scenario outline
        } else {
            scenarioCounterMap.put(scenarioName, scenarioCounterMap.get(scenarioName) + 1);
        }
        int scenarioIndex = scenarioCounterMap.get(scenarioName);
        log.info("Starting scenario : {}", scenario.getName());
        Serenity.setSessionVariable("scenarioName").to(scenarioName + "-" + scenarioIndex);
        if (tagFinder(scenario, "TalentSearch")) {
            System.setProperty("webdriver.base.url", EnvironmentSpecificConfiguration.from(environmentVariables).getPropertyValue("talent.webdriver.base.url"));
            System.setProperty("headless", EnvironmentSpecificConfiguration.from(environmentVariables).getPropertyValue("talent.headless"));
            System.setProperty("employeeNameToBeSearched", EnvironmentSpecificConfiguration.from(environmentVariables).getPropertyValue("employeeNameToBeSearched"));
        } else if (tagFinder(scenario, "PlaywrightDocSearch")) {
            System.setProperty("webdriver.base.url", EnvironmentSpecificConfiguration.from(environmentVariables).getPropertyValue("playwright.webdriver.base.url"));
            System.setProperty("headless", EnvironmentSpecificConfiguration.from(environmentVariables).getPropertyValue("playwright.headless"));
        }
        Init.launchBrowser();
    }

    @After
    public void afterScenario(Scenario scenario) {
        log.info("End of scenario : {}", scenario.getName());
        Init.closeBrowser();
    }

    private boolean tagFinder(Scenario currentScenario, String tagName) {
        boolean tagExist = false;
        for (String tag : currentScenario.getSourceTagNames()) {
            if (tag.equals("@" + tagName)) {
                tagExist = true;
                break;
            }
        }
        return tagExist;
    }

}
