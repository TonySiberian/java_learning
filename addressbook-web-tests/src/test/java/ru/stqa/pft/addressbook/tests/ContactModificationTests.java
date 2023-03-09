package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        app.getNavigationHelper().gotoHomePage();
        if (! app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().gotoGroupPage();
            if (! app.getGroupHelper().isThereAGroup()) {
                app.getGroupHelper().createGroup(new GroupData("test1", null, null));
            }
            app.getContactHelper().createContact(new ContactData("test_first_name", "test_last_name", "test_address", "11111111111", "test_e-mail@gmail.com", app.getGroupHelper().gettingGroupName()));
        }
        List<ContactData> before  = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("mod_first_name", "mod_last_name", "mod_address", "22222222222", "mod_test_e-mail@gmail.com", null), false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnHomePage();
        List<ContactData> after  = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());
    }
}
