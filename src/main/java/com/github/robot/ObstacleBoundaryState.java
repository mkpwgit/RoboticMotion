package com.github.robot;

import com.github.Coordinate;
import com.github.ImagePanel;
import com.github.RGBColor;
import com.github.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author kisel.nikolay@gmail.com
 * @since May 17, 2015.
 */
public class ObstacleBoundaryState implements State {

    private int degrees = 10;
    private List<Coordinate> line;
    private int number = 0;

    public ObstacleBoundaryState(Robot robot) {

    }

    @Override
    public void move(Robot robot) {
        determinateNextStep(robot);
        number++;
        robot.setX(line.get(number).getX());
        robot.setY(line.get(number).getY());
        System.out.println("Obstacle boundary: " + robot);
    }

    private void determinateNextStep(Robot robot) {
        List<Coordinate> oCoords = new ArrayList<Coordinate>();

        int currentDegree;
        RGBColor lastColor = null;
        List<Coordinate> previousSensorLine = new ArrayList<Coordinate>();
        for (currentDegree = 0; currentDegree <= 360; currentDegree += degrees) {
            double angle = currentDegree * Math.PI / 180;
            int endX = (int) Math.round(robot.getX() + robot.getSensor() * Math.sin(angle));
            int endY = (int) Math.round(robot.getY() + robot.getSensor() * Math.cos(angle));

            List<Coordinate> sensorLine = Utils.determineLine(robot.getX(), robot.getY(), endX, endY);
            boolean hasObstacles = false;
            for (Coordinate coordinate : sensorLine) {

                if (coordinate.getX() == robot.getX() && coordinate.getY() == robot.getY()) {
                    continue;
                }
                if (coordinate.getX() < 0 || coordinate.getY() < 0 || coordinate.getX() > 1023 || coordinate.getY() > 763) {
                    continue;
                }

                RGBColor currentColor = RGBColor.getColor(ImagePanel.image.getRGB(coordinate.getX(), coordinate.getY()));
                if (currentColor == RGBColor.BLACK) {
                    hasObstacles = true;
                }
            }

            RGBColor currentColor = null;
            if (hasObstacles) {
                currentColor = RGBColor.BLACK;
            } else {
                currentColor = RGBColor.WHITE;
            }
            if (lastColor != null && lastColor != currentColor) {
                oCoords.add(findFirstBlackPoint(sensorLine, previousSensorLine));
            }
            lastColor = currentColor;
            previousSensorLine = sensorLine;


        }

        Coordinate resultCoord = findMinDistance(robot, oCoords);
        line = Utils.determineLine(robot.getX(), robot.getY(), resultCoord.getX(), resultCoord.getY());
        number = 0;
    }

    @Override
    public State checkState(Robot robot) {
        return this;
    }

    private double countDistance(int x, int y, int endX, int endY) {
        return Math.sqrt((endX - x) * (endX - x) + (endY - y) * (endY - y));
    }

    private Coordinate findMinDistance(Robot robot, List<Coordinate> oCoords) {
        double minDistance = 9999999999999D;
        Coordinate resultCoord = null;
        for (Coordinate coordinate : oCoords) {
            double dist = countDistance(robot.getX(), robot.getY(), coordinate.getX(), coordinate.getY());
            dist += countDistance(coordinate.getX(), coordinate.getY(), ImagePanel.AIM_X, ImagePanel.AIM_Y);
            if (dist < minDistance) {
                minDistance = dist;
                resultCoord = coordinate;
            }
        }

        return resultCoord;
    }

    private Coordinate findFirstBlackPoint(List<Coordinate> coordinates, List<Coordinate> previousCoordinates) {
        for (Coordinate coordinate : coordinates) {
            if (RGBColor.getColor(ImagePanel.image.getRGB(coordinate.getX(), coordinate.getY())) == RGBColor.BLACK) {
                return coordinate;
            }
        }

        for (Coordinate coordinate : previousCoordinates) {
            if (RGBColor.getColor(ImagePanel.image.getRGB(coordinate.getX(), coordinate.getY())) == RGBColor.BLACK) {
                return coordinate;
            }
        }

        return null;
    }
}
