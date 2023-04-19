package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RemoveContactFromGroupTests extends TestBase{

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
        if(app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testRemoveContactFromGroup() {
        ContactData modContact = app.db().contacts().iterator().next();
        Groups groupsBefore = modContact.getGroups();
        if (groupsBefore.size() == 0) {
            app.goTo().homePage();
            GroupData group = app.db().groups().iterator().next();
            app.contact().addContactToGroup(modContact, group);
            groupsBefore.add(group);
        }
        GroupData delGroup = groupsBefore.iterator().next();
        app.goTo().homePage();
        app.contact().removeContactFromGroup(modContact, delGroup);
        Groups groupsAfter = app.db().contacts().iterator().next().withId(modContact.getId()).getGroups();
        assertThat(groupsAfter, equalTo(
                groupsBefore.without(delGroup)));
    }
}
