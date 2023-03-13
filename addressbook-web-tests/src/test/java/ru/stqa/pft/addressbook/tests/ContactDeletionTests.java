package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.List;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().list().size() == 0) {
            app.goTo().groupPage();
            if (app.group().list().size() == 0) {
                app.group().create(new GroupData("test1", null, null));
            }
            app.contact().create(new ContactData("test_first_name", "test_last_name", "test_address", "11111111111", "test_e-mail@gmail.com", app.group().gettingGroupName()));
        }
    }

    @Test
    public void testContactDeletion() throws Exception {
        List<ContactData> before  = app.contact().list();
        int index = before.size() - 1;
        app.contact().delete(index);
        app.goTo().homePage();
        List<ContactData> after  = app.contact().list();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(index);
        Assert.assertEquals(before, after);
    }
}