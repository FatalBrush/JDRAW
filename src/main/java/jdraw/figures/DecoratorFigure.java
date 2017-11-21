package jdraw.figures;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DecoratorFigure implements Figure {
    private Figure innerFigure;

    public DecoratorFigure(Figure figureToDecorate){
        this.innerFigure = figureToDecorate;
    }

    @Override
    public void draw(Graphics g) {
        innerFigure.draw(g);
    }

    @Override
    public void move(int dx, int dy) {
        innerFigure.move(dx, dy);
    }

    @Override
    public boolean contains(int x, int y) {
        return innerFigure.contains(x, y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        innerFigure.setBounds(origin, corner);
    }

    @Override
    public Rectangle getBounds() {
        return innerFigure.getBounds();
    }

    @Override
    public List<FigureHandle> getHandles() {
        // Handles must be decorated to!
        List<FigureHandle> handles = new LinkedList<>();
        for(FigureHandle handle : innerFigure.getHandles()){
            handles.add(new DecoratorHandle(handle));
        }
        return Collections.unmodifiableList(handles);
    }

    @Override
    public void addFigureListener(FigureListener listener) {
        innerFigure.addFigureListener(listener);
    }

    @Override
    public void removeFigureListener(FigureListener listener) {
        innerFigure.removeFigureListener(listener);
    }

    @Override
    public Figure clone() {
        DecoratorFigure df = null;
        try{
            df = (DecoratorFigure) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return df;
    }

    protected Figure getInnerFigure(){ // helper method
        return innerFigure;
    }

    private final class DecoratorHandle implements FigureHandle {
        private final FigureHandle innerHandle;

        public DecoratorHandle(FigureHandle handle) {
            this.innerHandle = handle;
        }

        @Override
        public Figure getOwner() {
            return DecoratorFigure.this;
        }

        @Override
        public Point getLocation() {
            return innerHandle.getLocation();
        }

        @Override
        public void draw(Graphics g) {
            innerHandle.draw(g);
        }

        @Override
        public Cursor getCursor() {
            return innerHandle.getCursor();
        }

        @Override
        public boolean contains(int x, int y) {
            return innerHandle.contains(x,y);
        }

        @Override
        public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
            innerHandle.startInteraction(x, y, e, v);
        }

        @Override
        public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
            innerHandle.dragInteraction(x,y,e,v);
        }

        @Override
        public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
            innerHandle.stopInteraction(x,y,e,v);
        }
    }
}
