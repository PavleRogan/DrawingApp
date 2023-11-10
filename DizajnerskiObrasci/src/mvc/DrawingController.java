package mvc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import command.AddShapeCmd;
import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;

public class DrawingController {
	
	private final int operationDraw = 1;
	private final int operationMorD = 0;
	private int activeOperation = operationDraw;
	private Point startPoint;
	boolean waitingEndPoint = false;
	
	private DrawingFrame frame;
	private DrawingModel model;
	private AddShapeCmd addShapeCmd;
	
	
	public DrawingController(DrawingFrame frame, DrawingModel model) {
		super();
		this.frame = frame;
		this.model = model;
	}

	public void mouseClicked(MouseEvent e) {
		
		Point mouseClick = new Point(e.getX(), e.getY());
		model.deselect();
		
		if (activeOperation == operationMorD) {
			model.select(mouseClick);
			frame.repaint();
			return;
		}
		
		if (frame.tglbtnPoint.isSelected()) {
			DlgPoint dlgPoint = new DlgPoint();
			dlgPoint.setPoint(mouseClick);
			dlgPoint.setColor(frame.color);
			dlgPoint.setVisible(true);
			if(dlgPoint.getPoint() != null) {			
				addActionToUndo(dlgPoint.getPoint()); 
				
			}
			
			frame.repaint();
			return;
		}else if(frame.tglbtnLine.isSelected()) {
			if(waitingEndPoint) {
				Line line = new Line(startPoint,mouseClick);
				DlgLine dlgLine = new DlgLine();
				dlgLine.setLine(line);
				dlgLine.setColor(frame.color);
				dlgLine.setVisible(true);
				if(dlgLine.getLine()!= null) model.addShape(dlgLine.getLine());
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
			dlgCircle.setColors(frame.innerColor, frame.color);
			dlgCircle.setVisible(true);
			if(dlgCircle.getCircle() != null) model.addShape(dlgCircle.getCircle());
			frame.repaint();
			return;
		}else if(frame.tglbtnRectangle.isSelected()) {
			DlgRectangle dlgRectangle = new DlgRectangle();
			dlgRectangle.setPoint(mouseClick);
			dlgRectangle.setColors(frame.color, frame.innerColor);
			dlgRectangle.setVisible(true);
			if(dlgRectangle.getRectangle() != null) model.addShape(dlgRectangle.getRectangle());
			frame.repaint();
			return;
		}else if(frame.tglbtnDonut.isSelected()) {
			DlgDonut dlgDonut = new DlgDonut();
			dlgDonut.setPoint(mouseClick);
			dlgDonut.setColors(frame.color, frame.innerColor);
			dlgDonut.setVisible(true);
			if(dlgDonut.getDonut() != null) model.addShape(dlgDonut.getDonut());
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
		
	}
	
	public void setMorD() {
		activeOperation = operationMorD;
		frame.btnModify.setEnabled(true);
		frame.btnDelete.setEnabled(true);
		frame.tglbtnPoint.setEnabled(false);
		frame.tglbtnLine.setEnabled(false);
		frame.tglbtnRectangle.setEnabled(false);
		frame.tglbtnCircle.setEnabled(false);
		frame.tglbtnDonut.setEnabled(false);
	}
	
	public void actionPerformedModify() {
		int index = model.getSelected();
		if (index == -1) return;
		Shape shape = model.getShape(index);
		if (shape instanceof Point) {
			DlgPoint dlgPoint = new DlgPoint();
			dlgPoint.setPoint((Point)shape);
			dlgPoint.setVisible(true);
			if(dlgPoint.getPoint() != null) {
				model.setShape(index, dlgPoint.getPoint());
				frame.repaint();
			}
		}else if (shape instanceof Line) {
			DlgLine dlgLine = new DlgLine();
			dlgLine.setLine((Line)shape);
			dlgLine.setVisible(true);					
			if(dlgLine.getLine() != null) {
				model.setShape(index, dlgLine.getLine());
				frame.repaint();
			}
		}else if (shape instanceof Rectangle) {
			DlgRectangle dlgRectangle = new DlgRectangle();
			dlgRectangle.setRectangle((Rectangle)shape);
			dlgRectangle.setVisible(true);
			
			if(dlgRectangle.getRectangle() != null) {
				model.setShape(index, dlgRectangle.getRectangle());
				frame.repaint();
			}
		}else if (shape instanceof Donut) {
				DlgDonut dlgDonut = new DlgDonut();
				dlgDonut.setDonut((Donut)shape);
				dlgDonut.setVisible(true);
				
				if(dlgDonut.getDonut() != null) {
					model.setShape(index, dlgDonut.getDonut());
					frame.repaint();
				}
		}else if (shape instanceof Circle) {
			DlgCircle dlgCircle = new DlgCircle();
			dlgCircle.setCircle((Circle)shape);
			dlgCircle.setVisible(true);
			
			if(dlgCircle.getCircle() != null) {
				model.setShape(index, dlgCircle.getCircle());
				frame.repaint();
			    }
		    } 
		}
	
		public void actionPerformedDelete() {
			if (model.isEmpty()) return;
			if (JOptionPane.showConfirmDialog(null, "Do you really want to delete shape?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) model.removeSelected();
			frame.repaint();
		}

		public void actionPerformedUndo(ActionEvent e) {
			if(model.getUndoList().size()==0) {
				JOptionPane.showMessageDialog(frame, "There are no commands to undo.");
			} 
			else {

			}
		}

		public void actionPerformedRedo(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	


