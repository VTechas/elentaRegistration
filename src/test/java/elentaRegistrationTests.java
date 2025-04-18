import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.time.Duration;
import java.util.Random;

public class elentaRegistrationTests {

    public static WebDriver driver;

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

    public void acceptCookies() {
        driver.get("https://elenta.lt");
        WebElement acceptBtn = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[2]/div[2]/button[1]"));
        acceptBtn.click();
    }

    public void clickLogin() {
        WebElement clickLogin = driver.findElement(By.xpath("//*[@id=\"header-container-nav\"]/a[3]"));
        clickLogin.click();
    }

    public void registerFresh () {
        WebElement clickRegister = driver.findElement(By.xpath("//*[@id=\"form\"]/fieldset/table/tbody/tr[10]/td/p/a"));
        clickRegister.click();
    }

    @BeforeClass
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        acceptCookies();
        driver.get("https://elenta.lt");
        clickLogin();
        registerFresh();
    }

    @BeforeMethod
    public void beforeMethod(){
        driver.get("https://elenta.lt/registracija");
    }

    @Test
    public void registrationValidInfoTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationTakenUsernameTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas");
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationTakenEmailTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("ThomasShelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationBlankUsernameTest() {
        driver.findElement(By.id("UserName")).sendKeys("");
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationBlankEmailTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationBlankPasswordTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationBlankRepeatPasswordTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationSpaceCharacterUsernameTest() {
        driver.findElement(By.id("UserName")).sendKeys(" ");
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationSpaceCharacterEmailTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys(" ");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationSpaceCharacterPasswordTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys(" ");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationSpaceCharacterPasswordRepeatTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys(" ");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationSingleSpecialCharacterUsernameTest() {
        driver.findElement(By.id("UserName")).sendKeys(generateRandomSpecialChars(1));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000 - 100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationSingleSpecialCharacterEmailTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys(generateRandomSpecialChars(1));
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registration6SpecialCharacterPasswordTest() {
        String randomPassword = generateRandomSpecialChars(6);
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys(randomPassword);
        driver.findElement(By.id("Password2")).sendKeys(randomPassword);
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationSpaceCharacterWithinUsernameTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas " + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationSpaceCharacterWithinEmailTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas " + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationSpaceCharacterWithinPasswordTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("She lby");
        driver.findElement(By.id("Password2")).sendKeys("She lby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationSpaceCharacterAfterAtSymbolEmailTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gm ail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationLowerAndUpperCaseEmailTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("ThOmAs" + (100 + Math.round(Math.random() * (1000-100))) + "ShElBy@GmAiL.CoM");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationLowerAndUpperCaseUsernameTest() {
        driver.findElement(By.id("UserName")).sendKeys("ThOmAs" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationOnlyNumbersUsernameTest() {
        driver.findElement(By.id("UserName")).sendKeys("" + 100 + Math.round(Math.random() * (10000000-100)));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationUsernameMatchesPasswordTest() {
        int random = 100 + (int)Math.round(Math.random() * (1000-100));
        String matching = "Thomas" + random;
        driver.findElement(By.id("UserName")).sendKeys(matching);
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys(matching);
        driver.findElement(By.id("Password2")).sendKeys(matching);
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registration31SpecialCharacterUsernameTest() {
        driver.findElement(By.id("UserName")).sendKeys(generateRandomSpecialChars(31));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationLowerCasePasswordOnlyTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("shelby");
        driver.findElement(By.id("Password2")).sendKeys("shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationSpecialCharactersBeforeAtSymbolEmailTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby!^#@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationMultipleAtSymbolsInEmailTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail@gmail@@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registration51CharactersEmailTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + generateRandomLetters(29) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationBadDomainEmailTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.123123123");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registration51CharactersPasswordTest() {
        String psw = generateRandomLetters(51);
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys(psw);
        driver.findElement(By.id("Password2")).sendKeys(psw);
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registration5CharactersPasswordTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelb");
        driver.findElement(By.id("Password2")).sendKeys("Shelb");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationOnlyNumbersPasswordTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("123456");
        driver.findElement(By.id("Password2")).sendKeys("123456");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationWithoutDomainEmailTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationWithoutAtCharacterEmailTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelbygmail.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @Test
    public void registrationSubdomainEmailTest() {
        driver.findElement(By.id("UserName")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))));
        driver.findElement(By.id("Email")).sendKeys("Thomas" + (100 + Math.round(Math.random() * (1000-100))) + "Shelby@gmail.subdomain.com");
        driver.findElement(By.id("Password")).sendKeys("Shelby");
        driver.findElement(By.id("Password2")).sendKeys("Shelby");
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @AfterClass
    public void tearDown(){
        //        driver.close();
    }
}