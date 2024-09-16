package com.automation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;

import java.nio.file.Paths;
import java.util.Arrays;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class OrangeHRM {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false).setChannel("msedge")
        );
        BrowserContext context = browser.newContext();
        Page page = context.newPage();
        page.navigate("https://opensource-demo.orangehrmlive.com/");
        page.getByPlaceholder("username").fill("Admin");
        page.getByPlaceholder("password").fill("admin123");

        page.screenshot(
                new Page.ScreenshotOptions()
                        .setFullPage(true)
                        .setMask(Arrays.asList(page.getByPlaceholder("username")))
                        .setMaskColor("Orange")
                        .setPath(Paths.get("./target/screenshot1.png"))
        );
        page.locator("//button[@type='submit']").click();

        // Wait for the page to be fully loaded
        page.waitForLoadState(LoadState.NETWORKIDLE);

        //Takes screenshot
        page.screenshot(
                new Page.ScreenshotOptions()
                        .setFullPage(true)
                        .setPath(Paths.get("./target/screenshot2.png"))
        );

        Page newPage = context.newPage();
        newPage.navigate("https://opensource-demo.orangehrmlive.com/");

        assertThat(newPage.locator(".oxd-userdropdown-tab")).isVisible();

        BrowserContext newContext = browser.newContext();
        Page page1 = newContext.newPage();
        page1.navigate("https://opensource-demo.orangehrmlive.com/");
        assertThat(page1.locator("//button[@type='submit']")).isVisible();

        page1.getByPlaceholder("username").screenshot(new Locator.ScreenshotOptions()
                .setPath(Paths.get("./target/screenshot3.png")));

        browser.close();
        playwright.close();
    }
}

