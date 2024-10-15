package com.org.capgemini.pages.talent;

import com.microsoft.playwright.Page;
import com.org.capgemini.setup.Init;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.annotations.Step;

@Slf4j
public class CorporateDirectoryPage extends Init {

    private final String corporateDirectoryLink = "//a[@title='Corporate Directory']";
    private Page corporateDirectoryPage;

    @Step
    public void clickOnCorporateDirectory() {
        corporateDirectoryPage = context.waitForPage(() -> {
            page.click(corporateDirectoryLink);
            log.info("Clicked on Corporate Directory link");
        });
    }

    @Step
    public void enterEmployeeDetailsAndSearch() {
        corporateDirectoryPage.bringToFront();
        String employeeNameTxtBox = "//input[@id='aNR']";
        corporateDirectoryPage.fill(employeeNameTxtBox, System.getProperty("employeeNameToBeSearched"));
        String searchBtn = "//*[@id='searchbutton']";
        corporateDirectoryPage.click(searchBtn);
    }

}
