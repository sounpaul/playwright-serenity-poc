package com.org.capgemini.utils;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;

@Slf4j
public class LTCapabilities {

    private static final String DS = FileSystems.getDefault().getSeparator();
    private static final String root = System.getProperty("user.dir");
    private static final File buildSessionFile = new File(root + DS + "buildSession.txt");

    public static String getCapabilities() {
        String cdpUrl = "";
        try {
            String buildSessionId = FileUtils.readFileToString(buildSessionFile, StandardCharsets.UTF_8);
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
            ltOptions.addProperty("project", "Playwright Serenity POC" + buildSessionId);
            ltOptions.addProperty("build", "Playwright-Serenity-POC" + buildSessionId);
            ltOptions.addProperty("w3c", true);
            ltOptions.addProperty("headless", false);
            ltOptions.addProperty("console", true);
            ltOptions.addProperty("plugin", "java-junit");
            capabilities.add("LT:Options", ltOptions);
            String caps = URLEncoder.encode(capabilities.toString(), "utf-8");
            cdpUrl = "wss://cdp.lambdatest.com/playwright?capabilities=" + caps;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return cdpUrl;
    }

}
