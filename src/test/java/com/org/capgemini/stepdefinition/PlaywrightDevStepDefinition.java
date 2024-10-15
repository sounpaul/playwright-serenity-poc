package com.org.capgemini.stepdefinition;

import com.org.capgemini.exception.TestRuntimeException;
import com.org.capgemini.pages.playwright.PlaywrightHomePage;
import io.cucumber.java.en.*;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.annotations.Steps;
import org.junit.Assert;

@Slf4j
public class PlaywrightDevStepDefinition {

    private boolean searchOperationResult = false;

    @Steps
    PlaywrightHomePage playwrightHomePage;

    @Given("the user is on Playwright documentation portal home page")
    public void the_user_is_playwright_documentation_page() {
        playwrightHomePage.validateHomePageLanding();
    }

    @When("the user select {string} from the preferred programming language dropdown")
    public void the_user_select_from_the_preferred_programming_language_dropdown(String dropdownName) {
        try {
            playwrightHomePage.selectPreferredLanguageDropdown(dropdownName);
        } catch (TestRuntimeException e) {
            log.error(String.valueOf(e));
        }
    }

    @When("the user clicks on the searchbar")
    public void the_user_clicks_on_the_searchbar() {
        playwrightHomePage.clickOnSearchBar();
    }

    @When("enters {string} as topic name")
    public void enters_locators_as_topic_name(String topicName) {
        searchOperationResult = playwrightHomePage.enterTopicName(topicName);
    }

    @Then("the search operation is marked as {string}")
    public void the_search_operation_is_marked_as(String opsResult) {
        Assert.assertEquals(opsResult.equals("Success"), searchOperationResult);
    }

    @Then("the user should be able to see documentation related for {string}")
    public void the_user_should_be_able_to_see_documentation_related_to_locators(String topicName) {
        playwrightHomePage.validateSearchResults(searchOperationResult, topicName);
    }
}
