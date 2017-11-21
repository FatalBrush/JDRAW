/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */
package jdraw.std;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import jdraw.figures.*;
import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawToolFactory;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.grid.SimpleGrid;

/**
 * Standard implementation of interface DrawContext.
 * 
 * @see DrawView
 * @author Dominik Gruntz & Christoph Denzler
 * @version 2.6, 24.09.09
 */
public class StdContext extends AbstractContext {
	private final List<Figure> figuresToPaste = new ArrayList<>();

	/**
	 * Constructs a standard context with a default set of drawing tools.
	 * @param view the view that is displaying the actual drawing.
	 */
  public StdContext(DrawView view) {
		super(view, null);
	}
	
  /**
   * Constructs a standard context. The drawing tools available can be parameterized using <code>toolFactories</code>.
   * @param view the view that is displaying the actual drawing.
   * @param toolFactories a list of DrawToolFactories that are available to the user
   */
	public StdContext(DrawView view, List<DrawToolFactory> toolFactories) {
		super(view, toolFactories);
	}

	/**
	 * Creates and initializes the "Edit" menu.
	 * 
	 * @return the new "Edit" menu.
	 */
	@Override
	protected JMenu createEditMenu() {
		JMenu editMenu = new JMenu("Edit");
		final JMenuItem undo = new JMenuItem("Undo");
		undo.setAccelerator(KeyStroke.getKeyStroke("control Z"));
		editMenu.add(undo);
		undo.addActionListener(e -> {
				final DrawCommandHandler h = getModel().getDrawCommandHandler();
				if (h.undoPossible()) {
					h.undo();
				}
			}
		);

		final JMenuItem redo = new JMenuItem("Redo");
		redo.setAccelerator(KeyStroke.getKeyStroke("control Y"));
		editMenu.add(redo);
		redo.addActionListener(e -> {
				final DrawCommandHandler h = getModel().getDrawCommandHandler();
				if (h.redoPossible()) {
					h.redo();
				}
			}
		);
		editMenu.addSeparator();

		JMenuItem sa = new JMenuItem("SelectAll");
		sa.setAccelerator(KeyStroke.getKeyStroke("control A"));
		editMenu.add(sa);
		sa.addActionListener( e -> {
				for (Figure f : getModel().getFigures()) {
					getView().addToSelection(f);
				}
				getView().repaint();
			}
		);

		editMenu.addSeparator();
		JMenuItem cut = new JMenuItem("Cut");
		cut.addActionListener(e -> {
			figuresToPaste.clear();
			for(Figure f : getView().getSelection()){
				getModel().removeFigure(f);
				figuresToPaste.add(f);
			}
			showStatusText("cut");

		});
		editMenu.add(cut);
		JMenuItem copy = new JMenuItem("Copy");
		copy.addActionListener(e -> {
			figuresToPaste.clear();
			for(Figure f : getView().getSelection()){
				figuresToPaste.add(f);
			}
			showStatusText("copied");
		});
		editMenu.add(copy);
		JMenuItem paste = new JMenuItem("Paste");
		paste.addActionListener(e -> {
			if(figuresToPaste.isEmpty()){
				showStatusText("copy or cut first");
			} else {
				Figure tmp = null;
				for(Figure f : figuresToPaste){
					tmp = f.clone();
					tmp.move(10,10);
					getModel().addFigure(tmp);
				}
				showStatusText("pasted");
				figuresToPaste.clear();
			}
		});
		editMenu.add(paste);

		editMenu.addSeparator();
		JMenuItem clear = new JMenuItem("Clear");
		editMenu.add(clear);
		clear.addActionListener(e -> {
			getModel().removeAllFigures();
		});
		
		editMenu.addSeparator();
		JMenuItem group = new JMenuItem("Group");
		group.addActionListener(e -> {
			List<Figure> figuresToRemoveFromModel = getView().getSelection();
			getModel().addFigure(new GroupFigure(figuresToRemoveFromModel));
			figuresToRemoveFromModel.forEach(figure -> {
				getModel().removeFigure(figure);
			});
			getView().getSelection().clear();
		});
		editMenu.add(group);

		JMenuItem ungroup = new JMenuItem("Ungroup");
		ungroup.addActionListener(e -> {
			List<Figure> figuresToUngroup = getView().getSelection();
			Iterator<Figure> figuresToUngroupIT = figuresToUngroup.iterator();
			while(figuresToUngroupIT.hasNext()){
				Figure figure = figuresToUngroupIT.next();
				if(figure instanceof GroupFigure){
					Iterable<Figure> eachPartOfThisGroup = ((GroupFigure) figure).getFigureParts();
					Iterator<Figure> it = eachPartOfThisGroup.iterator();
					while(it.hasNext()){ // there might be more groups in here, ongrouping first layer though
						Figure singleFigure = it.next();
						getView().getModel().addFigure(singleFigure);
					}
					getModel().removeFigure(figure);
					figuresToUngroupIT.remove();
				}
			}
			getView().getSelection().clear();
		});
		editMenu.add(ungroup);

		editMenu.addSeparator();

		JMenu orderMenu = new JMenu("Order...");
		JMenuItem frontItem = new JMenuItem("Bring To Front");
		frontItem.addActionListener(e -> {
			bringToFront(getView().getModel(), getView().getSelection());
		});
		orderMenu.add(frontItem);
		JMenuItem backItem = new JMenuItem("Send To Back");
		backItem.addActionListener(e -> {
			sendToBack(getView().getModel(), getView().getSelection());
		});
		orderMenu.add(backItem);
		editMenu.add(orderMenu);

		JMenu grid = new JMenu("Grid...");
		JMenuItem resetGrid = new JMenuItem("Reset Grid");
		resetGrid.addActionListener(e -> getView().setConstrainer(null));
		JMenuItem simpleGrid = new JMenuItem("Simple Grid");
		simpleGrid.addActionListener(e -> getView().setConstrainer(new SimpleGrid(20,20)));
		grid.add(resetGrid);
		grid.add(simpleGrid);
		editMenu.add(grid);

		JMenuItem borderDecorator = new JMenuItem("Add border decoration");
		borderDecorator.addActionListener(e -> {
			List<Figure> selectedFigures = getView().getSelection();
			getView().clearSelection();
			for(Figure selectedFigure : selectedFigures){
				BorderDecorator decoratedFigure = new BorderDecorator(selectedFigure);
				getModel().removeFigure(selectedFigure);
				getModel().addFigure(decoratedFigure);
				getView().addToSelection(decoratedFigure);
			}
		});
		editMenu.add(borderDecorator);

		return editMenu;
	}

	/**
	 * Creates and initializes items in the file menu.
	 * 
	 * @return the new "File" menu.
	 */
	@Override
	protected JMenu createFileMenu() {
	  JMenu fileMenu = new JMenu("File");
		JMenuItem open = new JMenuItem("Open");
		fileMenu.add(open);
		open.setAccelerator(KeyStroke.getKeyStroke("control O"));
		open.addActionListener(e -> doOpen());

		JMenuItem save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke("control S"));
		fileMenu.add(save);
		save.addActionListener(e ->	doSave());

		JMenuItem exit = new JMenuItem("Exit");
		fileMenu.add(exit);
		exit.addActionListener(e -> System.exit(0));
		
		return fileMenu;
	}

	@Override
	protected void doRegisterDrawTools() {
		// TODO Add new figure tools here
		DrawTool rectangleTool = new RectTool(this);
		addTool(rectangleTool);
		DrawTool lineTool = new LineTool(this);
		addTool(lineTool);
		DrawTool ovalTool = new OvalTool(this);
		addTool(ovalTool);
	}

	/**
	 * Changes the order of figures and moves the figures in the selection
	 * to the front, i.e. moves them to the end of the list of figures.
	 * @param model model in which the order has to be changed
	 * @param selection selection which is moved to front
	 */
	public void bringToFront(DrawModel model, List<Figure> selection) {
		// the figures in the selection are ordered according to the order in
		// the model
		List<Figure> orderedSelection = new LinkedList<Figure>();
		int pos = 0;
		for (Figure f : model.getFigures()) {
			pos++;
			if (selection.contains(f)) {
				orderedSelection.add(0, f);
			}
		}
		for (Figure f : orderedSelection) {
			model.setFigureIndex(f, --pos);
		}
	}

	/**
	 * Changes the order of figures and moves the figures in the selection
	 * to the back, i.e. moves them to the front of the list of figures.
	 * @param model model in which the order has to be changed
	 * @param selection selection which is moved to the back
	 */
	public void sendToBack(DrawModel model, List<Figure> selection) {
		// the figures in the selection are ordered according to the order in
		// the model
		List<Figure> orderedSelection = new LinkedList<Figure>();
		for (Figure f : model.getFigures()) {
			if (selection.contains(f)) {
				orderedSelection.add(f);
			}
		}
		int pos = 0;
		for (Figure f : orderedSelection) {
			model.setFigureIndex(f, pos++);
		}
	}

	/**
	 * Handles the saving of a drawing to a file.
	 */
	private void doSave() {
		JFileChooser chooser = new JFileChooser(getClass().getResource("")
				.getFile());
		chooser.setDialogTitle("Save Graphic");
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		FileFilter filter = new FileFilter() {
			@Override
			public String getDescription() {
				return "JDraw Graphic (*.draw)";
			}

			@Override
			public boolean accept(File f) {
				return f.getName().endsWith(".draw");
			}
		};
		chooser.setFileFilter(filter);
		int res = chooser.showSaveDialog(this);

		if (res == JFileChooser.APPROVE_OPTION) {
			// save graphic
			File file = chooser.getSelectedFile();
			if (chooser.getFileFilter() == filter && !filter.accept(file)) {
				file = new File(chooser.getCurrentDirectory(), file.getName() + ".draw");
			}
			System.out.println("save current graphic to file " + file.getName());
		}
	}

	/**
	 * Handles the opening of a new drawing from a file.
	 */
	private void doOpen() {
		JFileChooser chooser = new JFileChooser(getClass().getResource("")
				.getFile());
		chooser.setDialogTitle("Open Graphic");
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public String getDescription() {
				return "JDraw Graphic (*.draw)";
			}

			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().endsWith(".draw");
			}
		});
		int res = chooser.showOpenDialog(this);

		if (res == JFileChooser.APPROVE_OPTION) {
			// read jdraw graphic
			System.out.println("read file "
					+ chooser.getSelectedFile().getName());
		}
	}

}
