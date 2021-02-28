package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GittigidiyorLoginPage extends GittigidiyorPage {
    private final By usernameFieldLocator = By.id("L-UserNameField");
    private final By passwordFieldLocator = By.id("L-PasswordField");
    private final By loginButtonLocator = By.id("gg-login-enter");

    public GittigidiyorLoginPage(WebDriver driver) {
        super(driver);
    }

    public void setTextInUsernameField(String text) {
        driver.findElement(usernameFieldLocator).sendKeys(text);
    }

    public void setTextInPasswordField(String text) {
        driver.findElement(passwordFieldLocator).sendKeys(text);
    }

    public void clickLoginButton() {
        driver.findElement(loginButtonLocator).click();
    }
}
