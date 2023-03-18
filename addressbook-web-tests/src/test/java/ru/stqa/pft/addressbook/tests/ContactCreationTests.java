package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.goTo().homePage();
        Contacts before  = app.contact().all();
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }
        ContactData contact = new ContactData()
                .withFirstName("test_first_name").withLastName("test_last_name").withAddress("test_address0\ntest_address1\ntest_address2")
                .withHomePhone("11111111111").withMobilePhone("22222222222").withWorkPhone("33333333333")
                .withEmail("test_e-mail@gmail.com").withEmail2("test_e-mail2@gmail.com")
                .withEmail3("test_e-mail3@gmail.com").withGroup(app.group().gettingGroupName());
        app.contact().create(contact);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after  = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }

    @Test (enabled = false)
    public void testBadContactCreation() throws Exception {
        app.goTo().homePage();
        Contacts before  = app.contact().all();
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }
        ContactData contact = new ContactData()
                .withFirstName("test_first_name'").withLastName("test_last_name").withAddress("test_address0\ntest_address1\ntest_address2")
                .withHomePhone("11111111111").withMobilePhone("22222222222").withWorkPhone("33333333333")
                .withEmail("test_e-mail@gmail.com").withEmail2("test_e-mail2@gmail.com")
                .withEmail3("test_e-mail3@gmail.com").withGroup(app.group().gettingGroupName());
        app.contact().create(contact);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after  = app.contact().all();
        assertThat(after, equalTo(before));
    }
}