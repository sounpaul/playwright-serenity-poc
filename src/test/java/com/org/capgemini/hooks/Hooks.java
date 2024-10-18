package com.org.capgemini.hooks;

import com.org.capgemini.setup.Init;
import io.cucumber.java.*;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class Hooks {

    EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
    private static Map<String, Integer> scenarioCounterMap = new HashMap<>();
    private static String previousScenarioOutline = "";

    private static final String DS = FileSystems.getDefault().getSeparator();
    private static final String root = System.getProperty("user.dir");
    private static final File buildSessionFile = new File(root + DS + "buildSession.txt");

    @BeforeAll
    public static void beforeAll() {
        Thread.currentThread().setName("playwright-poc");
        String buildSessionId = UUID.randomUUID() + "-" + getCurrentTime();
        try {
            FileUtils.writeStringToFile(buildSessionFile, buildSessionId, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @AfterAll
    public static void afterAll() {
        try {
            FileUtils.delete(buildSessionFile);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
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

    private static String getCurrentTime() {
        ZoneId zoneId = ZoneId.of("Asia/Kolkata");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("dd-MMM-yy-HHmmss");
        return tf.format(zonedDateTime);
    }

}
