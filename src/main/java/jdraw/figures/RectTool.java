/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import jdraw.framework.DrawContext;

public class RectTool extends AbstractDrawTool {
	private Rect newRect = null;

	public RectTool(DrawContext context) {
		super(context);
	}

	@Override
	public void mouseDown(int x, int y, MouseEvent e) {
		if (newRect != null) {
			throw new IllegalStateException();
		}
		anchor = new Point(x, y);
		newRect = new Rect(x, y, 0, 0);
		view.getModel().addFigure(newRect);
	}

	@Override
	public void mouseDrag(int x, int y, MouseEvent e) {
		newRect.setBounds(anchor, new Point(x, y));
		java.awt.Rectangle r = newRect.getBounds();
		this.context.showStatusText("w: " + r.width + ", h: " + r.height);
	}

	@Override
	public void mouseUp(int x, int y, MouseEvent e) {
		newRect = null;
		anchor = null;
		this.context.showStatusText("Rectangle Mode");
	}

	
	@Override
	public Icon getIcon() {
		return new ImageIcon(getClass().getResource(IMAGES + "rectangle.png"));
	}

	@Override
	public String getName() {
		return "Rectangle";
	}
}
