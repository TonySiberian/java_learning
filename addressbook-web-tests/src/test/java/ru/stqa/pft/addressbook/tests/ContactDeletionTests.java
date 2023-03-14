package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.Set;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().all().size() == 0) {
            app.goTo().groupPage();
            if (app.group().all().size() == 0) {
                app.group().create(new GroupData().withName("test1"));
            }
            app.contact().create(new ContactData()
                    .withFirstName("test_first_name").withLastName("test_last_name").withAddress("test_address")
                    .withPhone("11111111111").withEmail("test_e-mail@gmail.com").withGroup(app.group().gettingGroupName()));
        }
    }

    @Test
    public void testContactDeletion() throws Exception {
        Set<ContactData> before  = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        app.goTo().homePage();
        Set<ContactData> after  = app.contact().all();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(deletedContact);
        Assert.assertEquals(before, after);
    }
}