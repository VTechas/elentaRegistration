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

public class ElentaRegistrationTests {

    public static WebDriver driver;

    final String VALID_PASSWORD = ("Shelby");
    final String VALID_PASSWORD_CONFIRM = ("Shelby");

    public static String getRandomUsername() {
        return "Thomas" + (100 + (int)(Math.random() * (1000 - 100)));
    }

    public static String getRandomEmail() {
        return "Thomas" + (100 + (int)(Math.random() * (1000 - 100))) + "Shelby@gmail.com";
    }

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

    public void registerFresh() {
        WebElement clickRegister = driver.findElement(By.xpath("//*[@id=\"form\"]/fieldset/table/tbody/tr[10]/td/p/a"));
        clickRegister.click();
    }

    public void fillRegistrationForm(String username, String email, String password, String passwordCnfrm) {
        driver.findElement(By.id("UserName")).sendKeys(username);
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("Password2")).sendKeys(passwordCnfrm);
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/fieldset/table/tbody/tr[11]/td[2]/input")).click();
    }

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        acceptCookies();
        driver.get("https://elenta.lt");
        clickLogin();
        registerFresh();
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get("https://elenta.lt/registracija");
    }

    @Test
    public void registrationValidInfoTest() {
        fillRegistrationForm(getRandomUsername(), getRandomUsername(), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationTakenUsernameTest() {
        fillRegistrationForm("Thomas", getRandomEmail(), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationTakenEmailTest() {
        fillRegistrationForm(getRandomUsername(), "ThomasShelby@gmail.com", VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationBlankUsernameTest() {
        fillRegistrationForm("", getRandomEmail(), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationBlankEmailTest() {
        fillRegistrationForm(getRandomUsername(), "", VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationBlankPasswordTest() {
        fillRegistrationForm(getRandomUsername(), getRandomEmail(), "", VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationBlankRepeatPasswordTest() {
        fillRegistrationForm(getRandomUsername(), getRandomEmail(), VALID_PASSWORD, "");
    }

    @Test
    public void registrationSpaceCharacterUsernameTest() {
        fillRegistrationForm(" ", getRandomEmail(), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationSpaceCharacterEmailTest() {
        fillRegistrationForm(getRandomUsername(), " ", VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationSpaceCharacterPasswordTest() {
        fillRegistrationForm(getRandomUsername(), getRandomEmail(), " ", VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationSpaceCharacterPasswordRepeatTest() {
        fillRegistrationForm(getRandomUsername(), getRandomEmail(), VALID_PASSWORD, " ");
    }

    @Test
    public void registrationSingleSpecialCharacterUsernameTest() {
        fillRegistrationForm(generateRandomSpecialChars(1), getRandomEmail(), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationSingleSpecialCharacterEmailTest() {
        fillRegistrationForm(getRandomUsername(), generateRandomSpecialChars(1), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registration6SpecialCharacterPasswordTest() {
        String randomPassword = generateRandomSpecialChars(6);
        fillRegistrationForm(getRandomUsername(), getRandomEmail(), randomPassword, randomPassword);
    }

    @Test
    public void registrationSpaceCharacterWithinUsernameTest() {
        fillRegistrationForm(("Thomas " + (100 + Math.round(Math.random() * (1000 - 100)))), getRandomEmail(), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationSpaceCharacterWithinEmailTest() {
        fillRegistrationForm(getRandomUsername(), ("Thomas " + (100 + Math.round(Math.random() * (1000 - 100))) + "Shelby@gmail.com"), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationSpaceCharacterWithinPasswordTest() {
        fillRegistrationForm(getRandomUsername(), getRandomEmail(), "She lby", "She lby");
    }

    @Test
    public void registrationSpaceCharacterAfterAtSymbolEmailTest() {
        fillRegistrationForm(getRandomUsername(), ("Thomas" + (100 + Math.round(Math.random() * (1000 - 100))) + "Shelby@gm ail.com"), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationLowerAndUpperCaseEmailTest() {
        fillRegistrationForm(getRandomUsername(), ("ThOmAs" + (100 + Math.round(Math.random() * (1000 - 100))) + "ShElBy@GmAiL.CoM"), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationLowerAndUpperCaseUsernameTest() {
        fillRegistrationForm(("ThOmAs" + (100 + Math.round(Math.random() * (1000 - 100)))), getRandomEmail(), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationOnlyNumbersUsernameTest() {
        fillRegistrationForm(("" + 100 + Math.round(Math.random() * (10000000 - 100))), getRandomEmail(), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationUsernameMatchesPasswordTest() {
        int random = 100 + (int) Math.round(Math.random() * (1000 - 100));
        String matching = "Thomas" + random;
        fillRegistrationForm(matching, getRandomEmail(), matching, matching);
    }

    @Test
    public void registration31SpecialCharacterUsernameTest() {
        fillRegistrationForm(generateRandomSpecialChars(31), getRandomEmail(), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationLowerCasePasswordOnlyTest() {
        fillRegistrationForm(getRandomUsername(), getRandomEmail(), "shelby", "shelby");
    }

    @Test
    public void registrationSpecialCharactersBeforeAtSymbolEmailTest() {
        fillRegistrationForm(getRandomUsername(), ("Thomas" + (100 + Math.round(Math.random() * (1000 - 100))) + "Shelby!^#@gmail.com"), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationMultipleAtSymbolsInEmailTest() {
        fillRegistrationForm(getRandomUsername(), ("Thomas" + (100 + Math.round(Math.random() * (1000 - 100))) + "Shelby@gmail@gmail@@gmail.com"), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registration51CharactersEmailTest() {
        fillRegistrationForm(getRandomUsername(), ("Thomas" + generateRandomLetters(29) + "Shelby@gmail.com"), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationBadDomainEmailTest() {
        fillRegistrationForm(getRandomUsername(), ("Thomas" + (100 + Math.round(Math.random() * (1000 - 100))) + "Shelby@gmail.123123123"), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registration51CharactersPasswordTest() {
        String psw = generateRandomLetters(51);
        fillRegistrationForm(getRandomUsername(), getRandomEmail(), psw, psw);
    }

    @Test
    public void registration5CharactersPasswordTest() {
        fillRegistrationForm(getRandomUsername(), getRandomEmail(), "Shelb", "Shelb");
    }

    @Test
    public void registrationOnlyNumbersPasswordTest() {
        fillRegistrationForm(getRandomUsername(), getRandomEmail(), "123456", "123456");
    }

    @Test
    public void registrationWithoutDomainEmailTest() {
        fillRegistrationForm(getRandomUsername(), ("Thomas" + (100 + Math.round(Math.random() * (1000 - 100))) + "Shelby@"), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationWithoutAtCharacterEmailTest() {
        fillRegistrationForm(getRandomUsername(), ("Thomas" + (100 + Math.round(Math.random() * (1000 - 100))) + "Shelbygmail.com"), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @Test
    public void registrationSubdomainEmailTest() {
        fillRegistrationForm(getRandomUsername(), ("Thomas" + (100 + Math.round(Math.random() * (1000 - 100))) + "Shelby@gmail.subdomain.com"), VALID_PASSWORD, VALID_PASSWORD_CONFIRM);
    }

    @AfterClass
    public void tearDown() {
        //        driver.close();
    }
}