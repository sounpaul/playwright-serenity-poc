package com.org.capgemini.pages.talent;

import com.org.capgemini.setup.Init;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.annotations.Step;
import org.junit.Assert;

@Slf4j
public class TalentHomePage extends Init {

    private final String globalLinks = "//li[@id='toolcenterglobal_choice']/button";

    @Step
    public void validateWhetherHomePageIsLaunched() {
        page.waitForLoadState();
        String closePopupBtn = "//div[@id='cookie_info']/div[2]/a";
        page.click(closePopupBtn);
        Assert.assertTrue(page.isVisible(globalLinks));
        log.info("Global links tab is accessible on Talent home page");
    }

    @Step
    public void clickOnGlobalLinks() {
        page.click(globalLinks);
        log.info("Clicked on global links tab");
    }

}
