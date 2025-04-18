import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.Random;

public class elentaAdFormFillingTests {
    public static WebDriver driver;
    public static WebDriverWait wait;

    public static String generateRandomSpecialChars(int length) {
        String specialChars = "!#$%^&*()_+-=[]{}|;:',.<>/?";
        StringBuilder result = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(specialChars.length());
            result.append(specialChars.charAt(index));
        }

        return result.toString();
    }

    public static String generateRandomLetters(int length) {
        String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String text = "";

        for (int i = 0; i < length; i++) {
            text += symbols.charAt((int) (Math.random() * symbols.length()));
        }
        return text;
    }

    public static String generateRandomNumbers (int length) {
        Random random = new Random();
        StringBuilder number = new StringBuilder();

        // First digit should not be 0
        number.append(random.nextInt(9) + 1);

        for (int i = 1; i < length; i++) {
            number.append(random.nextInt(10));
        }

        return number.toString();
    }

    public void uploadImage(String... relativeFilePaths) {
        WebElement fileInput = driver.findElement(By.id("inputfile"));
        StringBuilder fullPaths = new StringBuilder();

        for (int i = 0; i < relativeFilePaths.length; i++) {
            File file = new File(relativeFilePaths[i]);
            fullPaths.append(file.getAbsolutePath()).append("\n");
        }

        fileInput.sendKeys(fullPaths.toString().trim());

        long start = System.currentTimeMillis();
        while(true) {
            if(start + 5000 < System.currentTimeMillis()){
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
            if (driver.findElement(By.id("photos-container")).findElements(By.tagName("img")).size() == relativeFilePaths.length) {
                break;
            }

        }

    }

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
        wait = new WebDriverWait(driver,Duration.ofSeconds(5));
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
        driver.findElement(By.id("location-search-box")).clear();
        driver.findElement(By.id("phone")).clear();
        driver.findElement(By.id("email")).clear();
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

    @Test
    public void adFormFillBlankDescriptionTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillBlankPriceTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillBlankCityRegionTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillBlankPhoneNumberTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillBlankEmailTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillSpecialCharactersOnlyTitle() {
        driver.findElement(By.id("title")).sendKeys(generateRandomSpecialChars(10));
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillSpecialCharactersOnlyDescriptionTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys(generateRandomSpecialChars(10));
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillSpecialCharactersOnlyPriceTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys(generateRandomSpecialChars(10));
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillSpecialCharactersOnlyCityRegionTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys(generateRandomSpecialChars(10));
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillSpecialCharactersOnlyPhoneNumberTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys(generateRandomSpecialChars(10));
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillSpecialCharactersOnlyEmailTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys(generateRandomSpecialChars(10));
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillSingleCharacterTitleTest() {
        driver.findElement(By.id("title")).sendKeys(generateRandomLetters(1));
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillSingleCharacterDescriptionTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys(generateRandomLetters(1));
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillSingleCharacterPriceTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys(generateRandomLetters(1));
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillSingleCharacterCityRegionTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys(generateRandomLetters(1));
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillSingleCharacterPhoneNumberTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys(generateRandomLetters(1));
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillSingleCharacterEmailTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys(generateRandomLetters(1));
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFill256CharactersTitleTest() {
        driver.findElement(By.id("title")).sendKeys(generateRandomLetters(256));
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFill256CharactersDescriptionTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys(generateRandomLetters(256));
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFill25CharactersPriceTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys(generateRandomNumbers(25));
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFill25CharactersPhoneNumberTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys(generateRandomNumbers(25));
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFill256CharactersEmailTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys(generateRandomLetters(246) + "@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillDuplicateTitleTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillSubdomainEmailTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail.subdomain.com");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillInvalidDomainEmailTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@gmail");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillMultipleAtSymbolsEmailTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("ThomasShelby@@gmail");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void adFormFillSpaceCharacterWithinEmailTest() {
        driver.findElement(By.id("title")).sendKeys("Used Lada with golden rims");
        driver.findElement(By.id("text")).sendKeys("Selling my lovely Lada that was looked after and cared for");
        driver.findElement(By.id("price")).sendKeys("15000");
        driver.findElement(By.id("location-search-box")).sendKeys("Gargždai");
        driver.findElement(By.id("phone")).sendKeys("37061072845");
        driver.findElement(By.id("email")).sendKeys("Thomas Shelby@gmail");
        driver.findElement(By.id("submit-button")).click();
    }

    @Test
    public void photoUploadValidTest() {
        adFormFillValidInfoTest();
        uploadImage("pics/15.png");
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUploadNoPictureSelectedTest() {
        adFormFillValidInfoTest();
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUpload10PicturesUploadTest() {
        adFormFillValidInfoTest();
        uploadImage("pics/1.jpg", "pics/3.jpg", "pics/4.jpg", "pics/5.jpg", "pics/6.jpg", "pics/7.jpg", "pics/8.jpg", "pics/9.jpg", "pics/13.jpg", "pics/15.png");
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUploadGifTest() {
        adFormFillValidInfoTest();
        uploadImage("pics/11.gif");
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUploadPngTest() {
        adFormFillValidInfoTest();
        uploadImage("pics/15.png");
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUploadMp4Test() {
        adFormFillValidInfoTest();
        uploadImage("pics/2.mp4");
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUpload10MBFileTest() {
        adFormFillValidInfoTest();
        uploadImage("pics/16.png");
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUploadRemoveAlreadyUploadedPictureTest() throws InterruptedException {
        adFormFillValidInfoTest();
        uploadImage("pics/15.png");
        driver.findElement(By.id("remove-photo-1")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUploadDuplicatePictureUploadTest() {
        adFormFillValidInfoTest();
        uploadImage("pics/15.png", "pics/15.png", "pics/15.png");
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUploadDdsFileUploadTest() {
        adFormFillValidInfoTest();
        uploadImage("pics/10.dds");
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void removeUploadedAdTest() {
        photoUploadValidTest();
        driver.findElement(By.xpath("//*[@id=\"promotead-form\"]/table/tbody/tr[12]/td[2]/a")).click();
        driver.findElement(By.className("delete")).click();
        driver.switchTo().alert().accept();
    }

    @AfterClass
    public void tearDown(){
        //        driver.close();
    }
}
