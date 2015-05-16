package com.github;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import static com.github.RGBColor.*;

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

    private static int START_X = 20;
    private static int START_Y = 20;
    private static int AIM_X = 820;
    //    private static int AIM_Y = 590;
    private static int AIM_Y = 390;

    private int x = START_X;
    private int y = START_Y;

    int[][] pixels = new int[WIDTH][HEIGHT];
    ArrayList<Coordinate> line = new ArrayList<Coordinate>();
    int step = 0;

    private int whitePixels = 0;
    private int blackPixels = 0;
    private int unknownPixels = 0;

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
        } catch (IOException ex) {
            // handle exception...
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
        g.setColor(Color.red);
        g.fillOval(x, y, 20, 20);
        g.setColor(Color.BLUE);
        g.fillOval(AIM_X, AIM_Y, 40, 40);
        determineLine();
        changeCoord();
    }

    private void changeCoord() {
        x = line.get(step).getX();
        y = line.get(step).getY();

        step++;
    }

    private void checkColor(int rgbValue) {
        RGBColor rgbColor = RGBColor.getColor(rgbValue);
        if (rgbColor == null) {
            unknownPixels++;
            throw new RuntimeException("Incorrect picture!");
        }

        switch (rgbColor) {
            case WHITE:
                whitePixels++;
                break;
            case BLACK:
                blackPixels++;
                break;
            default:
                unknownPixels++;
        }
    }

    public void determineLine() {
        int d = 10;
        int tempX = x;
        int tempY = y;

        int w = AIM_X - tempX;
        int h = AIM_Y - tempY;
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
    }

}