package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if(app.db().contacts().size() == 0) {
            if(app.db().groups().size() == 0) {
                app.goTo().groupPage();
                app.group().create(new GroupData().withName("test1"));
            }
            app.goTo().homePage();
            Groups group  = app.db().groups();
            app.contact().create(new ContactData()
                    .withFirstName("test_first_name").withLastName("test_last_name").withAddress("test_address0\ntest_address1\ntest_address2")
                    .withHomePhone("11111111111").withMobilePhone("22222222222").withWorkPhone("33333333333")
                    .withHomePhone2("44444444444").withEmail("test_e-mail@gmail.com").withEmail2("test_e-mail2@gmail.com")
                    .withEmail3("test_e-mail3@gmail.com").inGroup(group.iterator().next()));
        }
    }

    @Test
    public void testContactModification() {
        Contacts before  = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withFirstName("mod_first_name").withLastName("mod_last_name")
                .withAddress("mod_address0\r\nmod_address1\r\nmod_address2").withHomePhone("55555555555").withMobilePhone("66666666666")
                .withWorkPhone("77777777777").withHomePhone2("88888888888").withEmail("mod_test_e-mail@gmail.com")
                .withEmail2("mod_test_e-mail2@gmail.com").withEmail3("mod_test_e-mail3@gmail.com");
        app.goTo().homePage();
        app.contact().modify(contact);
        assertEquals(app.contact().count(), before.size());
        Contacts after  = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
