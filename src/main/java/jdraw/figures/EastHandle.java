package jdraw.figures;

import jdraw.framework.Figure;

import java.awt.*;

public class EastHandle extends AbstractFigureHandle {
    public EastHandle(Figure owner) {
        super(owner);
    }

    @Override
    public Point getLocation() {
        Point tmp = owner.getBounds().getLocation();
        tmp.x += owner.getBounds().getWidth();
        tmp.y += owner.getBounds().getHeight()/2;
        return tmp;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
    }
}
