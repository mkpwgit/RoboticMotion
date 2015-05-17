package com.github.robot;

import com.github.Coordinate;
import com.github.ImagePanel;
import com.github.RGBColor;
import com.github.Utils;

import java.util.List;

import static com.github.robot.FollowWallState.Direction.EAST;
import static com.github.robot.FollowWallState.Direction.NORTH;
import static com.github.robot.FollowWallState.Direction.SOUTH;
import static com.github.robot.FollowWallState.Direction.WEST;

/**
 * <p>
 * </p>
 *
 * @author kisel.nikolay@gmail.com
 * @since May 17, 2015.
 */
public class FollowWallState implements State {

    private Direction wallDirection;
    private int stepOut = 0;

    public FollowWallState(Robot robot) {
        if (RGBColor.getColor(ImagePanel.image.getRGB(robot.getX() - 40, robot.getY())) == RGBColor.BLACK) {
            wallDirection = WEST;
        } else if (RGBColor.getColor(ImagePanel.image.getRGB(robot.getX() + 40, robot.getY())) == RGBColor.BLACK) {
            wallDirection = EAST;
        } else if (RGBColor.getColor(ImagePanel.image.getRGB(robot.getX(), robot.getY() + 40)) == RGBColor.BLACK) {
            wallDirection = SOUTH;
        } else {
            wallDirection = NORTH;
        }

    }

    @Override
    public void move(Robot robot) {
        System.out.println("Follow wall: " + robot + " Direction: " + wallDirection);
        switch (wallDirection) {
            case NORTH:
                robot.setX(robot.getX() - 10);
                break;
            case SOUTH:
                robot.setX(robot.getX() + 10);
                break;
            case WEST:
                robot.setY(robot.getY() + 10);
                break;
            case EAST:
                robot.setY(robot.getY() - 10);
                break;
        }

    }

    @Override
    public State getState(Robot robot) {
        boolean hasWall = false;
        if (RGBColor.getColor(ImagePanel.image.getRGB(robot.getX() - 20, robot.getY())) == RGBColor.BLACK) {
            hasWall = true;
        } else if (RGBColor.getColor(ImagePanel.image.getRGB(robot.getX() + 20, robot.getY())) == RGBColor.BLACK) {
            hasWall = true;
        } else if (RGBColor.getColor(ImagePanel.image.getRGB(robot.getX(), robot.getY() - 20)) == RGBColor.BLACK) {
            hasWall = true;
        } else if (RGBColor.getColor(ImagePanel.image.getRGB(robot.getX(), robot.getY() + 20)) == RGBColor.BLACK) {
            hasWall = true;
        }

        if (!hasWall) {
            stepOut++;
        }

        if (stepOut < 2) {
            return this;
        } else {
            List<Coordinate> line = Utils.determineLine(robot);
            int number = 0;
            for (int i = number; i < number + 4; i++) {
                if (RGBColor.getColor(ImagePanel.image.getRGB(line.get(i).getX(), line.get(i).getY())) == RGBColor.BLACK) {
                    stepOut = 0;
                    changeDirection();
                    return this;
                }
            }

            return new RightToGoalState(robot);
        }
    }

    public static enum Direction {
        NORTH, SOUTH, WEST, EAST;
    }

    private void changeDirection() {
        Direction newDirection = wallDirection;
        switch (newDirection) {
            case EAST:
                wallDirection = SOUTH;
                break;
            case SOUTH:
                wallDirection = WEST;
                break;
            case WEST:
                wallDirection = NORTH;
                break;
            case NORTH:
                wallDirection = EAST;
                break;

        }
    }
}
