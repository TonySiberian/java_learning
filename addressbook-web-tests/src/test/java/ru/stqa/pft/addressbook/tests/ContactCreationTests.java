package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
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
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContactsFromXml() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.xml"));
        String xml = "";
        String line = reader.readLine();
        while (line != null) {
            xml += line;
            line = reader.readLine();
        }
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        xstream.allowTypes(new Class[]{ContactData.class});
        List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
        return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.json"));
        String json = "";
        String line = reader.readLine();
        while (line != null) {
            json += line;
            line = reader.readLine();
        }
        Gson gson = new Gson();
        List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
        return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> invalidContacts() throws IOException {
        File photo = new File("src/test/resources/cat.jpg");
        app.goTo().groupPage();
        List<Object[]> list = new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.csv"));
        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(";");
            list.add(new Object[]{new ContactData()
                    .withFirstName(split[0] + "'").withLastName(split[1]).withAddress(split[2])
                    .withHomePhone(split[3]).withMobilePhone(split[4]).withWorkPhone(split[5])
                    .withHomePhone2(split[6]).withEmail(split[7]).withEmail2(split[8])
                    .withEmail3(split[9]).withPhoto(photo).withGroup(app.group().gettingGroupName())});
            line = reader.readLine();
        }
        return list.iterator();
    }

    @Test(dataProvider = "validContactsFromJson")
    public void testContactCreation(ContactData contact) throws Exception {
        app.goTo().homePage();
        Contacts before  = app.contact().all();
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }
        String group = new String(app.group().gettingGroupName());
        File photo = new File("src/test/resources/cat.jpg");
        app.contact().create(contact.withPhoto(photo).withGroup(group));
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