package pages;

import org.openqa.selenium.WebDriver;

public abstract class GittigidiyorPage {
    protected WebDriver driver;
    protected static final int TIMEOUT_DURATION = 10;

    public GittigidiyorPage(WebDriver driver) {
        this.driver = driver;
    }

}
