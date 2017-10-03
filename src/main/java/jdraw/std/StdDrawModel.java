/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.std;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jdraw.framework.*;

/**
 * Provide a standard behavior for the drawing model. This class initially does not implement the methods
 * in a proper way.
 * It is part of the course assignments to do so.
 * @author Sebastian Zimmermann
 *
 */
public class StdDrawModel implements DrawModel, FigureListener {
	private List<Figure> listOfFigures = new ArrayList<>();
	private List<DrawModelListener> listOfObservers = new ArrayList<>();

	@Override
	public void addFigure(Figure f) {
		if(f == null){return;}
		if(!listOfFigures.contains(f)){
			listOfFigures.add(f);
			f.addFigureListener(this); // register as an Observer of the subject Figure
			listOfObservers.forEach(drawModelListener -> drawModelListener.modelChanged(new DrawModelEvent(this, f, DrawModelEvent.Type.FIGURE_ADDED)));
		}
	}

	@Override
	public Iterable<Figure> getFigures() {
		return listOfFigures;
	}

	@Override
	public void removeFigure(Figure f) {
		if(f == null){ return;}
		f.removeFigureListener(this);
		listOfFigures.remove(f);
	}

	@Override
	public void addModelChangeListener(DrawModelListener listener) {
		/*
		basically register an observer for this subject (model)
		 */
		if(listener == null){ return; }
		if(!listOfObservers.contains(listener)){
			listOfObservers.add(listener);
		}
	}

	@Override
	public void removeModelChangeListener(DrawModelListener listener) {
		if(listener == null){return;}
		listOfObservers.remove(listener);
	}

	/** The draw command handler. Initialized here with a dummy implementation. */
	// TODO initialize with your implementation of the undo/redo-assignment.
	private DrawCommandHandler handler = new EmptyDrawCommandHandler();

	/**
	 * Retrieve the draw command handler in use.
	 * @return the draw command handler.
	 */
	@Override
	public DrawCommandHandler getDrawCommandHandler() {
		return handler;
	}

	@Override
	public void setFigureIndex(Figure f, int index) {
		if(f == null || !listOfFigures.contains(f)){
			throw new IllegalArgumentException();
		}
		if(index < 0 || index >= listOfFigures.size()){
			throw new IndexOutOfBoundsException();
		}

		listOfFigures.add(index, f);
		for(int i = 0; i < listOfFigures.size(); i++){
			if(listOfFigures.get(i) == f){
				if(index != i){
					listOfFigures.remove(i);
					i = Integer.MAX_VALUE-1;
				}
			}
		}
		listOfObservers.forEach(drawModelListener -> drawModelListener.modelChanged(new DrawModelEvent(this, f, DrawModelEvent.Type.DRAWING_CHANGED)));
	}

	@Override
	public void removeAllFigures() {
		listOfFigures.forEach(figure -> figure.removeFigureListener(this));
		listOfFigures.clear();
		listOfObservers.forEach(drawModelListener -> drawModelListener.modelChanged(new DrawModelEvent(this, null, DrawModelEvent.Type.DRAWING_CLEARED)));
	}

	@Override
	public void figureChanged(FigureEvent e) {
		if(e != null && e.getFigure() != null){
			for(int i = 0; i < listOfFigures.size(); i++){
				if(listOfFigures.get(i).equals(e.getFigure())){
					listOfFigures.set(i, e.getFigure());
					i = Integer.MAX_VALUE-1;
				}
			}
			listOfObservers.forEach(drawModelListener -> drawModelListener.modelChanged(new DrawModelEvent(this, null, DrawModelEvent.Type.DRAWING_CLEARED)));
		}
	}
}
