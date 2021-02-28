package tests;

import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.GittigidiyorHomePage;
import pages.GittigidiyorLoginPage;
import pages.GittigidiyorProductPage;
import pages.GittigidiyorSearchPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GittigidiyorTest {
    private static final String expectedHomePageURL = "https://www.gittigidiyor.com/";
    private static final String expectedLoginPageURL = "https://www.gittigidiyor.com/uye-girisi";
    private static final String expectedSecondSearchPageURL = "https://www.gittigidiyor.com/arama/?k=bilgisayar&sf=2";

    private static WebDriver driver;
    private static Logger logger;

    private static GittigidiyorHomePage homePage;
    private static GittigidiyorLoginPage loginPage;
    private static GittigidiyorSearchPage searchPage;
    private static GittigidiyorProductPage productPage;

    @BeforeClass
    public static void onTestStarted() {
        //Initializing driver
        System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
        driver = new ChromeDriver();

        //Initializing logger
        logger = Logger.getLogger(GittigidiyorTest.class);

        homePage = new GittigidiyorHomePage(driver);
        loginPage = new GittigidiyorLoginPage(driver);
        searchPage = new GittigidiyorSearchPage(driver);
        productPage = new GittigidiyorProductPage(driver);

        logger.info("Initialization finished.");
    }

    @AfterClass
    public static void onTestFinished() {
        logger.info("Test ended.");
        driver.close();
    }

    @Test
    public void Step1_homePageTest() {
        //Open home page and check if it is the requested page
        driver.get("http://www.gittigidiyor.com");
        logger.info("Page that is getting tested: " + driver.getTitle());
        driver.manage().window().maximize();
        boolean homePageCheck = expectedHomePageURL.equals(driver.getCurrentUrl());
        if (!homePageCheck) {
            logger.error("Opening Home Page Failed");
        } else {
            homePage.dismissCookiesMessage();
        }
        Assert.assertEquals("Home Page Check Failed", expectedHomePageURL, driver.getCurrentUrl());
    }

    @Test
    public void Step2_redirectionToLoginTest() {
        //Hover to login menu, click on login button then check if the redirected page is the requested login page
        homePage.hoverToLoginMenu();
        homePage.clickLoginButton();

        logger.info("Redirecting to page: " + driver.getTitle());
        boolean loginPageRedirectionCheck = expectedLoginPageURL.equals(driver.getCurrentUrl());
        if (!loginPageRedirectionCheck) {
            logger.error("Login Page Redirection Failed");
        }
        Assert.assertEquals("Login Page Redirection Failed", expectedLoginPageURL, driver.getCurrentUrl());
    }

    @Test
    public void Step3_loginTest() {
        //Login with credentials and check if login is successful
        loginPage.setTextInUsernameField("testuserfortestiniumproject@gmail.com");
        loginPage.setTextInPasswordField("testuser_0");
        loginPage.clickLoginButton();

        boolean loginCheck = homePage.checkIfLoggedIn();
        if (!loginCheck) {
            logger.error("Login Failed");
        }
        Assert.assertTrue("Login Check Failed", loginCheck);
    }

    @Test
    public void Step4_searchTest() {
        logger.info("Redirecting to page: " + driver.getTitle());

        //Search for "bilgisayar", go to the second page and check if the requested page is opened
        homePage.setTextInSearchBar("bilgisayar");
        homePage.clickSearchButton();

        logger.info("Redirecting to page: " + driver.getTitle());
    }

    @Test
    public void Step5_pageTest() {
        searchPage.goToPageNumber(2);
        logger.info("Redirecting to page: " + driver.getTitle());

        boolean directionToSecondPageCheck = expectedSecondSearchPageURL.equals(driver.getCurrentUrl());
        if (!directionToSecondPageCheck) {
            logger.error("Direction to Second Page Failed");
        }
        Assert.assertEquals("Direction to Second Page Failed", expectedSecondSearchPageURL, driver.getCurrentUrl());
    }

    @Test
    public void Step6_productSelectionTest() {
        //Select a random product from the product list and add it to cart.
        //Then compare prices in cart and product page
        searchPage.selectRandomProduct();
        logger.info("Redirecting to page: " + driver.getTitle());

        logger.info("Adding product to cart");

        productPage.addProductToCart();

        boolean priceInCartCheck = productPage.checkPriceInCart();
        if (!priceInCartCheck) {
            logger.error("Price Check in Cart Failed");
        }
        Assert.assertTrue("Price Check in Cart Failed", priceInCartCheck);
    }

    @Test
    public void Step7_incrementProductCountTest() {
        //Increment product count by 1 to make it 2 then check if the value changed to 2
        logger.info("Product count is incremented");
        productPage.incrementCountBy(1);

        boolean productCountCheck = productPage.checkProductCountEqualTo(2);
        if (!productCountCheck) {
            logger.error("Increment Check Failed");
        }
        Assert.assertTrue("Increment Check Failed", productCountCheck);
    }

    @Test
    public void Step8_productRemovalFromCartTest() {
        //Remove product from the cart then check if the cart is empty
        logger.info("Removing product from cart");
        productPage.removeProductFromCart();

        boolean emptyCartCheck = productPage.checkIfCartIsEmpty();
        if (!emptyCartCheck) {
            logger.error("Cart Empty Check Failed");
        }
        Assert.assertTrue("Cart Empty Check Failed", emptyCartCheck);
    }
}
