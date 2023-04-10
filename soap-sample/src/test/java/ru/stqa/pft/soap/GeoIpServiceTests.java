package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

    @Test
    public void testMyIp() {
        String geoIP = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("5.44.168.160");
        assertEquals(geoIP, "<GeoIP><Country>RU</Country><State>53</State></GeoIP>");
    }
}
