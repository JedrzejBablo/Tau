package com.app.restaurantgit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private final WebDriver driver;

    @FindBy(id = "email")
    WebElement loginInput;

    @FindBy(css = "#passwd")
    WebElement passwordInput;

    @FindBy(xpath = "//*[@id=\"SubmitLogin\"]")
    WebElement loginButton;


    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        PageFactory.initElements(driver, this);
    }

    public void login() {
        loginButton.click();
    }
    public void loginSuccess() {
        loginInput.sendKeys("daniel8b@wp.pl");
        passwordInput.sendKeys("wYUkc8ERUw3tUF9");
        loginButton.click();
    }
    public WebElement getLoginButton() {
        return loginButton;
    }

    public WebElement getLoginInput() {
        return loginInput;
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public boolean isLoginSuccessful() {
        return driver.findElement(By.xpath("//*[@id=\"center_column\"]/div[1]/ol/li")) == null;
    }

    public String loginSuccessfull(){
        return driver.getCurrentUrl();
    }
}