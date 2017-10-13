package jdraw.figures;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import java.awt.*;
import java.awt.event.MouseEvent;

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

    @Override
    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
        Rectangle r = owner.getBounds();
        anchorPoint = new Point(r.x + r.width, r.y);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        Rectangle r = owner.getBounds();
        owner.setBounds(new Point(x, anchorPoint.y), new Point(anchorPoint.x, anchorPoint.y + r.height));
    }
}
