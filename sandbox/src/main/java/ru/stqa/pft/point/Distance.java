package ru.stqa.pft.point;

public class Distance {

    public static void main(String[] args) {

        Point p1 = new Point(12, 4);
        Point p2 = new Point(18, 8);
        Point legs = new Point((p1.x - p2.x), (p1.y - p2.y));
        System.out.println("Расстояние между точками " + "p1(" + p1.x + ", " + p1.y + ")" + " и " + "p2(" + p2.x + ", " + p2.y + ")" + " = " + legs.distance());
    }

}