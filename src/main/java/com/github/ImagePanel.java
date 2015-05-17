package com.github;

import com.github.state.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * <p>
 * </p>
 *
 * @author kisel.nikolay@gmail.com
 * @since May 16, 2015.
 */
public class ImagePanel
        extends JPanel {

    public static final String IMAGE_PATH = "/home/mikalai/space4.bmp";

    private static int WIDTH = 1024;
    private static int HEIGHT = 768;

    public static int GOAL_X = 820;
    public static int GOAL_Y = 590;

    private Robot robot;
    private State state;
    private int[][] pixels = new int[WIDTH][HEIGHT];

    public static BufferedImage image;

    public ImagePanel() {
        try {
            image = ImageIO.read(new File(IMAGE_PATH));
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    pixels[i][j] = image.getRGB(i, j);
                    checkImage(image.getRGB(i, j));

                }
            }
            robot = new Robot();
            state = new RightToGoalState(robot);
        } catch (IOException ex) {
            // handle exception...
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
        g.setColor(Color.red);
        g.fillOval(robot.getX()-20, robot.getY()-20, 20, 20);
        g.setColor(Color.BLUE);
        g.fillOval(GOAL_X, GOAL_Y, 40, 40);
        if (!goalIsReached()) {
            changeCoord();
        }
    }

    private void changeCoord() {
        state = state.getState(robot);
        state.move(robot);
    }

    private void checkImage(int rgbValue) {
        RGBColor rgbColor = RGBColor.getColor(rgbValue);
        if (rgbColor == null) {
            throw new RuntimeException("Incorrect picture!");
        }
    }

    private boolean goalIsReached() {
        if (Math.abs(robot.getX() - GOAL_X) <= 5 && Math.abs(robot.getY() - GOAL_Y) <= 5) {
            return true;
        } else {
            return false;
        }
    }

}