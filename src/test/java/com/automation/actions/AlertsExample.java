package com.automation.actions;

import com.microsoft.playwright.*;

public class AlertsExample {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false).setChannel("msedge")
        );
        Page page = browser.newPage();
        page.navigate("http://the-internet.herokuapp.com/javascript_alerts");

        page.waitForLoadState();
        page.onDialog(dialog -> {
            System.out.println("Dialog type: " + dialog.type());
            System.out.println("Dialog message: " + dialog.message());

            switch (dialog.type()) {
                case "alert":
                    System.out.println("Handling alert dialog");
                    dialog.accept();
                    break;
                case "confirm":
                    System.out.println("Handling confirm dialog");
                    dialog.accept();
                    break;
                case "prompt":
                    System.out.println("Handling prompt dialog");
                    dialog.accept("ust");
                    System.out.println(dialog.message());
                    break;
                default:
                    System.out.println("Unknown dialog type: " + dialog.type());
                    break;
            }
        });

        page.getByText("Click for JS Alert").click();

        page.getByText("Click for JS Confirm").click();

        page.getByText("Click for JS Prompt").click();

    }
}

