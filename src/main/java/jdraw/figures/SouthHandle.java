package jdraw.figures;

import jdraw.framework.Figure;

import java.awt.*;

public class SouthHandle extends AbstractFigureHandle {
    public SouthHandle(Figure owner) {
        super(owner);
    }

    @Override
    public Point getLocation() {
        Point tmp = owner.getBounds().getLocation();
        tmp.x += owner.getBounds().getWidth()/2;
        tmp.y += owner.getBounds().getHeight();
        return tmp;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
    }
}
