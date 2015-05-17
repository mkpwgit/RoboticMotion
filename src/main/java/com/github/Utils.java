package com.github;

import com.github.robot.Robot;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author kisel.nikolay@gmail.com
 * @since May 17, 2015.
 */
public class Utils {

    public static List<Coordinate> determineLine(Robot robot) {
        List<Coordinate> line = new ArrayList<Coordinate>();

        int d = 10;
        int tempX = robot.getX();
        int tempY = robot.getY();

        int w = ImagePanel.AIM_X - tempX;
        int h = ImagePanel.AIM_Y - tempY;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;

        if (w < 0) {
            dx1 = -d;
        } else if (w > 0) {
            dx1 = d;
        }

        if (h < 0) {
            dy1 = -d;
        } else if (h > 0) {
            dy1 = d;
        }

        if (w < 0) {
            dx2 = -d;
        } else if (w > 0) {
            dx2 = d;
        }

        int longest = Math.abs(w / 10);
        int shortest = Math.abs(h / 10);
        if (!(longest > shortest)) {
            longest = Math.abs(h);
            shortest = Math.abs(w);
            if (h < 0) {
                dy2 = -d;
            } else if (h > 0) {
                dy2 = d;
            }
            dx2 = 0;
        }

        int numerator = longest >> 1;
        for (int i = 0; i <= longest; i++) {
            line.add(new Coordinate(tempX, tempY));
            numerator += shortest;
            if (!(numerator < longest)) {
                numerator -= longest;
                tempX += dx1;
                tempY += dy1;
            } else {
                tempX += dx2;
                tempY += dy2;
            }
        }

        return line;
    }
}
