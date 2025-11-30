package com.automation;

import com.microsoft.playwright.*;

import java.util.ArrayList;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SauceDemo {
    public static void main(String[] args) {

        Playwright playwright = Playwright.create();

        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("--start-maximized");
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(arguments));
        BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));

        Page page = context.newPage();

        page.navigate("https://www.saucedemo.com/");

        page.locator("#user-name").fill("standard_user");
        page.locator("#password").fill("secret_sauce");

        page.locator("#login-button").click();

        assertThat(page.locator(".app_logo")).isVisible();

        Locator listOfAddToCartButton = page.locator(".inventory_list button");

        for (int i = 0; i < listOfAddToCartButton.count(); i++){
            listOfAddToCartButton.nth(i).click(new Locator.ClickOptions().setDelay(100));
        }

        Locator shoppingCartIcon = page.locator("#shopping_cart_container > a");
        shoppingCartIcon.click();

        assertThat(page.getByText("Your Cart")).isVisible();

        page.locator("#checkout").click();

        page.getByPlaceholder("First Name").fill("Srikanth");
        page.getByPlaceholder("Last Name").fill("TV");
        page.getByPlaceholder("Zip/Postal Code").fill("515201");

        page.locator("[type='submit']").click();

        page.locator("button:has-text('Finish')").click();

        assertThat(page.locator("[id='checkout_complete_container'] > h2")).hasText("Thank you for your order!");


        page.close();
        playwright.close();
    }
}
