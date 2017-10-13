package jdraw.figures;

import jdraw.framework.Figure;

import java.awt.*;

public class SouthWestHandle extends AbstractFigureHandle {
    public SouthWestHandle(Figure owner) {
        super(owner);
    }

    @Override
    public Point getLocation() {
        Point tmp = owner.getBounds().getLocation();
        tmp.y += owner.getBounds().getHeight();
        return tmp;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
    }
}
