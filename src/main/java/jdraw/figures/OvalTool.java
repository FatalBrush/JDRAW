/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import jdraw.framework.DrawContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class OvalTool extends AbstractDrawTool {
	private Oval newOval = null;

	public OvalTool(DrawContext context) {
		super(context);
	}

	@Override
	public void mouseDown(int x, int y, MouseEvent e) {
		if (newOval != null) {
			throw new IllegalStateException();
		}
		anchor = new Point(x, y);
		newOval = new Oval(x, y, 0, 0);
		view.getModel().addFigure(newOval);
	}

	@Override
	public void mouseDrag(int x, int y, MouseEvent e) {
		newOval.setBounds(anchor, new Point(x, y));
		java.awt.Rectangle r = newOval.getBounds();
		this.context.showStatusText("w: " + r.width + ", h: " + r.height);
	}

	@Override
	public void mouseUp(int x, int y, MouseEvent e) {
		newOval = null;
		anchor = null;
		this.context.showStatusText("Oval Mode");
	}

	@Override
	public Icon getIcon() {
		return new ImageIcon(getClass().getResource(IMAGES + "oval.png"));
	}

	@Override
	public String getName() {
		return "Oval";
	}
}
