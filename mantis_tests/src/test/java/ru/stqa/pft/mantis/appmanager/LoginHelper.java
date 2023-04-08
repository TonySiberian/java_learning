package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.testng.Assert;

public class LoginHelper extends HelperBase {

    public LoginHelper(ApplicationManager app) {
        super(app);
    }

    public void enterCredentials(String username, String password) {
        wd.get(app.getProperty("web.baseUrl") + "login_page.php");
        type(By.name("username"), username);
        click(By.cssSelector("input[value='Вход']"));
//        clickLoginBtn();
        type(By.name("password"), password);
        click(By.cssSelector("input[value='Вход']"));
//        clickLoginBtn();
    }

    private void clickLoginBtn() {
        if (wd.findElement(By.cssSelector("input[value='Login']")) == null) {
            click(By.cssSelector("input[value='Login']"));
        } else {
            click(By.cssSelector("input[value='Вход']"));
        }
    }

}
