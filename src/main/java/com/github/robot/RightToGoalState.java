package com.github.robot;

import com.github.Coordinate;
import com.github.Utils;
import com.github.robot.Robot;
import com.github.robot.State;

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
    }
}
