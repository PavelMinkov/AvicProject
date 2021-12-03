package avic;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class AvicSmokeTests {

    private WebDriver driver;

    @BeforeTest
    public void profileSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void testsSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/");
    }

    @Test(priority = 1)
    public void checkThatUrlContainsSearchWord() {
        driver.findElement(By.xpath("//input[contains(@id,'input_search')]")).sendKeys("Samsung Galaxy");
        driver.findElement(By.xpath("//button[contains(@class,'button-reset search-btn')]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        assertTrue(driver.getCurrentUrl().contains("query=Samsung+Galaxy"));
    }

    @Test(priority = 2)
    public void checkElementsAmountOnSearchPage() {
        driver.findElement(By.xpath("//input[contains(@id,'input_search')]")).sendKeys("Samsung Galaxy");
        driver.findElement(By.xpath("//button[contains(@class,'button-reset search-btn')]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        List<WebElement> elementList = driver.findElements(By.xpath("//div[contains(@class,'prod-cart__descr')]"));
        assertEquals(elementList.size(), 12);
    }

    @Test(priority = 3)
    public void checkThatSearchResultsContainsSearchWord() {
        driver.findElement(By.xpath("//input[contains(@id,'input_search')]")).sendKeys("Samsung Galaxy");
        driver.findElement(By.xpath("//button[contains(@class,'button-reset search-btn')]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        List<WebElement> elementList = driver.findElements(By.xpath("//div[contains(@class,'prod-cart__descr')]"));
        for (WebElement webElement : elementList) {
            assertTrue(webElement.getText().contains("Galaxy"));
        }
    }

    @Test(priority = 4)
    public void checkAddToCart() {
        driver.findElement(By.xpath("//span[contains(@class,'sidebar-item')]//i[contains(@class,'icon')]")).click();
        driver.findElement(By.xpath("//span[contains(@class,'sidebar-item')]//span[contains(text(),'Apple')]")).click();
        driver.findElement(By.xpath("//div[contains(@class, 'brand-box__title')]//a[contains(text(),'iPhone')]")).click();
        new WebDriverWait(driver, 30).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        driver.findElement(By.xpath("//a[contains(@title,'Slim Box Purple (MHDF3)')][contains(@style,'800080')]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//a[contains(@class,'prod-cart__buy')][contains(@data-ecomm-cart,'Slim Box Purple (MHDF3)')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));
        driver.findElement(xpath("//div[contains(@class,'btns-cart-holder')]//a[contains(@class,'btn--orange')]")).click();
        String actualProductsCountInCart = driver.findElement(By.xpath("//div[contains(@class,'header-bottom__cart')]//div[contains(@class,'cart_count')]")).getText();
        assertEquals(actualProductsCountInCart, "1");
    }

    @Test(priority = 5)
    public void checkThatBrandContainsSearchBrand() {
        driver.findElement(By.xpath("//div[contains(@class,'partner-box')]//img[contains(@alt,'Asus')]")).click();
        driver.findElement(By.xpath("//a[contains(@href,'smartfonyi')][contains(@class,'brand__more')]")).click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        List<WebElement> elementList = driver.findElements(By.xpath("//div[contains(@class,'prod-cart__descr')]"));
        for (WebElement webElement : elementList) {
            assertTrue(webElement.getText().contains("ASUS"));
        }
    }

    @Test(priority = 6)
    public void checkAddMoreThatOneToCart() throws InterruptedException {
        driver.findElement(By.xpath("//span[contains(@class,'sidebar-item')]//i[contains(@class,'icon')]")).click();
        driver.findElement(By.xpath("//span[contains(@class,'sidebar-item')]//span[contains(text(),'Apple Store')]")).click();
        driver.findElement(By.xpath("//div[contains(@class, 'brand-box__title')]//a[contains(text(),'MacBook')]")).click();
        new WebDriverWait(driver, 30).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        driver.findElement(By.xpath("//a[contains(@title,'(MGN63)')][contains(@style,'#a9a9a9')]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//a[contains(@class,'prod-cart__buy')][contains(@data-ecomm-cart,'Apple MacBook Air 13 Space Gray Late 2020 (MGN63)')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));
        driver.findElement(xpath("//div[contains(@class,'btns-cart-holder')]//a[contains(@class,'btn--orange')]")).click();

        driver.findElement(By.xpath("//span[contains(@title,'(MXK62)')][contains(@style,'#c7c7c7')]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//a[contains(@class,'prod-cart__buy')][contains(@data-ecomm-cart,'Apple MacBook Pro 13 Silver (MXK62)')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));

        driver.findElement(By.xpath("//div[contains(@data-ecomm-cart,'Apple MacBook Pro 13 Silver (MXK62')]//span[contains(@class,'count--plus')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));
        driver.findElement(xpath("//div[contains(@class,'btns-cart-holder')]//a[contains(@class,'btn--orange')]")).click();

/*
        driver.findElement(By.xpath("//div[contains(@class,'header-bottom')]//img[contains(@alt,'Avic')]")).click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
*/
        Thread.sleep(1000);


        String actualProductsCountInCart = driver.findElement(By.xpath("//div[contains(@class,'header-bottom__cart')]//div[contains(@class,'cart_count')]")).getText();
        int i = Integer.parseInt(actualProductsCountInCart);
        assertEquals(i, 3);
    }

    @Test(priority = 7)
    public void checkRemoveToCart() {
        driver.findElement(By.xpath("//span[contains(@class,'sidebar-item')]//i[contains(@class,'icon')]")).click();
        driver.findElement(By.xpath("//span[contains(@class,'sidebar-item')]//span[contains(text(),'Apple Store')]")).click();
        driver.findElement(By.xpath("//div[contains(@class, 'brand-box__title')]//a[contains(text(),'Apple Watch')]")).click();
/*
        new WebDriverWait(driver, 30).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
*/

        driver.findElement(By.xpath("//a[contains(@class,'prod-cart__buy')][contains(@data-ecomm-cart,'MYDT2')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));
/*
        driver.findElement(xpath("//div[contains(@class,'btns-cart-holder')]//a[contains(@class,'btn--orange')]")).click();

        driver.findElement(By.xpath("//a[contains(@class,'prod-cart__buy')][contains(@data-ecomm-cart,'MX372')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));
*/

        driver.findElement(By.xpath("//div[contains(@data-ecomm-cart,'MYDT2')]//i[contains(@class,'btn-close')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));

        new WebDriverWait(driver, 30).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

/*
        //driver.findElement(xpath("//div[contains(@class,'btns-cart-holder')]//a[contains(@class,'btn--orange')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));


        new WebDriverWait(driver, 60).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        driver.findElement(By.xpath("//div[contains(@class,'right-icon')]//i[contains(@class,'cart-new')]")).click();
        driver.findElement(xpath("//div[contains(@class,'btns-cart-holder')]//a[contains(@class,'btn--orange')]")).click();


        //driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
*/

/*
        driver.findElement(By.xpath("//div[contains(@class,'header-bottom')]//img[contains(@alt,'Avic')]")).click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
*/
        //driver.findElement(By.xpath("//div[contains(@class,'header-bottom')]//img[contains(@alt,'Avic')]")).click();


        String actualProductsCountInCart = driver.findElement(By.xpath("//div[contains(@class,'header-bottom__cart')]//div[contains(@class,'cart_count')]")).getText();
        int i = Integer.parseInt(actualProductsCountInCart);
        assertEquals(i, 1);
    }


    @Test(priority = 8)
    public void checkThatCheckBoxResultsContainsSearchWord() {
        driver.findElement(By.xpath("//span[contains(@class,'sidebar-item')]//i[contains(@class,'icon')]")).click();
        driver.findElement(By.xpath("//span[contains(@class,'sidebar-item')]//span[contains(text(),'Apple Store')]")).click();
        driver.findElement(By.xpath("//div[contains(@class, 'brand-box__title')]//a[contains(text(),'Apple Watch')]")).click();
        driver.findElement(By.xpath("//label[contains(@for,'chasov-series-6')]")).click();
        List<WebElement> elementList = driver.findElements(By.xpath("//div[contains(@class,'prod-cart__descr')]"));
        for (WebElement webElement : elementList) {
            assertTrue(webElement.getText().contains("Series 6"));
        }
    }


    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}
