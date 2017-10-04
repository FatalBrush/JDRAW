package jdraw.figures;

import jdraw.framework.FigureEvent;
import java.awt.*;
import java.awt.geom.Line2D;


public class Line extends AbstractFigure {
	private static final int INTER_SIZE = 4;
	private Point originPoint = null;
	private Point endPoint = null;

	public Line(int x, int y, int w, int h) {
		originPoint = new Point(x, y);
		endPoint = new Point(w, h);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine(originPoint.x, originPoint.y, endPoint.x, endPoint.y);
		propagateFigureEvent(new FigureEvent(this));
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		Line original = new Line(origin.x, origin.y, corner.x, corner.y);
		originPoint = origin;
		endPoint = corner;
		if(!original.equals(this)){
			propagateFigureEvent(new FigureEvent(this));
		}
	}

	@Override
	public void move(int dx, int dy) {
		if(dx == originPoint.x && dy == originPoint.y){ // TODO: check starting and end point
			// same position, no changes needed
		} else {
			Point newOriginPoint = new Point(originPoint.x + dx, originPoint.y + dy);
			Point newEndPoint = new Point(endPoint.x + dx, endPoint.y + dy);
			originPoint = newOriginPoint;
			endPoint = newEndPoint;
			if(!myObserversAreBeingNotified){
				myObserversAreBeingNotified = true;
				propagateFigureEvent(new FigureEvent(this));
			}
			myObserversAreBeingNotified = false;
		}
	}

	@Override
	public boolean contains(int x, int y) {
		Line2D tmpLine = new Line2D.Double(originPoint, endPoint);
		return tmpLine.intersects(x - INTER_SIZE/2, y - INTER_SIZE/2, INTER_SIZE, INTER_SIZE);
	}

	@Override
	public Rectangle getBounds() {
		int width = originPoint.x - endPoint.x;
		if(width < 0){
			width = width * -1;
		}
		int height = originPoint.y - endPoint.y;
		if(height < 0){
			height = height * -1;
		}

		if(originPoint.x < endPoint.x && originPoint.y < endPoint.y){
			return new Rectangle(originPoint.x, originPoint.y, width, height);
		}

		if(originPoint.x < endPoint.x && originPoint.y > endPoint.y){
			return new Rectangle(originPoint.x, endPoint.y, width, height);
		}

		if(originPoint.x > endPoint.x && originPoint.y < endPoint.y){
			return new Rectangle(endPoint.x, originPoint.y, width, height);
		}

		if(originPoint.x > endPoint.x && originPoint.y > endPoint.y){
			return new Rectangle(endPoint.x, endPoint.y, width, height);
		}

		if(originPoint.x > endPoint.x && originPoint.y == endPoint.y){
			return new Rectangle(endPoint.x, endPoint.y, width, height);
		}

		if(originPoint.x < endPoint.x && originPoint.y == endPoint.y){
			return new Rectangle(originPoint.x, originPoint.y, width, height);
		}

		if(originPoint.x == endPoint.y && originPoint.y > endPoint.y){
			return new Rectangle(endPoint.x, endPoint.y, width, height);
		}

		if(originPoint.x == endPoint.y && originPoint.y < endPoint.y){
			return new Rectangle(originPoint.x, originPoint.y, width, height);
		}

		return null;
	}
}
