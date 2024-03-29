package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.Users;
import ru.stqa.pft.mantis.model.UsersData;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import static org.testng.Assert.assertTrue;

public class PasswordResetTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testPasswordReset() throws MessagingException, IOException {
        long now = System.currentTimeMillis();
        Users allUsers = app.db().users();
        UsersData selectedUser = allUsers.iterator().next();
        String user = new String(selectedUser.getUsername());
        String userEmail = new String(selectedUser.getEmail());
        String newUserPassword = String.format("password%s", now);

        app.login().enterCredentials(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
        app.users().selectUser(user);
        app.users().resetPassword();

        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, userEmail);
        app.registration().finish(confirmationLink, newUserPassword);
        assertTrue(app.newSession().login(user, newUserPassword));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
