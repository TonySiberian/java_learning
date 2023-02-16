package ru.stqa.pft.point;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void testPoint() {
        Point p1 = new Point(3, 6);
        Point p2 = new Point(4, 9);
        Assert.assertEquals(p1.distance(p2), 3.1622776601683795);
    }

}
