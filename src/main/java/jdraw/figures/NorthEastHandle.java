package jdraw.figures;

import jdraw.framework.Figure;

import java.awt.*;

public class NorthEastHandle extends AbstractFigureHandle {
    public NorthEastHandle(Figure owner) {
        super(owner);
    }

    @Override
    public Point getLocation() {
        Point tmp = owner.getBounds().getLocation();
        tmp.x += owner.getBounds().getWidth();
        return tmp;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
    }
}
