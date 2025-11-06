package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void navigateTo(String url) {
        driver.get(url);
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void type(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    // ==== SỬA HÀM CLICK: scroll + fallback JS click nếu bị chặn ====
    protected void click(By locator) {
        WebElement element = waitForClickable(locator);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            // scroll element vào giữa màn hình rồi dùng JS click
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", element);
        }
    }

    protected String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    protected boolean isElementVisible(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    // ==== MỚI: hàm ẩn quảng cáo/iframe ở DemoQA ====
    protected void removeAds() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // 1/ Ẩn banner fixed ở dưới cùng
            js.executeScript(
                    "var fixedBan = document.getElementById('fixedban');" +
                            "if (fixedBan) { fixedBan.style.display='none'; }");

            // 2/ Ẩn footer nếu có
            js.executeScript(
                    "var footer = document.querySelector('footer');" +
                            "if (footer) { footer.style.display='none'; }");

            // 3/ Ẩn các iframe quảng cáo google
            js.executeScript(
                    "var iframes = document.querySelectorAll('iframe[id^=\"google_ads_iframe\"]');" +
                            "for (var i = 0; i < iframes.length; i++) { " +
                            "   iframes[i].style.display='none'; " +
                            "}");
        } catch (Exception e) {
            // bỏ qua nếu không có quảng cáo
        }
    }
}
