package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class GittigidiyorProductPage extends GittigidiyorPage {
    private final By addToCartButtonLocator = By.id("add-to-basket");
    private final By productPriceWithoutDiscountTextLocator = By.id("sp-price-highPrice");
    private final By productPriceWithDiscountTextLocator = By.id("sp-price-lowPrice");
    private final By productSubtitleTextLocator = By.id("sp-subTitle");
    private final By productTitleTextLocator = By.id("sp-title");
    private final By cartItemsLocator = By.xpath("//div[@class = 'user-cart-items-container']/ul/li");
    private final By incrementCountButtonLocator = By.xpath(".//a[@aria-label='Adeti Artır']");
    private final By itemCountLocator = By.id("buyitnow_adet");
    private final By cartButtonLocator = By.xpath("//div[@class='basket-container robot-header-iconContainer-cart']");
    private final By productTitleLocator = By.xpath(".//p[@class='product-title']");
    private final By productPriceLocator = By.xpath("../p[@class='product-price']/span");
    private final By cartItemDeleteButtonLocator = By.xpath("//a[@class='header-cart-delete-button']");
    private final By emptyCartDivLocator = By.xpath("//div[contains(@class,'header-cart-empty gg-d-24')]");

    public GittigidiyorProductPage(WebDriver driver) {
        super(driver);
    }

    public void addProductToCart() {
        driver.findElement(addToCartButtonLocator).click();
    }

    public boolean checkPriceInCart() {
        WebElement priceTextElement = driver.findElement(productPriceWithDiscountTextLocator);
        if (priceTextElement.getText().equals("")) {
            priceTextElement = driver.findElement(productPriceWithoutDiscountTextLocator);
        }
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_DURATION);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemsLocator));
        List<WebElement> cartElements = driver.findElements(cartItemsLocator);


        WebElement productNameElement = driver.findElement(productSubtitleTextLocator);
        if (productNameElement.getText().equals("")) {
            productNameElement = driver.findElement(productTitleTextLocator);
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemsLocator));

        for (WebElement e : cartElements) {
            WebElement cartElement = e.findElement(productTitleLocator);

            if (cartElement.getText().equals(productNameElement.getText())) {
                WebElement priceElement = cartElement.findElement(productPriceLocator);
                if (priceElement.getText().equals(priceTextElement.getText())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void incrementCountBy(int count) {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_DURATION);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(cartItemsLocator));
        for (int i = 0; i < count; i++) {
            driver.findElement(incrementCountButtonLocator).click();
        }
    }

    public boolean checkProductCountEqualTo(int count) {
        int value = Integer.parseInt(driver.findElement(itemCountLocator).getAttribute("value"));
        return value == count;
    }

    public void removeProductFromCart() {
        WebElement productNameElement = driver.findElement(productSubtitleTextLocator);
        if (productNameElement.getText().equals("")) {
            productNameElement = driver.findElement(productTitleTextLocator);
        }
        List<WebElement> cartElements = driver.findElements(cartItemsLocator);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(cartButtonLocator)).perform();

        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_DURATION);

        for (WebElement e : cartElements) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productTitleLocator));
            WebElement cartElement = e.findElement(productTitleLocator);

            if (cartElement.getText().equals(productNameElement.getText())) {
                actions.moveToElement(cartElement).perform();
                wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemDeleteButtonLocator));
                WebElement deleteButtonElement = cartElement.findElement(cartItemDeleteButtonLocator);
                deleteButtonElement.click();
                break;
            }
        }
    }

    public boolean checkIfCartIsEmpty() {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_DURATION);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartDivLocator));
            return true;
        } catch (TimeoutException ex) {
            return false;
        }

    }
}
