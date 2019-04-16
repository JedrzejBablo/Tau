package com.app.restaurantgit.selenium;

import com.app.restaurantgit.LoginPage;
import com.app.restaurantgit.RegisterPage;
import com.app.restaurantgit.StartPage;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SeleniumTest {
    private static WebDriver driver;
    private StartPage startPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;

    @BeforeClass
    public static void driverSetup() {
        System.setProperty(
                "webdriver.chrome.driver",
                "wb/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts()
                .implicitlyWait(1, TimeUnit.MICROSECONDS);
        driver.manage().window().setSize(new Dimension(400, 600));
    }

    @Before
    public void before() {
        startPage = new StartPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage();

    }


    @Test
    public void homePage() throws IOException {
        driver.get("http://automationpractice.com");
        driver.findElement(By.cssSelector(".login")).click();

        assertEquals("Sign in",
                driver.findElement(
                        By.cssSelector("#SubmitLogin")
                ).getText());
        if (driver instanceof TakesScreenshot) {
            File f = ((TakesScreenshot) driver).
                    getScreenshotAs(OutputType.FILE);
            FileHandler.copy(f,
                    new File("screenshots/homePage.png"));
        }
    }

    @Test
    public void bestSellersCount() {
        startPage.open();
        startPage.clickBestSellers();
        assertEquals(7,
                startPage.getProducts().size());
    }

    @Test
    public void loginIncorrect() {
        loginPage.open();
        loginPage.login();
        assertFalse(loginPage.isLoginSuccessful());
    }

    @Test
    public void loginSuccessfull() {
        loginPage.open();
        loginPage.loginSuccess();
        assertEquals("http://automationpractice.com/index.php?controller=my-account", loginPage.loginSuccessfull());
    }

    @Test
    public void registerUser() throws IOException {

        assertEquals(true, registerPage.registerpage());
    }

    @AfterClass
    public static void cleanp() {
        driver.quit();
    }
}
