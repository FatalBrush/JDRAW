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
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents rectangles in JDraw.
 * 
 * @author Sebastian Zimmermann
 *
 */
public class Oval implements Figure {
	private List<FigureListener> myObservers = new CopyOnWriteArrayList<>(); // use COWAL in order to avoid problems with observer removal while sending notifications
	private static boolean myObserversAreBeingNotified = false; // in order to avoid notification cycles

	/**
	 * Use the java.awt.Rectangle in order to save/reuse code.
	 */
	//private Ellipse2D ellipse2D;
	private Rectangle rectangle;

	/**
	 * Create a new rectangle of the given dimension.
	 * @param x the x-coordinate of the upper left corner of the rectangle
	 * @param y the y-coordinate of the upper left corner of the rectangle
	 * @param w the rectangle's width
	 * @param h the rectangle's height
	 */
	public Oval(int x, int y, int w, int h) {
		rectangle = new Rectangle(x, y, w, h);
	}

	/**
	 * Draw the rectangle to the given graphics context.
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		g.setColor(Color.BLACK);
		g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		myObservers.forEach(figureListener -> figureListener.figureChanged(new FigureEvent(this)));
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		rectangle.setFrameFromDiagonal(origin, corner);
	}

	@Override
	public void move(int dx, int dy) {
		if(dx == rectangle.getX() && dy == rectangle.getY()){
			// same position, no changes needed
		} else {
			rectangle.setLocation(rectangle.x + dx, rectangle.y + dy);
			if(!myObserversAreBeingNotified){
				myObserversAreBeingNotified = true;
				myObservers.forEach(figureListener -> figureListener.figureChanged(new FigureEvent(this)));
			}
			myObserversAreBeingNotified = false;
		}
	}

	@Override
	public boolean contains(int x, int y) {
		return rectangle.contains(x, y);
	}

	@Override
	public Rectangle getBounds() {
		return rectangle.getBounds();
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
