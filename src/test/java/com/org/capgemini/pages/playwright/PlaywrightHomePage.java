package com.org.capgemini.pages.playwright;

import com.org.capgemini.exception.TestRuntimeException;
import com.org.capgemini.setup.Init;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.Serenity;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class PlaywrightHomePage extends Init {

    @Step
    public void validateHomePageLanding() {
        String getStartedBtn = "//div[@id='__docusaurus_skipToContent_fallback']/header/div/div/a";
        Assert.assertTrue(page.isEnabled(getStartedBtn));
        log.info("Playwright documentation website home page visible");
        takeScreenshot(page, "homepageVisible");
    }

    @Step
    public void selectPreferredLanguageDropdown(String dropdownName) throws TestRuntimeException {
        String dropDownBtn = "//*[@id='__docusaurus']/nav/div[1]/div[1]/div";
        page.hover(dropDownBtn);
        try {
            Thread.sleep(2000);
            takeScreenshot(page, "dropDownSelection");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        List<String> language = Arrays.asList("Node.js", "Python", "Java", ".NET");
        int index = IntStream.range(0, language.size())
                .filter(i -> language.get(i).equals(dropdownName))
                .findFirst()
                .orElse(-1);
        if (index != -1) {
            String preferredLanguageOption = "//div[@id='__docusaurus']/nav/div[1]/div[1]/div/ul/li[index]/a".replace("index", String.valueOf(index + 1));
            page.click(preferredLanguageOption);
            page.waitForLoadState();
            String languageSelectHeaderTxt = "//*[@id='__docusaurus']/nav/div[1]/div[1]/a[1]/b";
            Assert.assertEquals("Language selected text does not match", dropdownName.equals("Node.js") ? "Playwright" : "Playwright for " + dropdownName, page.locator(languageSelectHeaderTxt).textContent());
            log.info("\"{}\" selected as preferred language from dropdown", dropdownName);
            takeScreenshot(page, "dropDownSelected");
        } else {
            throw new TestRuntimeException("Trying to select invalid language. Playwright does not support " + dropdownName);
        }
    }

    @Step
    public void clickOnSearchBar() {
        String searchBar = "//*[@id='__docusaurus']/nav/div[1]/div[2]/div[2]/button/span[1]/span";
        page.click(searchBar);
        log.info("Clicked on search bar");
    }

    @Step
    public boolean enterTopicName(String topicName) {
        String topicSearchTxtBox = "//*[@id='docsearch-input']";
        boolean searchSuccess = false;
        page.click(topicSearchTxtBox);
        takeScreenshot(page, "searchBarClicked");
        page.fill(topicSearchTxtBox, topicName);
        takeScreenshot(page, "topicNameEntered");
        log.info("\"{}\" topic name entered", topicName);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        String firstSearchResult = "//*[@id='docsearch-item-0']/a/div/div[2]/span";
        if (page.isVisible(firstSearchResult)) {
            takeScreenshot(page, "searchResultsVisible");
            page.keyboard().press("Enter");
            searchSuccess = true;
        }
        page.waitForLoadState();
        return searchSuccess;
    }

    @Step
    public void validateSearchResults(boolean searchOpsResult, String topicName) {
        if (searchOpsResult) {
            String headerText = "//*[@id='__docusaurus_skipToContent_fallback']/div/div/main/div/div/div[1]/div/article/div[2]/header/h1";
            page.waitForSelector(headerText);
            Assert.assertEquals("Expected topic page is not opened", topicName, page.locator(headerText).textContent());
            log.info("Expected search results on topic \"{}\" is opened", topicName);
            takeScreenshot(page, "searchResultsOpened");
        } else {
            log.warn("Not validating searched topics as there were no search results");
        }
    }

}
