package com.org.capgemini.utils;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
public class LTCapabilities {

    public static String getCapabilities() {
        String cdpUrl = "";
        try {
            JsonObject capabilities = new JsonObject();
            JsonObject ltOptions = new JsonObject();
            capabilities.addProperty("browsername", "Chrome");
            capabilities.addProperty("browserVersion", "latest");
            capabilities.addProperty("platform", "Linux");
            ltOptions.addProperty("user", "sounak.paul");
            ltOptions.addProperty("accessKey", "UXLpTS8qGhLPMfhV6TAPj9X9f1f1Sh18TlXqHmPsgvM7IvAeMU");
            ltOptions.addProperty("visual", true);
            ltOptions.addProperty("video", true);
            ltOptions.addProperty("timezone", "Kolkata");
            ltOptions.addProperty("project", "Playwright Serenity POC");
            String buildSessionId = UUID.randomUUID() + "-" + getCurrentTime();
            ltOptions.addProperty("build", "Playwright-Serenity-POC");
            ltOptions.addProperty("w3c", true);
            ltOptions.addProperty("headless", false);
            ltOptions.addProperty("console", true);
            ltOptions.addProperty("plugin", "java-junit");
            capabilities.add("LT:Options", ltOptions);
            String caps = URLEncoder.encode(capabilities.toString(), "utf-8");
            cdpUrl = "wss://cdp.lambdatest.com/playwright?capabilities=" + caps;
            log.info("LambdaTest build session ID : {}", buildSessionId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return cdpUrl;
    }

    private static String getCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return tf.format(currentTime);
    }

}
