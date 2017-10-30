package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GroupFigure implements Figure {
    private List<Figure> parts = new ArrayList<>(); // TODO: maybe use COWAL
    private Rectangle bounds;

    public GroupFigure(List<Figure> partsForThisGroupFigure){
        this.parts = partsForThisGroupFigure;
        getBounds();
    }

    @Override
    public void draw(Graphics g) {
        parts.forEach(figure -> figure.draw(g));
    }

    @Override
    public void move(int dx, int dy) {
        parts.forEach(figure -> figure.move(dx, dy));
    }

    @Override
    public boolean contains(int x, int y) {
        return bounds != null && bounds.contains(x, y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {

    }

    @Override
    public Rectangle getBounds() {
        if(!parts.isEmpty()){
            Iterator<Figure> partsIt = parts.iterator();
            Rectangle groupBound = partsIt.next().getBounds();
            while(partsIt.hasNext()){
                groupBound.add(partsIt.next().getBounds());
            }
            bounds = groupBound;
            return groupBound;
        }
        return null;
    }

    @Override
    public List<FigureHandle> getHandles() {
        return null;
    }

    @Override
    public void addFigureListener(FigureListener listener) {

    }

    @Override
    public void removeFigureListener(FigureListener listener) {

    }

    @Override
    public Figure clone() {
        return null;
    }
}
