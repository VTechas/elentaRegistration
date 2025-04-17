import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class elentaAdFormFillingTests {
    public static WebDriver driver;

    public void acceptCookies() {
        driver.get("https://elenta.lt");
        WebElement acceptBtn = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[2]/div[2]/button[1]"));
        acceptBtn.click();
    }
    public void insertAd() {
        WebElement insertAd = driver.findElement(By.id("submit-new-ad-nav-button"));
        insertAd.click();
    }

    public void selectCategory () {
        WebElement selectCategory = driver.findElement(By.xpath("//*[@id=\"select-top-category-list\"]/li[1]/a"));
        selectCategory.click();
    }
    public void selectSubCategory () {
        WebElement selectSubCategory = driver.findElement(By.xpath("//*[@id=\"select-sub-category-list\"]/li[1]/a"));
        selectSubCategory.click();
    }
    @BeforeClass
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        acceptCookies();
        driver.get("https://elenta.lt");
        insertAd();
        selectCategory();
        selectSubCategory();
    }

    @BeforeMethod
    public void beforeMethod(){
        driver.get("https://elenta.lt/patalpinti/ivesti-informacija?categoryId=AutoMoto_Automobiliai&actionId=Siulo&returnurl=%2F");
    }

    @Test
    public void adFormFillValidInfoTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillBlankTitleTest() {
        driver.findElement(By.id("title")).sendKeys("");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }








}
