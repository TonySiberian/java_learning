package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.sql.*;

public class DbConnectionTest {

    @Test
    public void testGroupDbConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?user=root&password=");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select group_id, group_name, group_header, group_footer from group_list");
            Groups groups = new Groups();
            while (rs.next()) {
                groups.add(new GroupData().withId(rs.getInt("group_id"))
                        .withName(rs.getNString("group_name"))
                        .withHeader(rs.getNString("group_header"))
                        .withFooter(rs.getString("group_footer")));
            }
            rs.close();
            st.close();
            conn.close();

            System.out.println(groups);

            // Do something with the Connection

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    @Test
    public void testContactDbConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?user=root&password=");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "select id, firstname, lastname, address, home, mobile, work, email, email2, email3, phone2 from addressbook");
            Contacts contacts = new Contacts();
            while (rs.next()) {
                contacts.add(new ContactData().withId(rs.getInt("id"))
                        .withFirstName(rs.getNString("firstname"))
                        .withLastName(rs.getNString("lastname"))
                        .withAddress(rs.getString("address"))
                        .withHomePhone(rs.getString("home"))
                        .withMobilePhone(rs.getString("mobile"))
                        .withWorkPhone(rs.getString("work"))
                        .withEmail(rs.getString("email"))
                        .withEmail2(rs.getString("email2"))
                        .withEmail3(rs.getString("email3"))
                        .withHomePhone2(rs.getString("phone2")));
            }
            rs.close();
            st.close();
            conn.close();

            System.out.println(contacts);

            // Do something with the Connection

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
