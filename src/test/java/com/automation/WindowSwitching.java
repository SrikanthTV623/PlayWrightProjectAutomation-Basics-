package com.automation;

import com.microsoft.playwright.*;

public class WindowSwitching {

    public static void main(String[] args) throws InterruptedException {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        BrowserContext context = browser.newContext();

        Page page = context.newPage();
        page.navigate("https://www.ebay.com/");

        page.locator("#gh-ac").fill("tv");
        page.locator("#gh-btn").click();

        page.locator("//a[@class='s-item__link'][not(@tabindex)]").first().click();

        Page newPage = page.waitForPopup(() -> {
            page.locator("//a[@class='s-item__link'][not(@tabindex)]").first().click();
        });

        System.out.println(newPage.locator("div.x-price-primary span").textContent());

        newPage.close();

        page.bringToFront();
        page.locator("#gh-ac").clear();
        page.locator("#gh-ac").fill("laptop");
        page.locator("#gh-btn").click();
        Thread.sleep(3000);

        playwright.close();
    }



}
