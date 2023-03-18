package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().all().size() == 0) {
            app.goTo().groupPage();
            if (app.group().all().size() == 0) {
                app.group().create(new GroupData().withName("test1"));
            }
            app.contact().create(new ContactData()
                    .withFirstName("test_first_name").withLastName("test_last_name").withAddress("test_address0\ntest_address1\ntest_address2")
                    .withHomePhone("11111111111").withMobilePhone("22222222222").withWorkPhone("33333333333")
                    .withEmail("test_e-mail@gmail.com").withEmail2("test_e-mail2@gmail.com")
                    .withEmail3("test_e-mail3@gmail.com").withGroup(app.group().gettingGroupName()));
        }
    }

    @Test
    public void testContactModification() {
        Contacts before  = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withFirstName("mod_first_name").withLastName("mod_last_name")
                .withAddress("mod_address0\nmod_address1\nmod_address2").withHomePhone("44444444444").withMobilePhone("55555555555")
                .withWorkPhone("66666666666").withEmail("mod_test_e-mail@gmail.com")
                .withEmail2("mod_test_e-mail2@gmail.com").withEmail3("mod_test_e-mail3@gmail.com");
        app.contact().modify(contact);
        assertEquals(app.contact().count(), before.size());
        Contacts after  = app.contact().all();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }


}
