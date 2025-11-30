package com.automation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.util.ArrayList;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Ebay {
    public static void main(String[] args) {

        Playwright playwright = Playwright.create();

        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("--start-maximized");
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false).setChannel("msedge").setArgs(arguments));
        BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));

        Page page = context.newPage();

        page.navigate("https://www.ebay.com/");

        page.locator("div #vl-flyout-nav>ul>li:nth-child(3)>a").hover();
        page.locator("//nav[@aria-label='Most popular categories']//ul//li//a[contains(text(),'Smartphones')]")
                .click(new Locator.ClickOptions().setDelay(200));

        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cell Phones, Smart Watches & Accessories"))).isVisible();

        page.locator("img[alt='Apple']").click();

        Locator appleProductsPageHeading = page.locator("div .main-content section h1");
        assertThat(appleProductsPageHeading).containsText("Apple");

        //setting the storage capacity to 256GB from filter option
        page.locator("//button[@type='button']//span").getByText("Storage Capacity").click();
        page.locator("div[class*=filter-menu-button] ul>li>a label>span").getByText("256 GB").click();

        //setting the model to Apple 14 Pro Max from filter option
        page.locator("//button[@type='button']//span").getByText("Model").click();
        page.locator("//div[contains(@class,'filter-menu-button')]//ul/li/a//label/span[contains(text(),'iPhone 14 Pro Max')]").click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        page.close();
        playwright.close();
    }
}
