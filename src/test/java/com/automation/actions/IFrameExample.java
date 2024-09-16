package com.automation.actions;

import com.microsoft.playwright.*;

import java.util.List;

public class IFrameExample {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setChannel("msedge"));
        Page page = browser.newPage();
        page.navigate("https://www.globalsqa.com/demo-site/draganddrop/");

        FrameLocator frame1 = page.frameLocator(".demo-frame.lazyloaded").first();
        List<Locator> imageList = frame1.locator("//ul[@id='gallery']/li/img").all();
        Locator trash = frame1.locator("#trash");
        for (Locator ele : imageList) {
            ele.dragTo(trash);
        }

        playwright.close();
    }
}
