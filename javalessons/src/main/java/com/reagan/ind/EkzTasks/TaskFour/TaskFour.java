package com.reagan.ind.EkzTasks.TaskFour;

import javax.swing.*;
import java.awt.*;

public class TaskFour extends JFrame {
    private static int winWidth = 1600;
    private static int winHeight = 900;

    class Circle {
        Point2D center;
        int radius;

        public Circle(Point2D p, int r) {
            center = p;
            radius = r;
        }

        public boolean isInCircle(Point2D p) {
            return ((Math.pow(p.x - center.x, 2) + Math.pow(p.y - center.y, 2)) < Math.pow(radius, 2));
        }
    }

    class Triangle {
        Point2D[] points = new Point2D[3];

        Triangle(Point2D p1, Point2D p2, Point2D p3) {
            points[0] = p1;
            points[1] = p2;
            points[2] = p3;
        }

        boolean isInTriangle(Point2D p) {
            double q1 = (points[0].x - p.x) * (points[1].y - points[0].y) - (points[1].x - points[0].x) * (points[0].y - p.y);
            double q2 = (points[1].x - p.x) * (points[2].y - points[1].y) - (points[2].x - points[1].x) * (points[1].y - p.y);
            double q3 = (points[2].x - p.x) * (points[0].y - points[2].y) - (points[0].x - points[2].x) * (points[2].y - p.y);
            return (((q1 > 0) & (q2 > 0) & (q3 > 0)) || ((q1<0) & (q2<0) & (q3<0)));
        }
    }

    class Pentagon {
        Point2D[] points = new Point2D[5];

        Pentagon(Point2D p1, Point2D p2, Point2D p3, Point2D p4, Point2D p5) {
            points[0] = p1;
            points[1] = p2;
            points[2] = p3;
            points[3] = p4;
            points[4] = p5;
        }

        boolean isInPentagon(Point2D p) {
            boolean c = false;
            int i, j;
            for (i = 0, j=4; i < 5; j = i++) {
                /*
                if ((((points[i].y <= p.y) & (p.y < points[j].y)) || ((points[j].y <= p.y) & (p.y < points[i].y))) &
                        (p.x > (points[j].x - points[i].x)*(p.y - points[i].y)/(points[j].y - points[i].y + points[i].x))) {
                    c = !c;
                }
                j = i;
                 */
                if ((points[i].y > p.y) != (points[j].y > p.y) && (p.x < (points[j].x-points[i].x)*(p.y-points[i].y) / (points[j].y - points[i].y) + points[i].x)) {
                    c = !c;
                }
            }
            return c;
        }
    }

    private TaskFour() {
        setTitle("Задача №4");
        setSize(new Dimension(winWidth, winHeight));
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize(), fSize = getSize();
        if (fSize.height > sSize.height) {fSize.height = sSize.height;}
        if (fSize.width > sSize.width) {fSize.width = sSize.width;}
        setLocation((sSize.width - fSize.width)/2, (sSize.height - fSize.height)/2);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void paint(Graphics graphics) {
        Circle circle = new Circle(new Point2D(300, 300), 50);
        Triangle triangle = new Triangle(new Point2D(300, 230),
                new Point2D(370, 350),
                new Point2D(230, 350));
        Pentagon pentagon = new Pentagon(new Point2D(50, 130),
                new Point2D(450, 500),
                new Point2D(470, 510),
                new Point2D(530, 450),
                new Point2D(500, 430));

        graphics.setColor(Color.BLACK);
        graphics.drawOval(circle.center.x-circle.radius, circle.center.y-circle.radius, circle.radius*2, circle.radius*2);

        graphics.drawLine(triangle.points[0].x, triangle.points[0].y, triangle.points[1].x, triangle.points[1].y);
        graphics.drawLine(triangle.points[1].x, triangle.points[1].y, triangle.points[2].x, triangle.points[2].y);
        graphics.drawLine(triangle.points[2].x, triangle.points[2].y, triangle.points[0].x, triangle.points[0].y);

        graphics.drawLine(pentagon.points[0].x, pentagon.points[0].y, pentagon.points[1].x, pentagon.points[1].y);
        graphics.drawLine(pentagon.points[1].x, pentagon.points[1].y, pentagon.points[2].x, pentagon.points[2].y);
        graphics.drawLine(pentagon.points[2].x, pentagon.points[2].y, pentagon.points[3].x, pentagon.points[3].y);
        graphics.drawLine(pentagon.points[3].x, pentagon.points[3].y, pentagon.points[4].x, pentagon.points[4].y);
        graphics.drawLine(pentagon.points[4].x, pentagon.points[4].y, pentagon.points[0].x, pentagon.points[0].y);

        graphics.setColor(Color.RED);
        for (int y = 0; y < winHeight; y++) {
            for (int x = 0; x < winWidth; x++) {
                Point2D point = new Point2D(x, y);
                if (circle.isInCircle(point) && triangle.isInTriangle(point) && pentagon.isInPentagon(point)) {
                //if (pentagon.isInPentagon(point)) {
                    graphics.drawRect(x, y, 1, 1);
                }
            }
        }
    }

    private void run() {
        paint(getGraphics());
    }

    public static void main(String[] args) {
        TaskFour task = new TaskFour();
        task.run();
    }
}
