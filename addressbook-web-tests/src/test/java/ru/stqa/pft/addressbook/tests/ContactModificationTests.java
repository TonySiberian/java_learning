package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

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
    public void testContactModification() {
        List<ContactData> before  = app.contact().list();
        int index = before.size() - 1;
        ContactData contact = new ContactData(before.get(index).getId(), "mod_first_name", "mod_last_name", "mod_address", "22222222222", "mod_test_e-mail@gmail.com", null);
        app.contact().modify(index, contact);
        List<ContactData> after  = app.contact().list();
        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }


}
