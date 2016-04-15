package org.test.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by by Ilya Shatskikh (shatskikh.ilya@gmail.com)
 */
public class BlackboardFrame extends JFrame {
    private List<Line> lines;

    public BlackboardFrame(int x, int y, int width, int height, String title, final Util util) {
        setTitle(title);
        setBounds(x, y, width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                util.close();
                e.getWindow().dispose();
            }
        });

    }

    @Override
    public synchronized void paint(Graphics g) {
        if (lines != null) {
            for (Line line : lines) {
                Point a = line.getA();
                Point b = line.getB();
                g.drawLine(a.x, a.y, b.x, b.y);
            }
        }
    }

    public synchronized void addLine(Line line) {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        lines.add(line);
    }
}