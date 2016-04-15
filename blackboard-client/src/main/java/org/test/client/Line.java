package org.test.client;

import java.awt.*;

/**
 * Created by by Ilya Shatskikh (shatskikh.ilya@gmail.com)
 */
public class Line {
    private final Point a;

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public Line(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    private final Point b;
}
