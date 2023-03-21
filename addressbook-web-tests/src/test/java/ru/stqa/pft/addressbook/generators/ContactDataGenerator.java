package ru.stqa.pft.addressbook.generators;

import ru.stqa.pft.addressbook.model.ContactData;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

    public static void main(String[] args) throws IOException {
        int count = Integer.parseInt(args[0]);
        File file = new File(args[1]);

        List<ContactData> contacts = generateContacts(count);
        save(contacts, file);
    }

    private static void save(List<ContactData> contacts, File file) throws IOException {
        System.out.println(new File(".").getAbsolutePath());
        Writer writer = new FileWriter(file);
        for (ContactData contact : contacts) {
            writer.write(String.format("%s; %s; %s; %s; %s; %s; %s; %s; %s; %s\n"
                    , contact.getFirstName(), contact.getLastName(), contact.getAddress(), contact.getHomePhone()
                    , contact.getMobilePhone(), contact.getWorkPhone(), contact.getHomePhone2(), contact.getEmail()
                    , contact.getEmail2(), contact.getEmail3()));
        }
        writer.close();
    }

    private static List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++) {
            contacts.add(new ContactData().withFirstName(String.format("firstname %s", i))
                    .withLastName(String.format("lastname %s", i)).withAddress(String.format("address %s", i))
                    .withHomePhone(String.format("%s0000", i)).withMobilePhone(String.format("%s1111", i))
                    .withWorkPhone(String.format("%s2222", i)).withHomePhone2(String.format("%s3333", i))
                    .withEmail(String.format("%stest_e-mail@gmail.com", i)).withEmail2(String.format("%stest_e-mail2@gmail.com", i))
                    .withEmail3(String.format("%stest_e-mail3@gmail.com", i)));
        }
        return contacts;
    }
}
