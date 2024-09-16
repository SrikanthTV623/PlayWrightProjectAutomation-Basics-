package com.automation;

import com.microsoft.playwright.*;

import java.util.List;

public class EbayAssignment {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                //here by using set channel we are setting to Microsoft Edge Browser
                new BrowserType.LaunchOptions().setHeadless(false).setChannel("msedge")
        );
        Page page = browser.newPage();
        page.navigate("https://www.ebay.com/");

        Locator advancedSearch = page.locator("//a[@id='gh-as-a']");
        advancedSearch.click();

        page.fill("#_nkw","Java");

        Locator keywordOptions = page.locator("//select[@id='s0-1-17-4[0]-7[1]-_in_kw']");
        //keywordOptions.selectOption("Any words, any order");
        keywordOptions.selectOption("Exact words, exact order");

        Locator catogeriesField = page.locator("//select[@id='s0-1-17-4[0]-7[3]-_sacat']");
        catogeriesField.selectOption("Books & Magazines");

        page.fill("//input[contains(@aria-label,'minimum price range value')]","200");
        page.fill("//input[contains(@aria-label,'maximum price range value')]","1000");

        Locator selectNewCondition = page.locator("//label[contains(text(),'New')]/../span/input");
        selectNewCondition.click();

        Locator availableTo = page.locator("//select[@aria-label='Available to ']");
        availableTo.selectOption("India");

        Locator searchBtn = page.locator("//div[@class='adv-form__actions']//button[text()='Search']");
        searchBtn.click();

        Locator productElements = page.locator("//ul//a//span[@role='heading']");
        List<Locator> productTitlesList = productElements.all();

        System.out.println(productTitlesList.size());

        for (Locator ele : productTitlesList){
            System.out.println(ele.textContent());
        }

        //here we handle windows
        // page ----> tab (similar meaning in playwright)
        //here in this we give action to happen only when new tab opens
        //wait for the tab to be loaded once new tab opened
        //we extract the price
        Page newPage = page.waitForPopup(() -> {
            productTitlesList.get(0).click();
        });

        Locator priceOfProduct = newPage.locator("//div[@data-testid='x-price-primary']//span[@class=\"ux-textspans\"]");

        System.out.println(priceOfProduct.textContent());
        System.out.println(priceOfProduct.innerText());
        System.out.println(priceOfProduct.isVisible());
        System.out.println("===========================================================");

        browser.close();          // Close the specific browser instance
        playwright.close();       // Close all browsers and Playwright instance

    }
}
