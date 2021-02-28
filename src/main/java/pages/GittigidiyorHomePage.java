package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GittigidiyorHomePage extends GittigidiyorPage {
    private final By loginMenuLocator = By.xpath("//div[@data-cy='header-user-menu']");
    private final By loginButtonLocator = By.xpath("//a[@data-cy='header-login-button']");
    private final By accountMenuLocator = By.xpath("//div[@title='Hesabım']");
    private final By searchBarLocator = By.xpath("//input[@data-cy='header-search-input']");
    private final By searchButtonLocator = By.xpath("//button[@data-cy='search-find-button']");

    public GittigidiyorHomePage(WebDriver driver) {
        super(driver);
    }

    public void dismissCookiesMessage() {
        driver.findElement(By.xpath("//a[@class='tyj39b-3 gQhGuc']")).click();
    }

    public void hoverToLoginMenu() {
        Actions actions = new Actions(driver);
        WebElement loginMenuElement = driver.findElement(loginMenuLocator);
        actions.moveToElement(loginMenuElement).perform();
    }

    public void clickLoginButton() {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_DURATION);

        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButtonLocator));
        WebElement loginButton = driver.findElement(loginButtonLocator);
        loginButton.click();
    }

    public boolean checkIfLoggedIn() {
        try {
            driver.findElement(accountMenuLocator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void setTextInSearchBar(String text) {
        driver.findElement(searchBarLocator).sendKeys(text);
    }

    public void clickSearchButton() {
        driver.findElement(searchButtonLocator).click();
    }

}
