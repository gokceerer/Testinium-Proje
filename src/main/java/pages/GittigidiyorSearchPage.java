package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Random;

public class GittigidiyorSearchPage extends GittigidiyorPage {
    private final By pagerLocator = By.xpath("//div[contains(@class,'pager') and contains(@class,'hidden-m')]/ul/li");
    private final By productListLocator = By.xpath("//div[@class= 'clearfix']/ul/li");

    public GittigidiyorSearchPage(WebDriver driver) {
        super(driver);
    }

    public void goToPageNumber(int pageNumber) {
        Actions actions = new Actions(driver);
        List<WebElement> elements = driver.findElements(pagerLocator);
        boolean foundInList = false;
        for (WebElement e : elements) {
            if (e.getText().equals(Integer.toString(pageNumber))) {
                foundInList = true;
                actions.moveToElement(e.findElement(By.xpath(".//a"))).click().perform();
                break;
            }
        }

        if (!foundInList) {
            WebElement element = elements.get(elements.size() - 1).findElement(By.xpath(".//a"));
            actions.moveToElement(element).click().perform();
            goToPageNumber(pageNumber);
        }
    }

    public void selectRandomProduct() {
        List<WebElement> elements = driver.findElements(productListLocator);
        Random rand = new Random();
        int randProductId = rand.nextInt(elements.size());
        elements.get(randProductId).findElement(By.xpath(".//a")).click();
    }
}
