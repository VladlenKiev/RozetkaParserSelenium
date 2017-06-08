import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Main {
    static WebDriver driver;
    static Wait<WebDriver> wait;

    public static void main(String[] args) {

        System.setProperty("webdriver.gecko.driver","C:\\geckodriver.exe");
        driver = new FirefoxDriver();

        searchPage();
        String strSearch = "http://rozetka.com.ua/captain_morgan_5000299223017/p4968858/";
        driver.get(strSearch);
        wait = new WebDriverWait(driver, 50);


        WebElement elementReview = driver.findElement(By.className("g-rating-reviews"));
        System.out.println("reviewCount="+elementReview.getText());

        WebElement elementPrice = driver.findElement(By.className("detail-price-uah"));
        System.out.println("priceValue="+elementPrice.getText());

        testResult(elementReview,elementPrice);


        driver.quit();
    }

    @Test
    private static void testResult(WebElement elementReview, WebElement elementPrice){
        Assert.assertEquals("reviewCount must be equals 49", "50 отзывов", elementReview.getText());
        Assert.assertEquals("price must be equals 219", "219 грн", elementPrice.getText());

    }

    @Test
    private static String searchPage() {

        driver.get("http:/rozetka.com.ua/");
        wait = new WebDriverWait(driver, 30);

        driver.findElement(By.className("rz-header-search-input-text")).sendKeys("5000299223017\n");
        driver.findElement(By.className("btn-link-i")).click();


        // Wait for search to complete
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                return webDriver.getCurrentUrl() != null;
            }
        });
        String strResponseURL = driver.getCurrentUrl();
        Assert.assertEquals("http://rozetka.com.ua/captain_morgan_5000299223017/p4968858/",strResponseURL);
        return driver.getCurrentUrl();
    }
}
