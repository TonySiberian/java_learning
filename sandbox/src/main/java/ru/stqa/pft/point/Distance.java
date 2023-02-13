package ru.stqa.pft.point;

public class Distance {

    public static void main(String[] args) {

        Point p = new Point(12, 4, 18, 19);
        System.out.println("Расстояние между точками " + "a(" + p.xa + ", " + p.ya + ")" + " и " + "b(" + p.xb + ", " + p.yb + ")" + " = " + p.distance());
    }
}