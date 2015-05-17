package com.github.robot;

import com.github.Coordinate;
import com.github.ImagePanel;
import com.github.RGBColor;
import com.github.Utils;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author kisel.nikolay@gmail.com
 * @since May 17, 2015.
 */
public class RightToGoalState implements State {

    private List<Coordinate> line;
    private int number = 0;

    public RightToGoalState(Robot robot) {
        line = Utils.determineLine(robot);
        number = 0;
    }

    @Override
    public void move(Robot robot) {
        number++;
        robot.setX(line.get(number).getX());
        robot.setY(line.get(number).getY());
        System.out.println("Right to goal. "+ robot);
    }

    @Override
    public State checkState(Robot robot) {
        for (int i = number; i < number + 6; i++) {
            if (RGBColor.getColor(ImagePanel.image.getRGB(line.get(i).getX(), line.get(i).getY())) == RGBColor.BLACK) {
                return new ObstacleBoundaryState(robot);
            }
        }

        return this;
    }
}
