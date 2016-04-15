package org.test.client;

/**
 * Created by by Ilya Shatskikh (shatskikh.ilya@gmail.com)
 */
public class Command {
    private int color;
    private String deviceId;
    private double x;
    private double y;
    private Action action;
    public enum Action {START, MOVE}

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
