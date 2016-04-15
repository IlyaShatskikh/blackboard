package org.test.client;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya Shatskikh (Ilya.Shackih@ocrv.ru).
 */
public class Util {
    private static final String IP = "localhost";
    private static final int PORT = 29288;

    private static final String FRAME_TITLE = "Blackboard";
    private static final int X = 500;
    private static final int Y = 200;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final double STEP = 0.01;

    private final BlackboardFrame frame;
    private final Client client;

    private final List<Command> commandList = new ArrayList<>();

    public Util() {
        this.frame = new BlackboardFrame(X, Y, WIDTH, HEIGHT, FRAME_TITLE, this);
        this.client = new Client(IP, PORT, this);
        this.client.start();
    }

    public void drawCommands(Command command) {
        if (command.getAction() == Command.Action.START && !commandList.isEmpty()) {
            drawCurve(commandList);
            commandList.clear();
        } else {
            commandList.add(command);
        }
    }

    public void drawCommands() {
        if (!commandList.isEmpty()) {
            drawCurve(commandList);
        }
    }

    public void drawCurve(List<Command> commandList) {
        int width = frame.getWidth();
        int height = frame.getHeight();

        List<Point> points = new ArrayList<>();
        for (Command command : commandList) {
            points.add(new Point((int) (command.getX() * width), (int) (command.getY() * height)));
        }
        drawBezierCurve(points);
    }

    private void drawBezierCurve(List<Point> sourcePoints) {
        List<Point> resultPoints = new ArrayList<>();
        for (double i = 0; i <= 1; i += STEP) {
            resultPoints.add(calculateBezierPoint(i, sourcePoints));
        }
        for (int i = 1; i < resultPoints.size(); i++) {
            int x1 = (int) (resultPoints.get(i - 1).getX());
            int y1 = (int) (resultPoints.get(i - 1).getY());
            int x2 = (int) (resultPoints.get(i).getX());
            int y2 = (int) (resultPoints.get(i).getY());
            frame.addLine(new Line(new Point(x1, y1), new Point(x2, y2)));
        }
        frame.repaint();
    }

    private Point calculateBezierPoint(double step, List<Point> srcPoints) {
        double x = 0;
        double y = 0;

        int n = srcPoints.size() - 1;
        for (int i = 0; i <= n; i++) {
            x += factorial(n) / (factorial(i) * factorial(n - i)) * srcPoints.get(i).getX() * Math.pow(step, i) * Math.pow(1 - step, n - i);
            y += factorial(n) / (factorial(i) * factorial(n - i)) * srcPoints.get(i).getY() * Math.pow(step, i) * Math.pow(1 - step, n - i);
        }
        return new Point((int) x, (int) y);
    }

    private double factorial(double number) {
        if (number == 0) return 1;
        double result = 1;
        for (int i = 1; i <= number; i++)
            result *= i;
        return result;
    }

    public void close() {
        client.closeConnection();
    }

}
