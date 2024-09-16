package com.automation.actions;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class checkBox {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );

        Page checkBoxPage = browser.newPage();
        checkBoxPage.navigate("https://the-internet.herokuapp.com/");

        //click on checkbox
        checkBoxPage.locator("//a[text()='Checkboxes']").click();

        //click on checkbox and Assert the checked button
        Locator checkBox1 = checkBoxPage.locator("(//input[@type='checkbox'])[1]");
        checkBox1.check();
        assertThat(checkBox1).isChecked();

        //uncheck the checked button and Assert the checked button
        Locator checkBox2 = checkBoxPage.locator("(//input[@type='checkbox'])[2]");
        checkBox2.uncheck();
        assertThat(checkBox2).isChecked(new LocatorAssertions.IsCheckedOptions().setChecked(false));

    }
}
