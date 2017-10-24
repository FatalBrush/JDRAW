package jdraw.grid;

import jdraw.framework.PointConstrainer;

import java.awt.*;

public class SimpleGrid implements PointConstrainer {
    private int stepX;
    private int stepY;

    public SimpleGrid(int sx, int sy){
        this.stepX = Math.max(1, sx);
        this.stepY = Math.max(1, sy);
    }

    @Override
    public Point constrainPoint(Point p) {
        /*
        Example: p.x = 128 and stepX = 10
        128 + 10/2 = 133
        133 / 10 = 13
        13 * 10 = 130 <- is now the constrainPoint
         */
        int x = ((p.x + stepX/2)/stepX) * stepX;
        int y = ((p.y + stepY/2)/stepY) * stepY;
        return new Point(x, y);
    }

    @Override
    public int getStepX(boolean right) {
        return stepX;
    }

    @Override
    public int getStepY(boolean down) {
        return stepY;
    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void mouseDown() {

    }

    @Override
    public void mouseUp() {

    }
}
