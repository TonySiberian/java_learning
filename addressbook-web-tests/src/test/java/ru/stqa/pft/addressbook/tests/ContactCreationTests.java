package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.getContactHelper().initContactCreation();
        app.getContactHelper().fillContactForm(new ContactData("test_first_name", "test_last_name", "test_address", "11111111111", "test_e-mail@gmail.com"));
        app.getContactHelper().submitContactCreation();
        app.getContactHelper().returnHomePage();
    }
}