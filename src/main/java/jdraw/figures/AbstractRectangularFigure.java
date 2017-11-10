package jdraw.figures;

import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

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

    @Override
    public List<FigureHandle> getHandles() {
        List<FigureHandle> handles = new LinkedList<>();
        handles.add(new NorthHandle(this));
        handles.add(new NorthEastHandle(this));
        handles.add(new EastHandle(this));
        handles.add(new SouthEastHandle(this));
        handles.add(new SouthHandle(this));
        handles.add(new SouthWestHandle(this));
        handles.add(new WestHandle(this));
        handles.add(new NorthWestHandle(this));
        return handles;
    }

    @Override
    public AbstractRectangularFigure clone() {
        AbstractRectangularFigure clone = (AbstractRectangularFigure) super.clone(); // copy the basis
        clone.rectangle = (Rectangle) this.rectangle.clone(); // copy this class's attributes
        return clone;
    }
}
