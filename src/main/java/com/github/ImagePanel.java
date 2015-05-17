package com.github;

import com.github.robot.*;

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

    private static int WIDTH = 1024;
    private static int HEIGHT = 768;

    public static int AIM_X = 820;
    public static int AIM_Y = 590;
//    public static int AIM_Y = 390;

    private com.github.robot.Robot robot;
    private State state;

    private int[][] pixels = new int[WIDTH][HEIGHT];

    int sensor = 30;

    private BufferedImage image;

    public ImagePanel() {
        try {
            image = ImageIO.read(new File("/home/mikalai/space4.bmp"));
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    pixels[i][j] = image.getRGB(i, j);
                    checkColor(image.getRGB(i, j));

                }
            }
            robot = new com.github.robot.Robot();
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
        g.fillOval(robot.getX(), robot.getY(), 20, 20);
        g.setColor(Color.BLUE);
        g.fillOval(AIM_X, AIM_Y, 40, 40);
        changeCoord();
    }

    private void changeCoord() {
        state.move(robot);
    }

    private void checkColor(int rgbValue) {
        RGBColor rgbColor = RGBColor.getColor(rgbValue);
        if (rgbColor == null) {
            throw new RuntimeException("Incorrect picture!");
        }
    }

}