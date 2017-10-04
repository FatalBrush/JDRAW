package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;

import java.awt.*;

public abstract class AbstractDrawTool implements DrawTool {
    protected static final String IMAGES = "/images/";
    protected DrawContext context;
    protected DrawView view;
    protected Point anchor = null;

    public AbstractDrawTool(DrawContext context){
        this.context = context;
        this.view = context.getView();
    }

    @Override
    public void deactivate() {
        this.context.showStatusText("");
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

    @Override
    public void activate() {
        this.context.showStatusText(getName() + " Mode");
    }
}
