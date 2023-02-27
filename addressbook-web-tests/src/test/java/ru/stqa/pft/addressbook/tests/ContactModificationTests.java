package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("mod_first_name", "mod_last_name", "mod_address", "22222222222", "mod_test_e-mail@gmail.com", null), false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnHomePage();
    }
}
