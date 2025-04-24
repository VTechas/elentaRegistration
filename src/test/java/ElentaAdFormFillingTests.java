import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.Random;

public class ElentaAdFormFillingTests {

    public static WebDriver driver;
    public static WebDriverWait wait;

    final String VALID_TITLE = "Used Lada with golden rims";
    final String VALID_DESC = "Selling my lovely Lada...";
    final String VALID_PRICE = "15000";
    final String VALID_LOCATION = "Gargždai";
    final String VALID_PHONE = "37061072845";
    final String VALID_EMAIL = "ThomasShelby@gmail.com";

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

    public static String generateRandomNumbers(int length) {
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
        while (true) {
            if (start + 5000 < System.currentTimeMillis()) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
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

    public void fillAdForm(String title, String description, String price, String location, String phone, String email) {
        driver.findElement(By.id("title")).sendKeys(title);
        driver.findElement(By.id("text")).sendKeys(description);
        driver.findElement(By.id("price")).sendKeys(price);
        driver.findElement(By.id("location-search-box")).sendKeys(location);
        driver.findElement(By.id("phone")).sendKeys(phone);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("submit-button")).click();
    }

    public void selectCategory() {
        WebElement selectCategory = driver.findElement(By.xpath("//*[@id=\"select-top-category-list\"]/li[1]/a"));
        selectCategory.click();
    }

    public void selectSubCategory() {
        WebElement selectSubCategory = driver.findElement(By.xpath("//*[@id=\"select-sub-category-list\"]/li[1]/a"));
        selectSubCategory.click();
    }

    public void emailAssert() {
        String expected = "Blogas el. pašto adresas";
        String actual = "";
        try {
            actual = driver.findElement(By.id("ee")).getText();
        } catch (Exception e) {
        }
        Assert.assertEquals(actual, expected);
    }

    public void positiveAssert() {
        String expected = "įkelkite nuotraukas";
        String actual = "";
        try {
            actual = driver.findElement(By.xpath("//*[@id=\"fileinput-label\"]/span")).getText();
        } catch (Exception e) {
        }
        Assert.assertEquals(actual, expected);
    }

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        acceptCookies();
        driver.get("https://elenta.lt");
        insertAd();
        selectCategory();
        selectSubCategory();
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get("https://elenta.lt/patalpinti/ivesti-informacija?categoryId=AutoMoto_Automobiliai&actionId=Siulo&returnurl=%2F");
        driver.findElement(By.id("location-search-box")).clear();
        driver.findElement(By.id("phone")).clear();
        driver.findElement(By.id("email")).clear();
    }

    @Test
    public void adFormFillValidInfoTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_LOCATION, VALID_PHONE, VALID_EMAIL);
        positiveAssert();
    }

    @Test
    public void adFormFillBlankTitleTest() {
        fillAdForm("", VALID_DESC, VALID_PRICE, VALID_LOCATION, VALID_PHONE, VALID_EMAIL);
        String expected = "Įveskite skelbimo pavadinimą";
        String actual = "";
        try {
            actual = driver.findElement(By.id("te")).getText();
        } catch (Exception e) {
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void adFormFillBlankDescriptionTest() {
        fillAdForm(VALID_TITLE, "", VALID_PRICE, VALID_LOCATION, VALID_PHONE, VALID_EMAIL);
        String notExpected = "display:none";
        String actual = driver.findElement(By.id("txte")).getDomAttribute("style");
        Assert.assertNotEquals(actual, notExpected);
    }

    @Test
    public void adFormFillBlankPriceTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, "", VALID_LOCATION, VALID_PHONE, VALID_EMAIL);
        positiveAssert();
    }

    @Test
    public void adFormFillBlankCityRegionTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, "", VALID_PHONE, VALID_EMAIL);
        positiveAssert();
    }

    @Test
    public void adFormFillBlankPhoneNumberTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_LOCATION, "", VALID_EMAIL);
        String notExpected = "display:none";
        String actual = driver.findElement(By.id("ce")).getDomAttribute("style");
        Assert.assertNotEquals(actual, notExpected);
    }

    @Test
    public void adFormFillBlankEmailTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_LOCATION, VALID_PHONE, "");
        positiveAssert();
    }

    @Test
    public void adFormFillSpecialCharactersOnlyTitle() {
        String expected = driver.getCurrentUrl();
        fillAdForm(generateRandomSpecialChars(10), VALID_DESC, VALID_PRICE, VALID_LOCATION, VALID_PHONE, VALID_EMAIL);
        String actual = "";
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("inputfile")));
            actual = driver.getCurrentUrl();
        } catch (Exception e) {
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void adFormFillSpecialCharactersOnlyDescriptionTest() {
        fillAdForm(VALID_TITLE, generateRandomSpecialChars(10), VALID_PRICE, VALID_LOCATION, VALID_PHONE, VALID_EMAIL);
        positiveAssert();
    }

    @Test
    public void adFormFillSpecialCharactersOnlyPriceTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, generateRandomSpecialChars(10), VALID_LOCATION, VALID_PHONE, VALID_EMAIL);
        positiveAssert();
    }

    @Test
    public void adFormFillSpecialCharactersOnlyCityRegionTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, generateRandomSpecialChars(10), VALID_PHONE, VALID_EMAIL);
        positiveAssert();
    }

    @Test
    public void adFormFillSpecialCharactersOnlyPhoneNumberTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_LOCATION, generateRandomSpecialChars(10), VALID_EMAIL);
        String expected = "display:none";
        String actual = driver.findElement(By.xpath("//*[@id=\"pe\"]")).getDomAttribute("style");
        Assert.assertNotEquals(actual, expected);
    }

    @Test
    public void adFormFillSpecialCharactersOnlyEmailTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_LOCATION, VALID_PHONE, generateRandomSpecialChars(10));
        emailAssert();
    }

    @Test
    public void adFormFillSingleCharacterTitleTest() {
        String expected = driver.getCurrentUrl();
        fillAdForm(generateRandomLetters(1), VALID_DESC, VALID_PRICE, VALID_LOCATION, VALID_PHONE, VALID_EMAIL);
        String actual = "";
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("inputfile")));
            actual = driver.getCurrentUrl();
        } catch (Exception e) {
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void adFormFillSingleCharacterDescriptionTest() {
        fillAdForm(VALID_TITLE, generateRandomLetters(1), VALID_PRICE, VALID_LOCATION, VALID_PHONE, VALID_EMAIL);
        positiveAssert();
    }

    @Test
    public void adFormFillSingleCharacterPriceTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, generateRandomLetters(1), VALID_LOCATION, VALID_PHONE, VALID_EMAIL);
        positiveAssert();
    }

    @Test
    public void adFormFillSingleCharacterCityRegionTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, generateRandomLetters(1), VALID_PHONE, VALID_EMAIL);
        positiveAssert();
    }

    @Test
    public void adFormFillSingleCharacterPhoneNumberTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_LOCATION, generateRandomLetters(1), VALID_EMAIL);
        String expected = "display:none";
        String actual = driver.findElement(By.xpath("//*[@id=\"pe\"]")).getDomAttribute("style");
        Assert.assertNotEquals(actual, expected);
    }

    @Test
    public void adFormFillSingleCharacterEmailTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_LOCATION, VALID_PHONE, generateRandomLetters(1));
        emailAssert();
    }

    @Test
    public void adFormFill256CharactersTitleTest() {
        String expected = driver.getCurrentUrl();
        fillAdForm(generateRandomLetters(256), VALID_DESC, VALID_PRICE, VALID_LOCATION, VALID_PHONE, VALID_EMAIL);
        String actual = "";
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("inputfile")));
            actual = driver.getCurrentUrl();
        } catch (Exception e) {
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void adFormFill256CharactersDescriptionTest() {
        fillAdForm(VALID_TITLE, generateRandomLetters(256), VALID_PRICE, VALID_LOCATION, VALID_PHONE, VALID_EMAIL);
        positiveAssert();
    }

    @Test
    public void adFormFill25NumbersPriceTest() {
        String expected = driver.getCurrentUrl();
        fillAdForm(VALID_TITLE, VALID_DESC, generateRandomNumbers(25), VALID_LOCATION, VALID_PHONE, VALID_EMAIL);
        String actual = "";
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("inputfile")));
            actual = driver.getCurrentUrl();
        } catch (Exception e) {
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void adFormFill25NumbersPhoneNumberTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_LOCATION, generateRandomNumbers(25), VALID_EMAIL);

        try {
            driver.switchTo().alert();
            Assert.fail();
        } catch (Exception e) {
            String notExpected = "display:none";
            String actual = driver.findElement(By.xpath("//*[@id=\"pe\"]")).getDomAttribute("style");
            Assert.assertNotEquals(actual, notExpected);
        }
    }

    @Test
    public void adFormFill256CharactersEmailTest() {
        String expected = driver.getCurrentUrl();
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_LOCATION, VALID_PHONE, generateRandomLetters(246) + "@gmail.com");
        String actual = "";
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("inputfile")));
            actual = driver.getCurrentUrl();
        } catch (Exception e) {
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void adFormFillDuplicateTitleTest() {
        String expected = driver.getCurrentUrl();
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_LOCATION, VALID_PHONE, VALID_EMAIL);
        String actual = "";
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("inputfile")));
            actual = driver.getCurrentUrl();
        } catch (Exception e) {
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void adFormFillSubdomainEmailTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_LOCATION, VALID_PHONE, "ThomasShelby@gmail.subdomain.com");
        positiveAssert();
    }

    @Test
    public void adFormFillNoAtSymbolInEmailTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_LOCATION, VALID_PHONE, "ThomasShelbygmail.com");
        emailAssert();
    }

    @Test
    public void adFormFillInvalidDomainEmailTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_LOCATION, VALID_PHONE, "ThomasShelby@gmail");
        emailAssert();
    }

    @Test
    public void adFormFillMultipleAtSymbolsEmailTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_LOCATION, VALID_PHONE, "ThomasShelby@@gmail.com");
        emailAssert();
    }

    @Test
    public void adFormFillSpaceCharacterWithinEmailTest() {
        fillAdForm(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_LOCATION, VALID_PHONE, "Thomas Shelby@gmail.com");
        emailAssert();
    }

    @Test
    public void photoUploadValidTest() {
        adFormFillValidInfoTest();
        uploadImage("pics/15.png");
        String notExpected = "display: block;";
        String actual = "";
        try {
            actual = driver.findElement(By.xpath("//*[@id=\"photo-1\"]/div/img")).getText();
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertNotEquals(actual, notExpected);
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUploadNoPictureSelectedTest() {
        adFormFillValidInfoTest();
        uploadImage("");
        String notExpected = "display: block;";
        String actual = "";
        try {
            actual = driver.findElement(By.xpath("//*[@id=\"photos-container\"]")).getText();
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertNotEquals(actual, notExpected);
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUpload10PicturesUploadTest() {
        adFormFillValidInfoTest();
        uploadImage("pics/1.jpg", "pics/3.jpg", "pics/4.jpg", "pics/5.jpg", "pics/6.jpg", "pics/7.jpg", "pics/8.jpg", "pics/9.jpg", "pics/13.jpg", "pics/15.png");
        String expected = "display: block;";
        String actual = "";
        try {
            actual = driver.findElement(By.xpath("//*[@id=\"fileupload-message\"]")).getText();
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(actual, expected);
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUploadGifTest() {
        adFormFillValidInfoTest();
        uploadImage("pics/11.gif");
        String notExpected = "display: block;";
        String actual = "";
        try {
            actual = driver.findElement(By.xpath("//*[@id=\"photo-1\"]/div/img")).getText();
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertNotEquals(actual, notExpected);
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUploadPngTest() {
        adFormFillValidInfoTest();
        uploadImage("pics/15.png");
        String notExpected = "display: block;";
        String actual = "";
        try {
            actual = driver.findElement(By.xpath("//*[@id=\"photo-1\"]/div/img")).getText();
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertNotEquals(actual, notExpected);
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUploadMp4Test() {
        adFormFillValidInfoTest();
        uploadImage("pics/2.mp4");
        String expected = "display: block;";
        String actual = "";
        try {
            actual = driver.findElement(By.xpath("//*[@id=\"fileupload-message\"]")).getText();
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(actual, expected);
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUpload10MBFileTest() {
        adFormFillValidInfoTest();
        uploadImage("pics/16.png");
        String notExpected = "Įkeliama ...";
        String actual = "";
        try {
            actual = driver.findElement(By.xpath("//*[@id=\"photo-new\"]/div")).getText();
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertNotEquals(actual, notExpected);
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUploadRemoveAlreadyUploadedPictureTest() {
        adFormFillValidInfoTest();
        uploadImage("pics/15.png");
        driver.findElement(By.id("remove-photo-1")).click();
        wait.until(driver -> driver.findElements(By.cssSelector("#photos-container img")).isEmpty());

        int imageCount = driver.findElements(By.cssSelector("#photos-container img")).size();
        Assert.assertEquals(imageCount, 0);

        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUploadDuplicatePictureUploadTest() {
        adFormFillValidInfoTest();
        uploadImage("pics/15.png", "pics/15.png", "pics/15.png");

        String notExpected = "display: block;";
        String actual = "";
        try {
            actual = driver.findElement(By.id("fileupload-message")).getText();
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }

        Assert.assertNotEquals(actual, notExpected);
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void photoUploadDdsFileUploadTest() {
        adFormFillValidInfoTest();
        uploadImage("pics/10.dds");

        String notExpected = "display: block;";
        String actual = "";
        try {
            actual = driver.findElement(By.id("fileupload-message")).getText();
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }

        Assert.assertNotEquals(actual, notExpected);
        driver.findElement(By.id("forward-button")).click();
        driver.findElement(By.id("forward-button")).click();
    }

    @Test
    public void removeUploadedAdTest() {
        photoUploadValidTest();
        driver.findElement(By.xpath("//*[@id=\"promotead-form\"]/table/tbody/tr[12]/td[2]/a")).click();
        driver.findElement(By.className("delete")).click();

        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        try {
            driver.findElement(By.xpath("//*[@id=\"pe\"]"));
            Assert.fail();
        } catch (NoSuchElementException e) {
        }
    }

    @AfterClass
    public void tearDown() {
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
        }
        //        driver.close();
    }
}
