package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.Set;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.goTo().homePage();
        Set<ContactData> before  = app.contact().all();
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }
        ContactData contact = new ContactData()
                .withFirstName("test_first_name").withLastName("test_last_name").withAddress("test_address")
                .withPhone("11111111111").withEmail("test_e-mail@gmail.com").withGroup(app.group().gettingGroupName());
        app.contact().create(contact);
        Set<ContactData> after  = app.contact().all();
        Assert.assertEquals(after.size(), before.size() + 1);

        contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
        before.add(contact);
        Assert.assertEquals(after, before);
    }
}