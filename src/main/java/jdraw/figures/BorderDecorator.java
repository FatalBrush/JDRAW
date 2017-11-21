package jdraw.figures;

import jdraw.framework.Figure;

import java.awt.*;

public class BorderDecorator extends DecoratorFigure {
    private static final int BORDER_OFFSET = 5;

    public BorderDecorator(Figure figureToDecorate) {
        super(figureToDecorate);
    }

    @Override
    public Rectangle getBounds(){
        Rectangle r = getInnerFigure().getBounds();
        r.grow(BORDER_OFFSET, BORDER_OFFSET);
        return r;
    }

    @Override
    public void draw(Graphics g){
        super.draw(g);
        Rectangle r = getBounds();
        g.setColor(Color.white);
        g.drawLine(r.x, r.y, r.x, r.y + r.height);
        g.drawLine(r.x, r.y, r.x + r.width, r.y);
        g.setColor(Color.gray);
        g.drawLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height);
        g.drawLine(r.x, r.y + r.height, r.x + r.width, r.y + r.height);
    }

    @Override
    public boolean contains(int x, int y){
        return getBounds().contains(x,y);
    }
}
