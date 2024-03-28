package mvc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import adapter.HexagonAdapter;
import command.AddShapeCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.DeselectAllCmd;
import command.DeselectShapeCmd;
import command.RedoShapeCmd;
import command.RemoveMultipleCmd;
import command.RemoveShapeCmd;
import command.SelectShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.UndoShapeCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectCmd;
import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgHexagon;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.IndexedShapeHelper;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import observer.SelectedShapes;
import observer.SelectedShapesObserver;
import strategy.SaveDrawing;
import strategy.SaveLog;
import strategy.SavingManager;

public class DrawingController {
	
	private final int operationDraw = 1;
	private final int operationMorD = 0;
	private int activeOperation = operationDraw;
	private Point startPoint;
	boolean waitingEndPoint = false;
	
	private DrawingFrame frame;
	private DrawingModel model;
	private AddShapeCmd addShapeCmd;
	private RemoveShapeCmd removeShapeCmd;
	private UpdatePointCmd updatePointCmd;
	private UpdateLineCmd updateLineCmd;
	private UpdateRectCmd updateRectCmd;
	private UpdateCircleCmd updateCircleCmd;
	private UpdateHexagonCmd updateHexagonCmd;
	private UpdateDonutCmd updateDonutCmd;
	private UndoShapeCmd undoShapeCmd;
	private RedoShapeCmd redoShapeCmd;
	private RemoveMultipleCmd removeMultipleCmd;
	private DeselectShapeCmd deselectShapeCmd;
	private SelectShapeCmd selectShapeCmd;
	private DeselectAllCmd deselectAllCmd;
	private Color borderColor = Color.BLACK;
	private Color innerColor = Color.WHITE;
	private SelectedShapes selectedShapes;
	private SelectedShapesObserver selectedShapesObserver;
	private ToFrontCmd toFrontCmd;
	private ToBackCmd toBackCmd;
	private BringToFrontCmd bringToFrontCmd;
	private BringToBackCmd bringToBackCmd;
	private ArrayList<String> listToLog;
	private String lstForLogging;
	private SaveLog saveLog;
	private SavingManager savingManager;
	private SaveDrawing saveDrawing;
	private ArrayList<String> loadedLog;
	private int logStep;
	private Shape selectedShape;
	
	public DrawingController(DrawingFrame frame, DrawingModel model) {
		super();
		this.frame = frame;
		this.model = model;
		this.selectedShapes = new SelectedShapes();
		this.selectedShapesObserver = new SelectedShapesObserver(frame);
		this.selectedShapes.addObserver(selectedShapesObserver);
		listToLog = new ArrayList<String>();
	}

	public void mouseClicked(MouseEvent e) {
		
		stopCurrentLog();
		Point mouseClick = new Point(e.getX(), e.getY());
		
		//model.deselect();
		
//		if (activeOperation == operationMorD) {
//			
//			model.select(mouseClick);
//			
//			this.selectedShapes.setNumOfSelectedShapes(model.getNumberOfSelectedShapes());
//			
//			frame.repaint();
//			return;
//		}
				
		if (activeOperation == operationMorD) {
			
			for (int i = model.getShapeList().size()-1; i >= 0; i--) {
				if (model.getShape(i).contains(mouseClick.getX(), mouseClick.getY()) && model.getShape(i).isSelected() == true  ) {
					deselectShapeCmd = new DeselectShapeCmd(model.getShape(i),model);
					deselectShapeCmd.execute();		
					log(model.getShape(i),"Deselect");	
					break;
				}
				if (model.getShape(i).contains(mouseClick.getX(), mouseClick.getY()) && model.getShape(i).isSelected() == false  ) {
					
					selectShapeCmd = new SelectShapeCmd(model.getShape(i),model);
					selectShapeCmd.execute();
					log(model.getShape(i),"Select");
					break;
					
				}
			} if(frame.getView().isOnShape(mouseClick) != true && this.selectedShapes.getNumOfSelectedShapes()>0){
	          
				ArrayList<Shape> selectedShapesList= new ArrayList<Shape>();
	            model.getShapeList().forEach(shape -> {
	            	if(shape.isSelected())
	            		selectedShapesList.add(shape);
	            	
	            });
	            
	            deselectAllCmd = new DeselectAllCmd(selectedShapesList,model);
	            deselectAllCmd.execute();
	            listToLog.add("Deselect: ALL");
        		frame.getLogList().setModel(toDlm());
				
		}
			this.selectedShapes.setNumOfSelectedShapes(model.getNumberOfSelectedShapes());
			
			frame.repaint();
			return;
					
					
}
		
		if (frame.tglbtnPoint.isSelected()) {
			DlgPoint dlgPoint = new DlgPoint();
			dlgPoint.setPoint(mouseClick);
			if(borderColor != null)
				dlgPoint.setColor(borderColor);
			
			dlgPoint.setVisible(true);
			if(dlgPoint.getPoint() != null) {			
				addActionToUndo(dlgPoint.getPoint()); 
				borderColor = dlgPoint.getColor();
				frame.getBtnColor().setBackground(borderColor);
				model.clearRedoList();
				model.clearRedoList();
				log(dlgPoint.getPoint(), "Draw");
				
			}
			
			frame.repaint();
			return;
		}else if(frame.tglbtnLine.isSelected()) {
			if(waitingEndPoint) {
				Line line = new Line(startPoint,mouseClick);
				DlgLine dlgLine = new DlgLine();
				dlgLine.setLine(line);
				if(borderColor != null)
					dlgLine.setColor(borderColor);
				//dlgLine.setColor(frame.color);
				dlgLine.setVisible(true);
				if(dlgLine.getLine()!= null) {
					addActionToUndo(dlgLine.getLine());
					borderColor = dlgLine.getColor();
					frame.getBtnColor().setBackground(borderColor);
					model.clearRedoList();
					log(dlgLine.getLine(), "Draw");
				}
				waitingEndPoint=false;
				frame.repaint();
				return;
			}
			startPoint = mouseClick;
			waitingEndPoint=true;
			return;
		}else if(frame.tglbtnCircle.isSelected()) {
			DlgCircle dlgCircle = new DlgCircle();
			dlgCircle.setPoint(mouseClick);
			dlgCircle.setColors(innerColor, borderColor);
			dlgCircle.setVisible(true);
			if(dlgCircle.getCircle() != null) {
				addActionToUndo(dlgCircle.getCircle());
				borderColor = dlgCircle.getColor();
				innerColor = dlgCircle.getInnerColor();
				frame.getBtnColor().setBackground(borderColor);
				frame.getBtnInnerColor().setBackground(innerColor);
				model.clearRedoList();
				log(dlgCircle.getCircle(), "Draw");
				}
			frame.repaint();
			return;
		}else if(frame.tglbtnRectangle.isSelected()) {
			DlgRectangle dlgRectangle = new DlgRectangle();
			dlgRectangle.setPoint(mouseClick);
			dlgRectangle.setColors(borderColor, innerColor);
			dlgRectangle.setVisible(true);
			if(dlgRectangle.getRectangle() != null) { 
				addActionToUndo(dlgRectangle.getRectangle());
				borderColor = dlgRectangle.getColor();
				innerColor = dlgRectangle.getInnerColor();
				frame.getBtnColor().setBackground(borderColor);
				frame.getBtnInnerColor().setBackground(innerColor);
				model.clearRedoList();	
				log(dlgRectangle.getRectangle(), "Draw");
			}
			frame.repaint();
			return;
		}else if(frame.tglbtnDonut.isSelected()) {
			DlgDonut dlgDonut = new DlgDonut();
			dlgDonut.setPoint(mouseClick);
			dlgDonut.setColors(borderColor,innerColor);
			dlgDonut.setVisible(true);
			if(dlgDonut.getDonut() != null) { 
				addActionToUndo(dlgDonut.getDonut());
				borderColor = dlgDonut.getColor();
				innerColor = dlgDonut.getInnerColor();
				frame.getBtnColor().setBackground(borderColor);
				frame.getBtnInnerColor().setBackground(innerColor);
				model.clearRedoList();
				log(dlgDonut.getDonut(), "Draw");
			}
			frame.repaint();
			return;	
			
		} else if (frame.tglbtnHexagon.isSelected()) {
			DlgHexagon dlgHexagon = new DlgHexagon();
			dlgHexagon.setPoint(mouseClick);
			dlgHexagon.setColors(borderColor,innerColor);
			dlgHexagon.setVisible(true);
			
			if(dlgHexagon.getHexagonAdapter() != null) {
				
				addActionToUndo(dlgHexagon.getHexagonAdapter());
				borderColor = dlgHexagon.getColor();
				innerColor = dlgHexagon.getInnerColor();
				frame.getBtnColor().setBackground(borderColor);
				frame.getBtnInnerColor().setBackground(innerColor);
				model.clearRedoList();
				log(dlgHexagon.getHexagonAdapter(), "Draw");

			}
			frame.repaint();
			return;
		}
		
		
	}
	
	
	private void addActionToUndo(Shape shape) {
		
		addShapeCmd = new AddShapeCmd(shape, model);
		addShapeCmd.execute();
		
	}

	public void setDraw() {
		activeOperation = operationDraw;
		model.deselect();
		frame.repaint();
		frame.btnModify.setEnabled(false);
		frame.btnDelete.setEnabled(false);
		frame.tglbtnPoint.setEnabled(true);
		frame.tglbtnLine.setEnabled(true);
		frame.tglbtnRectangle.setEnabled(true);
		frame.tglbtnCircle.setEnabled(true);
		frame.tglbtnDonut.setEnabled(true);	
		frame.tglbtnHexagon.setEnabled(true);	
		
	}
	
	public void setMorD() {
		activeOperation = operationMorD;
		frame.btnModify.setEnabled(false);
		frame.btnDelete.setEnabled(false);
		frame.tglbtnPoint.setEnabled(false);
		frame.tglbtnLine.setEnabled(false);
		frame.tglbtnRectangle.setEnabled(false);
		frame.tglbtnCircle.setEnabled(false);
		frame.tglbtnDonut.setEnabled(false);
		frame.tglbtnHexagon.setEnabled(false);
	}
	
	public void actionPerformedModify() {
		
		stopCurrentLog();
		int index = model.getSelected();
		if (index == -1) return;
		
		Shape shape = model.getShape(index);
		if (shape instanceof Point) {
			DlgPoint dlgPoint = new DlgPoint();
			dlgPoint.setPoint((Point)shape);
			dlgPoint.setVisible(true);
			if(dlgPoint.getPoint() != null) {
				
				Point newPoint = dlgPoint.getPoint();
				Point oldPoint = (Point) model.getShapeList().get(index);
				updatePointCmd = new UpdatePointCmd(oldPoint, newPoint, model);
				updatePointCmd.execute();
				borderColor = dlgPoint.getColor();
				frame.getBtnColor().setBackground(borderColor);
				model.clearRedoList();
				log(dlgPoint.getPoint(),"Modify");
				//model.setShape(index, dlgPoint.getPoint());
				frame.repaint();
			}
		}else if (shape instanceof Line) {
			DlgLine dlgLine = new DlgLine();
			dlgLine.setLine((Line)shape);
			dlgLine.setVisible(true);					
			if(dlgLine.getLine() != null) {
				
				Line oldState = (Line) model.getShapeList().get(model.getSelected());
				Line newState = dlgLine.getLine();
				updateLineCmd = new UpdateLineCmd(oldState, newState, model);
				updateLineCmd.execute();
				borderColor = dlgLine.getColor();
				frame.getBtnColor().setBackground(borderColor);
				//model.setShape(index, dlgLine.getLine());
				model.clearRedoList();
				log(dlgLine.getLine(),"Modify");
				frame.repaint();
			}
		}else if (shape instanceof Rectangle) {
			DlgRectangle dlgRectangle = new DlgRectangle();
			dlgRectangle.setRectangle((Rectangle)shape);
			dlgRectangle.setVisible(true);
			
			if(dlgRectangle.getRectangle() != null) {
				
				Rectangle oldS = (Rectangle) model.getShapeList().get(model.getSelected());
				Rectangle newS = dlgRectangle.getRectangle();
				updateRectCmd = new UpdateRectCmd(oldS, newS, model);
				updateRectCmd.execute();
				borderColor = dlgRectangle.getColor();
				innerColor = dlgRectangle.getInnerColor();
				frame.getBtnColor().setBackground(borderColor);
				frame.getBtnInnerColor().setBackground(innerColor);
				//model.setShape(index, dlgRectangle.getRectangle());
				model.clearRedoList();
				log(dlgRectangle.getRectangle(),"Modify");
				frame.repaint();
			}
		}else if (shape instanceof Donut) {
				DlgDonut dlgDonut = new DlgDonut();
				dlgDonut.setDonut((Donut)shape);
				dlgDonut.setVisible(true);
				
				if(dlgDonut.getDonut() != null) {
					
					Donut newD = dlgDonut.getDonut();
					Donut oldD = (Donut) model.getShapeList().get(model.getSelected());
					updateDonutCmd = new UpdateDonutCmd(oldD, newD, model);
					updateDonutCmd.execute();
					borderColor = dlgDonut.getColor();
					innerColor = dlgDonut.getInnerColor();
					frame.getBtnColor().setBackground(borderColor);
					frame.getBtnInnerColor().setBackground(innerColor);
					//model.setShape(index, dlgDonut.getDonut());
					model.clearRedoList();
					log(dlgDonut.getDonut(),"Modify");
					frame.repaint();
				}
		}else if (shape instanceof Circle) {
			DlgCircle dlgCircle = new DlgCircle();
			dlgCircle.setCircle((Circle)shape);
			dlgCircle.setVisible(true);
			
			if(dlgCircle.getCircle() != null) {
				
				Circle newC = dlgCircle.getCircle();
				Circle oldC = (Circle) model.getShapeList().get(model.getSelected());
				updateCircleCmd = new UpdateCircleCmd(oldC, newC, model);
				updateCircleCmd.execute();
				borderColor = dlgCircle.getColor();
				innerColor = dlgCircle.getInnerColor();
				frame.getBtnColor().setBackground(borderColor);
				frame.getBtnInnerColor().setBackground(innerColor);
				//model.setShape(index, dlgCircle.getCircle());
				model.clearRedoList();
				log(dlgCircle.getCircle(),"Modify");
				frame.repaint();
			    }
		    } else if (shape instanceof HexagonAdapter) {
		    	
		    	
				DlgHexagon dlgHexagon = new DlgHexagon();
				dlgHexagon.setHexagonAdapter((HexagonAdapter)shape);
				dlgHexagon.setVisible(true);
				
				if(dlgHexagon.getHexagonAdapter() != null) {
					HexagonAdapter newHexagon = dlgHexagon.getHexagonAdapter();
					HexagonAdapter oldHexagon = (HexagonAdapter) model.getShapeList().get( model.getSelected());
					updateHexagonCmd = new UpdateHexagonCmd(oldHexagon, newHexagon, model);
					updateHexagonCmd.execute();
					borderColor = dlgHexagon.getColor();
					innerColor = dlgHexagon.getInnerColor();
					frame.getBtnColor().setBackground(borderColor);
					frame.getBtnInnerColor().setBackground(innerColor);
					model.clearRedoList();
					log(dlgHexagon.getHexagonAdapter(),"Modify");
					frame.repaint();
				}
			} 
		}
	
		public void actionPerformedDelete() {
			stopCurrentLog();
			if (model.isEmpty()) return;
			int index = model.getSelected();
			
			if (index == -1) {
		        JOptionPane.showMessageDialog(frame, "Please select a shape to delete.");
		        return;
		    }
			
			if (JOptionPane.showConfirmDialog(null, "Do you really want to delete shape?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0){
				
				ArrayList<Shape> selectedShapesList= new ArrayList<Shape>();
	            model.getShapeList().forEach(shape -> {
	            	if(shape.isSelected())
	            		selectedShapesList.add(shape);});
	            logList(selectedShapesList);
				
				
				ArrayList<Integer> selectedIndexes = model.getSelectedIndexes();
		        ArrayList<IndexedShapeHelper> helperList = new ArrayList<>();
		        
		        for (int i = selectedIndexes.size() - 1; i >= 0; i--) {
		        	
		            Shape selectedShape = model.getShapeList().get(selectedIndexes.get(i));
		            IndexedShapeHelper helper = new IndexedShapeHelper(selectedShape, i);
		            helperList.add(helper);
		            
		        }
		        removeMultipleCmd = new RemoveMultipleCmd(helperList, model);
		        removeMultipleCmd.execute();        
		        this.selectedShapes.setNumOfSelectedShapes(model.getNumberOfSelectedShapes());
		        frame.repaint();
			}
			frame.repaint();
		}
	
	

		public void actionPerformedUndo() {
			stopCurrentLog();
			undo();
		}
		
		public void undo() {
			if(model.getUndoList().size()==0) {
				JOptionPane.showMessageDialog(frame, "There are no commands to undo.");
			} 
			else {
				
				undoShapeCmd = new UndoShapeCmd(model);
				undoShapeCmd.execute();
				
				frame.repaint();
				
				this.selectedShapes.setNumOfSelectedShapes(model.getNumberOfSelectedShapes());
				listToLog.add("Undo");
				frame.getLogList().setModel(toDlm());
					
			}
			
		}
		

		public void actionPerformedRedo() {
			stopCurrentLog();
			redo();
		}
		
		public void redo() {
			if(model.getRedoList().size()==0) {
				JOptionPane.showMessageDialog(frame, "There are no commands to redo.");
			} else {
				redoShapeCmd = new RedoShapeCmd(model);
				redoShapeCmd.execute();
				frame.repaint();
				
				this.selectedShapes.setNumOfSelectedShapes(model.getNumberOfSelectedShapes());
				listToLog.add("Redo");
				frame.getLogList().setModel(toDlm());
			}
		}
		
		public void chooseBorderColor() {
			borderColor = JColorChooser.showDialog(null, "Choose a color", borderColor);
			frame.getBtnColor().setBackground(borderColor);
			
		}
		public void chooseInnerColor() {
			innerColor = JColorChooser.showDialog(null, "Choose a color", innerColor);
			frame.getBtnInnerColor().setBackground(innerColor);
			
		}

		public void toFront() {
			// TODO Auto-generated method stub
			
			if(model.getShapeList().size()>0) {
				Shape selectedShape = model.getShapeList().get(model.getSelected());
				toFrontCmd = new ToFrontCmd(model, selectedShape);
				toFrontCmd.execute();
				log(selectedShape,"ToFront");
				frame.repaint();
			}
			
		}
		
		public void toFrontClick() {
			stopCurrentLog();
			toFront();
			model.clearRedoList();
			
		}

		public void toBack() {
			// TODO Auto-generated method stub
			
			if(model.getShapeList().size()>0) {
				Shape selectedShape = model.getShapeList().get(model.getSelected());
				toBackCmd = new ToBackCmd(model, selectedShape);
				toBackCmd.execute();
				log(selectedShape,"ToBack");
				frame.repaint();
			}
			
		}
		
		public void toBackClick() {
			stopCurrentLog();
			toBack();
			model.clearRedoList();
		}

		public void bringToFront() {
			
			if(model.getShapeList().size()>0) {
				
				Shape selected = model.getShapeList().get(model.getSelected());
				
				bringToFrontCmd = new BringToFrontCmd(selected, model);
				
				bringToFrontCmd.execute();
				
				log(selected,"BringToFront");
				
				frame.repaint();
			}
			
		}
		
		public void bringToFrontClick() {
			stopCurrentLog();
			bringToFront();
			
		}

		public void bringToBack() {
			
			
			if(model.getShapeList().size()>0) {
				
				Shape selectedShape = model.getShapeList().get(model.getSelected());
				
				bringToBackCmd = new BringToBackCmd(selectedShape, model);
				
				bringToBackCmd.execute();
				log(selectedShape,"BringToBack");
	
				frame.repaint();
			}
			
		}
		
		public void bringToBackClick() {
			stopCurrentLog();
			bringToBack();
			
		}
		
		private void log(Shape shape, String operation) {		
			
			if(shape instanceof Point) {
				
				listToLog.add(operation + ": Point= " +((Point)shape).toString());			
			}else if (shape instanceof Line) {
				
				listToLog.add(operation + ": Line= " +((Line)shape).toString());			
	
			}else if(shape instanceof Donut) {
				
				listToLog.add(operation + ": Donut= " +((Donut)shape).toString());			
	
			}else if(shape instanceof Circle) {
				
				listToLog.add(operation + ": Circle= " +((Circle)shape).toString());			
	
			}else if(shape instanceof Rectangle) {
				
				listToLog.add(operation + ": Rectangle= " +((Rectangle)shape).toString());			
	
			}else if(shape instanceof HexagonAdapter) {
				
				listToLog.add(operation + ": Hexagon= " +((HexagonAdapter)shape).toString());			
			}
		
		frame.getLogList().setModel(toDlm());
	}
		

		private void logList(ArrayList<Shape> selectedList) {
			 lstForLogging ="Delete";
			selectedList.forEach(shape -> {
				if(shape instanceof Point) {
					lstForLogging= lstForLogging + ": Point= " +((Point)shape).toString();			
				}else if (shape instanceof Line) {
					lstForLogging= lstForLogging + ": Line= " +((Line)shape).toString();			
		
				}else if(shape instanceof Donut) {
					lstForLogging= lstForLogging +  ": Donut= " +((Donut)shape).toString();			
		
				}else if(shape instanceof Circle) {
					lstForLogging= lstForLogging + ": Circle= " +((Circle)shape).toString();			
		
				}else if(shape instanceof Rectangle) {
					lstForLogging= lstForLogging + ": Rectangle= " +((Rectangle)shape).toString();			
		
				}else if(shape instanceof HexagonAdapter) {
					lstForLogging= lstForLogging + ": Hexagon= " +((HexagonAdapter)shape).toString();			
				}
			});
			listToLog.add(lstForLogging);
			frame.getLogList().setModel(toDlm());
		}
		
		private DefaultListModel<String> toDlm()
		{
			Iterator<String> iterator = listToLog.iterator();
			
			DefaultListModel<String> dlm = new DefaultListModel<String>();
			
			while(iterator.hasNext()) {
				
				dlm.addElement(iterator.next());
			}	
			return dlm;
		}

		public int getCountSelected() {
			int numberOfSelected= 0;
			
			for (int i = model.getShapeList().size()-1; i >= 0; i--) {
				
				if (model.getShape(i).isSelected()) {
					
					numberOfSelected++;
				}
			}	
			
			
			return numberOfSelected;
		}
		
		public void saveLog() {
			
			stopCurrentLog();
			
			saveLog = new SaveLog(frame);
			savingManager = new SavingManager(saveLog);	
			
			JFileChooser jFileChooser= new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			
			int dlg = jFileChooser.showOpenDialog(frame);	
			
			if (dlg == JFileChooser.APPROVE_OPTION){
				
	           String address= jFileChooser.getSelectedFile().getPath();
	            savingManager.save(address);
			}	else {
				
	           JOptionPane.showMessageDialog(null, "Saving has been canceled!", "Message", JOptionPane.INFORMATION_MESSAGE);
	        }
			
		}

		public void saveDrawing() {
			
			stopCurrentLog();
			
			saveDrawing = new SaveDrawing(model);
			savingManager = new SavingManager(saveDrawing);
			
			JFileChooser jFileChooser= new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			
			int dlg = jFileChooser.showOpenDialog(frame);
			
			if (dlg == JFileChooser.APPROVE_OPTION){
				
	            String address= jFileChooser.getSelectedFile().getPath();
	            savingManager.save(address);
			}	else {
				
	           JOptionPane.showMessageDialog(null, "Saving canceled!", "Message", JOptionPane.INFORMATION_MESSAGE);
	        }
			
		}

		public void loadLog() {
			
			stopCurrentLog();
			JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			int dlg = jFileChooser.showOpenDialog(frame);
			File file = jFileChooser.getSelectedFile();
			
			if(getCountSelected() > 0) {
				
				deselectAllCmd= new DeselectAllCmd((ArrayList<Shape>)model.getShapeList(), this.model);
				deselectAllCmd.execute();
				listToLog.add("Deselect: ALL");
				
				frame.getLogList().setModel(toDlm());
			}
			
			if (dlg == JFileChooser.APPROVE_OPTION) {
			
				try {
					
					loadedLog= new ArrayList<String>();
					loadedLog.addAll(Files.readAllLines(Paths.get(file.getPath())));
					logStep = 0;
					
					frame.getLogList().removeAll();
					
					frame.getBtnUndo().setEnabled(false);
					frame.getBtnRedo().setEnabled(false);
					frame.getBtnNextStep().setEnabled(true);
					
					model.clear();
					listToLog.clear();
					frame.getLogList().setModel(toDlm());

				}  catch (IOException ioe) {
					JOptionPane.showMessageDialog(null, "An error occured!", "Message",
							JOptionPane.INFORMATION_MESSAGE);

				}
				
				
				frame.getView().repaint();
				
			} else {
				JOptionPane.showMessageDialog(null, "Loading cancelled!", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
			
			
		}

		public void loadNext() {
			
			String logLine = loadedLog.get(logStep);
			
			String[] cmdArray = logLine.split(" ");		
			String operation = cmdArray[0];
			String loggedShape ="";
			
			 //System.out.println("Operation: " + operation);
			
			if(cmdArray.length>1) {
				
				loggedShape = cmdArray[1];
				// System.out.println(" logged shape" + loggedShape);

			}
			if(operation.equals("Draw:")) {
				
				setDraw();
				Shape shape;
				if(loggedShape.equals("Point=")) {
					
					shape = new Point(Integer.parseInt(cmdArray[2]),Integer.parseInt(cmdArray[3]),Color.decode(cmdArray[5]));
					addShapeCmd = new AddShapeCmd(shape, model);
					
				}else if(loggedShape.equals("Line=")) {
					
					shape = new Line(new Point(Integer.parseInt(cmdArray[2]),Integer.parseInt(cmdArray[3])),new Point(Integer.parseInt(cmdArray[7]),Integer.parseInt(cmdArray[8])), new Color(Integer.parseInt(cmdArray[12])));
					addShapeCmd = new AddShapeCmd(shape, model);
					
				}else if(loggedShape.equals("Rectangle=")) {
					
					shape = new Rectangle(new Point (Integer.parseInt(cmdArray[3]),Integer.parseInt(cmdArray[4])), Integer.parseInt(cmdArray[8]), Integer.parseInt(cmdArray[10]),new Color(Integer.parseInt(cmdArray[12])),new Color(Integer.parseInt(cmdArray[14])));
					addShapeCmd = new AddShapeCmd(shape, model);
					
				}else if(loggedShape.equals("Donut=")) {
					
					shape = new Donut(new Point (Integer.parseInt(cmdArray[3]),Integer.parseInt(cmdArray[4])), Integer.parseInt(cmdArray[8]),Integer.parseInt(cmdArray[14]),new Color(Integer.parseInt(cmdArray[10])),new Color(Integer.parseInt(cmdArray[12])));
					addShapeCmd = new AddShapeCmd(shape, model);
					
				}else if(loggedShape.equals("Circle=")) {
					
					Circle circle = new Circle(new Point (Integer.parseInt(cmdArray[3]),Integer.parseInt(cmdArray[4])), Integer.parseInt(cmdArray[8]),new Color(Integer.parseInt(cmdArray[10])),new Color(Integer.parseInt(cmdArray[12])));
					addShapeCmd = new AddShapeCmd(circle, model);
					
				}else if(loggedShape.equals("Hexagon=")) {
					
					shape = new HexagonAdapter(new Point (Integer.parseInt(cmdArray[3]),Integer.parseInt(cmdArray[4])), Integer.parseInt(cmdArray[8]),new Color(Integer.parseInt(cmdArray[10])),new Color(Integer.parseInt(cmdArray[12])));
					addShapeCmd = new AddShapeCmd(shape, model);
					
				}
				
				    addShapeCmd.execute();
				    model.addToUndoList(addShapeCmd);
				
				
				frame.getBtnUndo().setEnabled(true);	
				frame.getBtnRedo().setEnabled(true);
				
				
			}else if (operation.equals("Delete:")) {
				ArrayList<Shape> selectedShapesList= new ArrayList<Shape>();
				
	            model.getShapeList().forEach(shape -> {
	            	if(shape.isSelected())
	            		selectedShapesList.add(shape);});
	            selectedShapesList.forEach(shape -> {
						removeShapeCmd = new RemoveShapeCmd(shape,model);
						removeShapeCmd.execute();
						model.addToUndoList(redoShapeCmd);
						
						
					});
	            logList(selectedShapesList);
				
				if(model.isEmpty()) {
					
					setDraw();
				
				}
			
			}else if (operation.equals("Modify:")) {
				
				System.out.println("selected: " + selectedShape.toString());
				if(selectedShape != null) {
					
					Shape shape;
					
					if(loggedShape.equals("Point=")) {
						
						Point point = new Point(Integer.parseInt(cmdArray[2]),Integer.parseInt(cmdArray[3]),Color.decode(cmdArray[5]));
						updatePointCmd = new UpdatePointCmd((Point)model.getShape(model.getSelected()),point,model);
						
						updatePointCmd.execute();
						//model.addToUndoList(updatePointCmd);
					}else if(loggedShape.equals("Line=")) {
						
						Line line = new Line(new Point(Integer.parseInt(cmdArray[2]),Integer.parseInt(cmdArray[3])),new Point(Integer.parseInt(cmdArray[7]),Integer.parseInt(cmdArray[8])), new Color(Integer.parseInt(cmdArray[12])));
						updateLineCmd = new UpdateLineCmd((Line)model.getShape(model.getSelected()),line,model);
						
						updateLineCmd.execute();
						//model.addToUndoList(updateLineCmd);
						
					}else if(loggedShape.equals("Rectangle=")) {
						
						shape = new Rectangle(new Point (Integer.parseInt(cmdArray[3]),Integer.parseInt(cmdArray[4])), Integer.parseInt(cmdArray[8]), Integer.parseInt(cmdArray[10]),new Color(Integer.parseInt(cmdArray[12])),new Color(Integer.parseInt(cmdArray[14])));
						updateRectCmd = new UpdateRectCmd((Rectangle)model.getShape(model.getSelected()),(Rectangle)shape,model);
						updateRectCmd.execute();
						//model.addToUndoList(updateRectCmd);
					}else if(loggedShape.equals("Donut=")) {
						
						shape = new Donut(new Point (Integer.parseInt(cmdArray[3]),Integer.parseInt(cmdArray[4])), Integer.parseInt(cmdArray[8]),Integer.parseInt(cmdArray[14]),new Color(Integer.parseInt(cmdArray[10])),new Color(Integer.parseInt(cmdArray[12])));
						updateDonutCmd = new UpdateDonutCmd((Donut)model.getShape(model.getSelected()),(Donut)shape,model);
						
						updateDonutCmd.execute();
						//model.addToUndoList(updateDonutCmd);
					}else if(loggedShape.equals("Circle=")) {
						
						Circle circle = new Circle(new Point (Integer.parseInt(cmdArray[3]),Integer.parseInt(cmdArray[4])), Integer.parseInt(cmdArray[8]),new Color(Integer.parseInt(cmdArray[10])),new Color(Integer.parseInt(cmdArray[12])));
						updateCircleCmd = new UpdateCircleCmd((Circle)model.getShape(model.getSelected()),circle,model);
						updateCircleCmd.execute();
						//model.addToUndoList(updateCircleCmd);
					}else if(loggedShape.equals("Hexagon=")) {
						
						shape = new HexagonAdapter(new Point (Integer.parseInt(cmdArray[3]),Integer.parseInt(cmdArray[4])), Integer.parseInt(cmdArray[8]),new Color(Integer.parseInt(cmdArray[10])),new Color(Integer.parseInt(cmdArray[12])));
						updateHexagonCmd = new UpdateHexagonCmd((HexagonAdapter)model.getShape(model.getSelected()),(HexagonAdapter)shape,model);
						updateHexagonCmd.execute();
						//model.addToUndoList(updateHexagonCmd);
					}
					
						
				}
						
			}else if (operation.equals("Select:")) {
				
				setMorD();
				if(loggedShape.equals("Point=")) {
					selectedShape = new Point(Integer.parseInt(cmdArray[2]),Integer.parseInt(cmdArray[3]),Color.decode(cmdArray[5]));				
				}else if(loggedShape.equals("Line=")) {
					selectedShape = new Line(new Point(Integer.parseInt(cmdArray[2]),Integer.parseInt(cmdArray[3])),new Point(Integer.parseInt(cmdArray[7]),Integer.parseInt(cmdArray[8])), new Color(Integer.parseInt(cmdArray[12])));
				}else if(loggedShape.equals("Rectangle=")) {
					selectedShape = new Rectangle(new Point (Integer.parseInt(cmdArray[3]),Integer.parseInt(cmdArray[4])), Integer.parseInt(cmdArray[8]), Integer.parseInt(cmdArray[10]),new Color(Integer.parseInt(cmdArray[12])),new Color(Integer.parseInt(cmdArray[14])));
				}else if(loggedShape.equals("Donut=")) {
					selectedShape = new Donut(new Point (Integer.parseInt(cmdArray[3]),Integer.parseInt(cmdArray[4])), Integer.parseInt(cmdArray[8]),Integer.parseInt(cmdArray[14]),new Color(Integer.parseInt(cmdArray[10])),new Color(Integer.parseInt(cmdArray[12])));
				}else if(loggedShape.equals("Circle=")) {
					
					selectedShape = new Circle(new Point (Integer.parseInt(cmdArray[3]),Integer.parseInt(cmdArray[4])), Integer.parseInt(cmdArray[8]),new Color(Integer.parseInt(cmdArray[10])),new Color(Integer.parseInt(cmdArray[12])));
				}else if(loggedShape.equals("Hexagon=")) {
					selectedShape = new HexagonAdapter(new Point (Integer.parseInt(cmdArray[3]),Integer.parseInt(cmdArray[4])), Integer.parseInt(cmdArray[8]),new Color(Integer.parseInt(cmdArray[10])),new Color(Integer.parseInt(cmdArray[12])));
				}
				
				model.getShapeList().forEach(shape -> {
					if(shape.toString().equals(selectedShape.toString())) {
						selectShapeCmd = new SelectShapeCmd(shape,model);
						selectShapeCmd.execute();
						//model.addToUndoList(selectShapeCmd);
						selectedShape = shape;
					}
				});;

				//frame.getBtnUndo().setEnabled(true); I GORE
			
				
			}else if (operation.equals("Deselect:") && selectedShape != null) {
				if(loggedShape.equals("ALL")) {
					 ArrayList<Shape> selectedShapes= new ArrayList<Shape>();
			            model.getShapeList().forEach(shape -> {
			            	if(shape.isSelected())
			            		selectedShapes.add(shape);});
			            deselectAllCmd = new DeselectAllCmd(selectedShapes,model);
			            deselectAllCmd.execute();
			            //model.addToUndoList(deselectAllCmd);
			            
			           
				}else {
					if(loggedShape.equals("Point=")) {
						selectedShape = new Point(Integer.parseInt(cmdArray[2]),Integer.parseInt(cmdArray[3]),Color.decode(cmdArray[5]));					
					}else if(loggedShape.equals("Line=")) {
						selectedShape = new Line(new Point(Integer.parseInt(cmdArray[2]),Integer.parseInt(cmdArray[3])),new Point(Integer.parseInt(cmdArray[7]),Integer.parseInt(cmdArray[8])), new Color(Integer.parseInt(cmdArray[12])));
					}else if(loggedShape.equals("Rectangle=")) {
						selectedShape = new Rectangle(new Point (Integer.parseInt(cmdArray[3]),Integer.parseInt(cmdArray[4])), Integer.parseInt(cmdArray[8]), Integer.parseInt(cmdArray[10]),new Color(Integer.parseInt(cmdArray[12])),new Color(Integer.parseInt(cmdArray[14])));
					}else if(loggedShape.equals("Donut=")) {
						selectedShape = new Donut(new Point (Integer.parseInt(cmdArray[3]),Integer.parseInt(cmdArray[4])), Integer.parseInt(cmdArray[8]),Integer.parseInt(cmdArray[14]),new Color(Integer.parseInt(cmdArray[10])),new Color(Integer.parseInt(cmdArray[12])));
					}else if(loggedShape.equals("Circle=")) {
						selectedShape = new Circle(new Point (Integer.parseInt(cmdArray[3]),Integer.parseInt(cmdArray[4])), Integer.parseInt(cmdArray[8]),new Color(Integer.parseInt(cmdArray[10])),new Color(Integer.parseInt(cmdArray[12])));
					}else if(loggedShape.equals("Hexagon=")) {
						selectedShape = new HexagonAdapter(new Point (Integer.parseInt(cmdArray[3]),Integer.parseInt(cmdArray[4])), Integer.parseInt(cmdArray[8]),new Color(Integer.parseInt(cmdArray[10])),new Color(Integer.parseInt(cmdArray[12])));
					}
					
					model.getShapeList().forEach(shape -> {
						if(shape.toString().equals(selectedShape.toString())) {
							deselectShapeCmd = new DeselectShapeCmd(shape,model);
							deselectShapeCmd.execute();
							//model.addToUndoList(deselectAllCmd);
							
							
						}
					});;
				}
				
			}else if (operation.equals("Undo")) {
				undo();
				
			}else if (operation.equals("Redo")) {
				redo();
			}else if (operation.equals("ToFront:")) {
				toFront();
				
			}else if (operation.equals("ToBack:")) {
				toBack();
			}else if (operation.equals("BringToFront:")) {
				bringToFront();
				
			}else if (operation.equals("BringToBack:")) {
				bringToBack();
			}
			
			if(model.getShapeList().size() > 0) {
				
				//frame.getBtnSelect().setEnabled(true);
			}
			this.selectedShapes.setNumOfSelectedShapes(model.getNumberOfSelectedShapes());
			
			if((operation.equals("Draw:")) || (operation.equals("Modify:")) || (operation.equals("Select:")) || (operation.equals("Deselect:")) )
			listToLog.add(logLine);
			
			frame.getLogList().setModel(toDlm());
			
			frame.repaint();

			logStep++;
				
			if(logStep == loadedLog.size()) {
					
				//stopCurrentLog();
					frame.getBtnNextStep().setEnabled(false);
					
				}
			
		}
		
		
		public void stopCurrentLog(){
			if(loadedLog != null && frame.getBtnNextStep().isEnabled())
			if(loadedLog.size()>0) {
				if (JOptionPane.showConfirmDialog(null, "You have not finished loading", "Stop logging", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
				loadedLog.clear();
				logStep=0;
				frame.getBtnNextStep().setEnabled(false);		
				}
			}
		}

		public void loadDrawing() {
			
			stopCurrentLog();
			JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			int dlg = jFileChooser.showOpenDialog(frame);
			File file = jFileChooser.getSelectedFile();
			
			if (dlg == JFileChooser.APPROVE_OPTION) {
				
				try {
					FileInputStream fileInputStream = new FileInputStream(file);
					
					ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
					model.clear();
					listToLog.clear();
					frame.getLogList().setModel(toDlm()); 
					
					setDraw();
					ArrayList<Shape> tempList = (ArrayList<Shape>) objectInputStream.readObject();
					tempList.forEach(shape-> shape.setSelected(false));
					
					tempList.forEach(shape->{
						addShapeCmd= new AddShapeCmd(shape, model);
						addShapeCmd.execute();
						//
						log(shape,"Draw");
					});
					objectInputStream.close();
					fileInputStream.close();
					if(model.size()>0) {
						
						//frame.getBtnMorD().setEnabled(true);
						frame.getBtnUndo().setEnabled(true);
						frame.getBtnRedo().setEnabled(false);
					}
				} catch (ClassNotFoundException e) {
					JOptionPane.showMessageDialog(null, "File not found!", "Message", JOptionPane.INFORMATION_MESSAGE);

				} catch (IOException ioe) {
					JOptionPane.showMessageDialog(null, "An error occured!", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				}
				
				
				this.selectedShapes.setNumOfSelectedShapes(model.getNumberOfSelectedShapes());
				frame.getView().repaint();
				
			} else {
				JOptionPane.showMessageDialog(null, "Loading cancelled!", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
			
		}

		
	}
	


