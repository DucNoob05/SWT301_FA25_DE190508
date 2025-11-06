package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegistrationFormPage extends BasePage {

    private static final String URL = "https://demoqa.com/automation-practice-form";

    public RegistrationFormPage(WebDriver driver) {
        super(driver);
    }

    // ===== Locators =====
    private By firstNameInput = By.id("firstName");
    private By lastNameInput = By.id("lastName");
    private By emailInput = By.id("userEmail");
    private By mobileInput = By.id("userNumber");
    private By addressTextArea = By.id("currentAddress");

    private By maleGender = By.cssSelector("label[for='gender-radio-1']");
    private By femaleGender = By.cssSelector("label[for='gender-radio-2']");
    private By otherGender = By.cssSelector("label[for='gender-radio-3']");

    private By sportsHobby = By.cssSelector("label[for='hobbies-checkbox-1']");
    private By readingHobby = By.cssSelector("label[for='hobbies-checkbox-2']");
    private By musicHobby = By.cssSelector("label[for='hobbies-checkbox-3']");

    private By submitButton = By.id("submit");

    private By resultModal = By.cssSelector(".modal-content");
    private By modalTitle = By.id("example-modal-sizes-title-lg");
    private By closeModalButton = By.id("closeLargeModal");

    public void navigate() {
        navigateTo(URL);
        // Ẩn bớt quảng cáo/iframe đè lên form
        removeAds();
    }

    // ===== Actions trên form =====

    public void setFirstName(String firstName) {
        type(firstNameInput, firstName);
    }

    public void setLastName(String lastName) {
        type(lastNameInput, lastName);
    }

    public void setEmail(String email) {
        type(emailInput, email);
    }

    public void setMobile(String mobile) {
        type(mobileInput, mobile);
    }

    public void setCurrentAddress(String address) {
        type(addressTextArea, address);
    }

    public void selectGender(String gender) {
        String g = gender.toLowerCase().trim();
        switch (g) {
            case "male":
            case "nam":
                click(maleGender);
                break;
            case "female":
            case "nữ":
                click(femaleGender);
                break;
            default:
                click(otherGender);
                break;
        }
    }

    public void selectHobby(String hobby) {
        String h = hobby.toLowerCase().trim();
        switch (h) {
            case "sports":
            case "thể thao":
                click(sportsHobby);
                break;
            case "reading":
            case "đọc sách":
                click(readingHobby);
                break;
            case "music":
            case "âm nhạc":
                click(musicHobby);
                break;
            default:
                break;
        }
    }

    public void submit() {
        click(submitButton);
    }

    // ===== Kết quả sau submit =====

    public boolean isResultModalDisplayed() {
        return isElementVisible(resultModal);
    }

    public String getModalTitle() {
        return getText(modalTitle);
    }

    public void closeModal() {
        click(closeModalButton);
    }

    // ===== Optional: HTML5 validation message =====

    public String getFirstNameValidationMessage() {
        WebElement firstName = waitForVisibility(firstNameInput);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].validationMessage;", firstName);
    }

    public String getMobileValidationMessage() {
        WebElement mobile = waitForVisibility(mobileInput);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].validationMessage;", mobile);
    }
}
