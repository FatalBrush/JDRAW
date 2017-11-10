package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractFigure implements Figure {
    protected List<FigureListener> myObservers = new CopyOnWriteArrayList<>(); // use COWAL in order to avoid problems with observer removal while sending notifications
    protected static boolean myObserversAreBeingNotified = false; // in order to avoid notification cycles

    @Override
    public List<FigureHandle> getHandles(){
        List<FigureHandle> handles = new LinkedList<>();
        handles.add(new NorthWestHandle(this));
        handles.add(new SouthEastHandle(this));
        return handles;
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
    public AbstractFigure clone() {
        AbstractFigure clone = null;
        try {
            clone = (AbstractFigure) super.clone();
            //myObservers.forEach(clone::addFigureListener); shallow copy of listeners
        }  catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    protected void propagateFigureEvent(FigureEvent e){
        myObservers.forEach(figureListener -> figureListener.figureChanged(e));
    }
}
