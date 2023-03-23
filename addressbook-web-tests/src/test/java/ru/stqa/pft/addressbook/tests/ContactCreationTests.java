package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContacts() throws IOException {
        File photo = new File("src/test/resources/cat.jpg");
        app.goTo().groupPage();
        List<Object[]> list = new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.csv"));
        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(";");
            list.add(new Object[]{new ContactData()
                    .withFirstName(split[0]).withLastName(split[1]).withAddress(split[2])
                    .withHomePhone(split[3]).withMobilePhone(split[4]).withWorkPhone(split[5])
                    .withHomePhone2(split[6]).withEmail(split[7]).withEmail2(split[8])
                    .withEmail3(split[9]).withPhoto(photo).withGroup(app.group().gettingGroupName())});
            line = reader.readLine();
        }
        return list.iterator();
    }

    @DataProvider
    public Iterator<Object[]> invalidContacts() {
        File photo = new File("src/test/resources/cat.jpg");
        app.goTo().groupPage();
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[] {new ContactData()
                .withFirstName("test_first_name1'").withLastName("test_last_name1").withAddress("1test_address0\n1test_address1\n1test_address2")
                .withHomePhone("11111111111").withMobilePhone("12222222222").withWorkPhone("13333333333")
                .withHomePhone2("14444444444").withEmail("1test_e-mail@gmail.com").withEmail2("1test_e-mail2@gmail.com")
                .withEmail3("1test_e-mail3@gmail.com").withPhoto(photo).withGroup(app.group().gettingGroupName())});
        list.add(new Object[] {new ContactData()
                .withFirstName("test_first_name2'").withLastName("test_last_name2").withAddress("2test_address0\n2test_address1\n2test_address2")
                .withHomePhone("21111111111").withMobilePhone("22222222222").withWorkPhone("23333333333")
                .withHomePhone2("24444444444").withEmail("2test_e-mail@gmail.com").withEmail2("2test_e-mail2@gmail.com")
                .withEmail3("2test_e-mail3@gmail.com").withPhoto(photo).withGroup(app.group().gettingGroupName())});
        list.add(new Object[] {new ContactData()
                .withFirstName("test_first_name3'").withLastName("test_last_name3").withAddress("3test_address0\n3test_address1\n3test_address2")
                .withHomePhone("31111111111").withMobilePhone("32222222222").withWorkPhone("33333333333")
                .withHomePhone2("34444444444").withEmail("3test_e-mail@gmail.com").withEmail2("3test_e-mail2@gmail.com")
                .withEmail3("3test_e-mail3@gmail.com").withPhoto(photo).withGroup(app.group().gettingGroupName())});
        return list.iterator();
    }

    @Test(dataProvider = "validContacts")
    public void testContactCreation(ContactData contact) throws Exception {
        app.goTo().homePage();
        Contacts before  = app.contact().all();
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }
        app.contact().create(contact);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after  = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }

    @Test(dataProvider = "invalidContacts")
    public void testBadContactCreation(ContactData contact) throws Exception {
        app.goTo().homePage();
        Contacts before  = app.contact().all();
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }
        app.contact().create(contact);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after  = app.contact().all();
        assertThat(after, equalTo(before));
    }
}