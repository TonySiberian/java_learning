package ru.stqa.pft.point;

public class Point {

    public double xa;
    public double ya;
    public double xb;
    public double yb;

    public Point(double xa, double ya, double xb, double yb){
        this.xa = xa;
        this.ya = ya;
        this.xb = xb;
        this.yb = yb;
    }

    public double distance() {
        return Math.sqrt(Math.pow((this.xa - this.xb), 2) + Math.pow((this.ya - this.yb), 2));
    }
}