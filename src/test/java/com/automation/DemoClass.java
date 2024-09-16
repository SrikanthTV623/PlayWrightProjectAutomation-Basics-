package com.automation;

import com.microsoft.playwright.*;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DemoClass {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        Page page = browser.newPage();
        page.navigate("https://www.saucedemo.com/");

        Locator usernameInput = page.locator("#user-name");
        Locator passwordInput = page.locator("#password");
        Locator loginButton = page.locator("#login-button");

        usernameInput.fill("standard_user");
        passwordInput.fill("secret_sauce");
        loginButton.click();

        assertThat(page.locator("span.title")).hasText("Products");

        List<Locator> listOfPrices = page.locator("div.inventory_item_price").all();
        for (Locator price:listOfPrices){
            System.out.println(price.allTextContents()+" "+price.innerText());
        }

        List<Locator> addToCartBtnOfProducts = page.locator("//button[contains(@id,'add-to-cart')]").all();
        for (int i=0;i<3;i++){
            addToCartBtnOfProducts.get(i).click();
        }

        assertThat(page.locator("//span[@data-test='shopping-cart-badge']")).hasText("3");

        Locator cartIcon = page.locator(".shopping_cart_link");
        cartIcon.click();

        List<Locator> productsNamesAddedInCart = page.locator(".inventory_item_name").all();
        List<Locator> productsPricesAddedInCart = page.locator(".inventory_item_price").all();

        for(int j=0;j<productsNamesAddedInCart.size();j++){
            System.out.println(productsNamesAddedInCart.get(j).innerText()+"    "+productsPricesAddedInCart.get(j).innerText());
        }

        Locator checkOutBtn = page.locator("#checkout");
        checkOutBtn.click();

        page.fill("#first-name","Rajesh");
        page.fill("#last-name","S");
        page.fill("#postal-code","656565");

        page.locator("#continue").click();

        assertThat(page.locator("//span[@data-test='title']")).isVisible();
        assertThat(page.locator("//span[@data-test='title']")).hasText("Checkout: Overview");

        page.locator("#finish").click();

        Locator orderPlacedText = page.locator("h2.complete-header");
        System.out.println(orderPlacedText.innerText());

        playwright.close();
        System.out.println("Execution Done");
    }
}
