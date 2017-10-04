package jdraw.figures;

import jdraw.framework.DrawContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class LineTool extends AbstractDrawTool {
	private Line newLine = null;

	public LineTool(DrawContext context) {
		super(context);
	}

	@Override
	public void mouseDown(int x, int y, MouseEvent e) {
		if (newLine != null) {
			throw new IllegalStateException();
		}
		anchor = new Point(x, y);
		newLine = new Line(x, y, x+1, y+1);
		view.getModel().addFigure(newLine);
	}

	@Override
	public void mouseDrag(int x, int y, MouseEvent e) {
		newLine.setBounds(anchor, new Point(x, y));
		double length = Math.sqrt((Math.pow((int)(x - anchor.x),2)+ Math.pow((y - anchor.y),2)));;
		this.context.showStatusText("Line lenght: " + Math.round(length));
	}

	@Override
	public void mouseUp(int x, int y, MouseEvent e) {
		newLine = null;
		anchor = null;
		this.context.showStatusText("Line Mode");
	}

	@Override
	public Icon getIcon() {
		return new ImageIcon(getClass().getResource(IMAGES + "line.png"));
	}

	@Override
	public String getName() {
		return "Line";
	}

}
