package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.testng.Assert;

public class UsersHelper extends HelperBase {

    public UsersHelper(ApplicationManager app) {
        super(app);
    }

    public void selectUser(String username) {
        wd.get(app.getProperty("web.baseUrl") + "manage_user_page.php");
        click(By.xpath("//a[contains(text(),'" + username + "')]"));
    }

    public void resetPassword() {
        click(By.cssSelector("input[value='Сбросить пароль']"));
//        if (wd.findElement(By.cssSelector("input[value='Reset Password']")) != null) {
//            click(By.cssSelector("input[value='Reset Password']"));
//        } else {
//            click(By.cssSelector("input[value='Сбросить пароль']"));
//        }
    }
}
