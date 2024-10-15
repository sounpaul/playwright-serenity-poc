package com.org.capgemini.stepdefinition;


import com.org.capgemini.pages.talent.CorporateDirectoryPage;
import com.org.capgemini.pages.talent.TalentHomePage;
import io.cucumber.java.en.*;
import net.serenitybdd.annotations.Steps;

public class TalentSearchStepDefinition {

    @Steps
    TalentHomePage homePage;

    @Steps
    CorporateDirectoryPage corporateDirectoryPage;

    @Given("the user is inside the talent home page")
    public void the_capgemini_talent_page_is_launched_in_the_browser() {
        homePage.validateWhetherHomePageIsLaunched();
    }
    @When("the user navigates to global links page")
    public void the_user_navigates_to_global_links_page() {
        homePage.clickOnGlobalLinks();
    }
    @When("the user navigates to corporate directory page")
    public void the_user_navigates_to_corporate_directory_page() {
        corporateDirectoryPage.clickOnCorporateDirectory();
    }
    @When("the user enters the name of the employee in the text box")
    public void the_user_enters_the_name_of_the_employee_in_the_text_box() {
//        corporateDirectoryPage.enterEmployeeDetailsAndSearch();
    }
    @Then("fetch the details of the given employee")
    public void fetch_the_details_of_the_given_employee() {

    }

}
