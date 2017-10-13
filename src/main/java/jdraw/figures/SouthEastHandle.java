package jdraw.figures;

import jdraw.framework.Figure;

import java.awt.*;

public class SouthEastHandle extends AbstractFigureHandle {
    public SouthEastHandle(Figure owner) {
        super(owner);
    }

    @Override
    public Point getLocation() {
        Point tmp = owner.getBounds().getLocation();
        tmp.x += owner.getBounds().getWidth();
        tmp.y += owner.getBounds().getHeight();
        return tmp;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
    }
}
