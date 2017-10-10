package jdraw.figures;

import jdraw.framework.Figure;

import java.awt.*;

public class NorthWestHandle extends AbstractFigureHandle {
    public NorthWestHandle(Figure owner) {
        super(owner);
    }

    @Override
    public Point getLocation() {
        return owner.getBounds().getLocation();
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
    }
}
