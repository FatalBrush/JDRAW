/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import jdraw.framework.FigureEvent;

public class Rect extends AbstractRectangularFigure {
	public Rect(int x, int y, int w, int h) {
		super(x,y,w,h);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		g.setColor(Color.BLACK);
		g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		propagateFigureEvent(new FigureEvent(this));
	}

	@Override
	public boolean contains(int x, int y) {
		return rectangle.contains(x, y);
	}
}
