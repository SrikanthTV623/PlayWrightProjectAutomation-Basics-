package com.automation.actions;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;

public class ActionTypes {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        Page page = browser.newPage();
        page.navigate("https://www.ebay.com/");

        // Text input
        page.getByLabel("Search for anything").fill("TV");

        page.locator("#gh-btn").click();

        page.getByLabel("Advanced Search").click();

        Locator searchValueFill = page.locator("#_nkw");
        searchValueFill.press("Shift+T");
        searchValueFill.press("Shift+V");

        //page.fill("#_nkw","TV");

        // Single selection matching the value
        page.getByLabel("Keyword options").click();
        page.getByLabel("Keyword options").selectOption("4");

        // Single selection matching the label
        page.getByLabel("In this category").click();
        page.getByLabel("In this category").selectOption(new SelectOption().setLabel("Computers/Tablets & Networking"));

        page.locator("//div[@class='adv-form__actions']//button[text()='Search']").scrollIntoViewIfNeeded();
        page.locator("//a[text()='Clear options']/../button").click();


        page.locator("//a[@class='s-item__link'][not(@tabindex)]").first().focus();

        //page.locator("//a[@class='s-item__link'][not(@tabindex)]").first().click();


    }
}
