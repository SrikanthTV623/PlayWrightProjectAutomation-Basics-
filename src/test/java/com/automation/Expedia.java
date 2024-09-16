package com.automation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class Expedia {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        Page page = browser.newPage();
        page.navigate("https://www.expedia.co.in/");

        assertThat(page.locator("a.uitk-header-brand-logo")).isVisible();

        Locator selectFlights = page.locator("//span[text()='Flights']");
        selectFlights.click();

        Locator leavingFromField = page.locator("//button[@aria-label='Leaving from']");
        leavingFromField.click();
        page.fill("//input[@id='origin_select']","Trivandrum");
        page.locator("(//button[contains(@aria-label,'Trivandrum')])[1]").click();

        Locator goingTo = page.locator("//button[@aria-label='Going to']");
        goingTo.click();
        page.fill("//input[@id='destination_select']","Bangalore");
        page.locator("(//button[contains(@aria-label,'Bangalore')])[1]").click();

        Locator clickOnDateField = page.locator("//button[@data-testid='uitk-date-selector-input1-default']");
        clickOnDateField.click();

        String setFromDateTxt = "Friday 6 September 2024";
        String setFromDateXPath = "//div/div[contains(@aria-label,'%s')]//following-sibling::div";
        String setFromDateFormattedXPath = String.format(setFromDateXPath,setFromDateTxt);
        Locator setStartDate = page.locator(setFromDateFormattedXPath);
        setStartDate.click();

        String setToDateTxt = "Sunday 8 September 2024";
        String setToDateXPath = "//div/div[contains(@aria-label,'%s')]//following-sibling::div";
        String setToDateFormattedXPath = String.format(setToDateXPath,setToDateTxt);
        Locator setEndDate = page.locator(setToDateFormattedXPath);
        setEndDate.click();

        page.locator("//button[@data-stid='apply-date-selector']").click();

        Locator selectTravellersCount = page.locator("//button[@data-stid='open-room-picker']");
        selectTravellersCount.click();

        int countOfAdults = 3;
        int noOfChildren = 2;
        int childrenAge[] = {12,8};

        String typeOfPerson1 = "Adults";
        String typeOfPerson2 = "Children";

        String setIncreasePersonField = "(//div/label/span[text()='%s']/../../div/button/span)[2]";
        String setCountOfPersonField = "//div/label/span[text()='%s']/../../div/input[@value='%d']";

        Locator countOfAdultsTxtOnPage = page.locator(String.format(setCountOfPersonField,typeOfPerson1,countOfAdults));
        Locator increaseAdultsPlusButton = page.locator(String.format(setIncreasePersonField,typeOfPerson1));
        while(!countOfAdultsTxtOnPage.isVisible()){
            increaseAdultsPlusButton.click();
        }

        Locator countOfChildrenTxtOnPage = page.locator(String.format(setCountOfPersonField,typeOfPerson2,noOfChildren));
        Locator increaseChildrenPlusButton = page.locator(String.format(setIncreasePersonField,typeOfPerson2));
        int childNo=0;
        while(!countOfChildrenTxtOnPage.isVisible()){
            increaseChildrenPlusButton.click();
            Locator setAgeOfChildrenOption = page.locator(String.format("#age-traveler_selector_children_age_selector-%s",childNo));
            if(setAgeOfChildrenOption.isVisible()){
                setAgeOfChildrenOption.selectOption(String.valueOf(childrenAge[childNo]));
            }
            childNo++;
        }

        page.locator("#travelers_selector_done_button").click();

        Locator searchBtn = page.locator("#search_button");
        searchBtn.click();

        //both the lines of code is same we use anyone

        //page.locator("#sort-filter-dropdown-SORT").waitFor(new Locator.WaitForOptions().setTimeout(600000));

        page.locator("#sort-filter-dropdown-SORT")
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        Locator sortByDropDown = page.locator("#sort-filter-dropdown-SORT");
        sortByDropDown.selectOption("Price (lowest to highest)");

        Locator priceDisplayedInSearchResults = page.locator("//ul[@data-test-id='listings']/li[1]//div[@class='uitk-price-lockup align-end']//span[2]");
        String priceDisplayedInSearchResultsValue = priceDisplayedInSearchResults.innerText();

        Locator priceDisplayedForThatDate = page.locator("//span[contains(text(),'Fri, 6 Sept')]//following-sibling::span");
        String priceDisplayedForThatDateValue = priceDisplayedForThatDate.innerText();

        System.out.println(priceDisplayedInSearchResultsValue+" "+priceDisplayedForThatDateValue);

        assertThat(page.locator(priceDisplayedInSearchResultsValue)).containsText(priceDisplayedForThatDateValue);

        //playwright.close();

    }
}
