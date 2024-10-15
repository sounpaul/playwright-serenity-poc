package com.org.capgemini.setup;

import com.microsoft.playwright.*;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.core.Serenity;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class Init {

    public static Page page;
    private static Browser browser;
    public static BrowserContext context;
    private static final String DS = FileSystems.getDefault().getSeparator();
    private static final String root = System.getProperty("user.dir");

    public static void launchBrowser() {
        boolean headless = System.getProperty("headless").equals("true");
        String url = System.getProperty("webdriver.base.url");
        Playwright playwright = Playwright.create();
        BrowserType chrome = playwright.chromium();
        browser = chrome.launch(new BrowserType.LaunchOptions().setHeadless(headless));
        /**
         ClientCertificate certificate = new ClientCertificate("signincert.capgemini.com:443");
         certificate.setPfxPath(Paths.get(root + DS + "sounpaulcert.cer"));
         Browser.NewContextOptions options = new Browser.NewContextOptions().setClientCertificates(List.of(certificate));
         context = browser.newContext(options);
         **/
        context = browser.newContext();
        page = context.newPage();
        page.navigate(url);
        log.info("Browser launched and opened URL : {}", url);
    }

    public static void closeBrowser() {
        context.close();
        browser.close();
        log.info("Browser closed");
    }

    public static void takeScreenshot(Page page, String fileName) {
//        readDefaultHTMLTemplate("default-html-template.html").replace("image", screenshotPath.toAbsolutePath().toString())
        try {
            String scenarioName = Serenity.sessionVariableCalled("scenarioName");
            Path screenshotPath = Paths.get(root + DS + "target" + DS + "site" + DS + "serenity" + DS + scenarioName + "-" + fileName + ".png");
            page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
            Serenity.recordReportData().asEvidence().withTitle(fileName).downloadable().fromFile(screenshotPath);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static String readDefaultHTMLTemplate(String template) {
        String val = "";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = loader.getResourceAsStream(template)) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is), StandardCharsets.UTF_8);
            val = scanner.useDelimiter("\\A").next();
        } catch (Exception e) {
            log.error("Exception occurred");
            val = "exception occurred while reading template";
        }
        return val;
    }
}
