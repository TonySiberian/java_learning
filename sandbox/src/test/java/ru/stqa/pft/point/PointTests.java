package ru.stqa.pft.point;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void testPoint() {
        Point p = new Point(3, 6, 4, 9);
        Assert.assertEquals(p.distance(), 3.1622776601683795);
    }

}
