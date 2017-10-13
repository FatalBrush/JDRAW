package jdraw.figures;

import jdraw.framework.Figure;

import java.awt.*;

public class WestHandle extends AbstractFigureHandle {
    public WestHandle(Figure owner) {
        super(owner);
    }

    @Override
    public Point getLocation() {
        Point tmp = owner.getBounds().getLocation();
        tmp.y += owner.getBounds().getHeight()/2;
        return tmp;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
    }
}
