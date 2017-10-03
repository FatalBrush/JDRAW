/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents rectangles in JDraw.
 * 
 * @author Sebastian Zimmermann
 *
 */
public class Line implements Figure {
	private List<FigureListener> myObservers = new CopyOnWriteArrayList<>(); // use COWAL in order to avoid problems with observer removal while sending notifications
	private static boolean myObserversAreBeingNotified = false; // in order to avoid notification cycles

	/**
	 * Use the java.awt.Rectangle in order to save/reuse code.
	 */
	private Line2D line2D;

	/**
	 * Create a new rectangle of the given dimension.
	 * @param x the x-coordinate of the upper left corner of the rectangle
	 * @param y the y-coordinate of the upper left corner of the rectangle
	 * @param w the rectangle's width
	 * @param h the rectangle's height
	 */
	public Line(int x, int y, int w, int h) {
		line2D = new Line2D.Double(new Point2D.Double(x, y), new Point2D.Double(w, h));
	}

	/**
	 * Draw the rectangle to the given graphics context.
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine((int)line2D.getX1(), (int)line2D.getY1(), (int)line2D.getX2(), (int)line2D.getY2());
		myObservers.forEach(figureListener -> figureListener.figureChanged(new FigureEvent(this)));
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		line2D.setLine(origin, corner);
	}

	@Override
	public void move(int dx, int dy) {
		if(dx == line2D.getX1() && dy == line2D.getY1()){ // TODO: check starting and end point
			// same position, no changes needed
		} else {
			line2D.setLine(line2D.getX1()+dx, line2D.getY1()+dy, line2D.getX2(), line2D.getY2()); // TODO: work with individual points
			if(!myObserversAreBeingNotified){
				myObserversAreBeingNotified = true;
				myObservers.forEach(figureListener -> figureListener.figureChanged(new FigureEvent(this)));
			}
			myObserversAreBeingNotified = false;
		}
	}

	@Override
	public boolean contains(int x, int y) {
		return line2D.contains(new Point2D.Double(x,y));
	}

	@Override
	public Rectangle getBounds() {
		return line2D.getBounds();
	}

	/**
	 * Returns a list of 8 handles for this Rectangle.
	 * @return all handles that are attached to the targeted figure.
	 * @see Figure#getHandles()
	 */	
	@Override
	public List<FigureHandle> getHandles() {
		return null;
	}

	@Override
	public void addFigureListener(FigureListener listener) {
		if(listener == null){return;}
		if(!myObservers.contains(listener)){
			myObservers.add(listener);
		}
	}

	@Override
	public void removeFigureListener(FigureListener listener) {
		if(listener != null){
			myObservers.remove(listener);
		}
	}

	@Override
	public Figure clone() {
		return null;
	}
}
