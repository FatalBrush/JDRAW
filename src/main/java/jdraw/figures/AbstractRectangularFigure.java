package jdraw.figures;

import jdraw.framework.FigureEvent;

import java.awt.*;

public abstract class AbstractRectangularFigure extends AbstractFigure {
    protected java.awt.Rectangle rectangle;

    public AbstractRectangularFigure(int x, int y, int w, int h){
        rectangle = new java.awt.Rectangle(x, y, w, h);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        rectangle.setFrameFromDiagonal(origin, corner);
    }

    @Override
    public void move(int dx, int dy) {
        if(dx == rectangle.getX() && dy == rectangle.getY()){
            // same position, no changes needed
        } else {
            rectangle.setLocation(rectangle.x + dx, rectangle.y + dy);
            if(!myObserversAreBeingNotified){
                myObserversAreBeingNotified = true;
                propagateFigureEvent(new FigureEvent(this));
            }
            myObserversAreBeingNotified = false;
        }
    }

    @Override
    public Rectangle getBounds() {
        return rectangle.getBounds();
    }
}
