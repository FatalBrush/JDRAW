package jdraw.figures;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import java.awt.*;
import java.awt.event.MouseEvent;

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

    @Override
    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
        Rectangle r = owner.getBounds();
        anchorPoint = new Point(r.x + r.width, r.y + r.height);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        owner.setBounds(new Point(x, y), anchorPoint);
    }
}
