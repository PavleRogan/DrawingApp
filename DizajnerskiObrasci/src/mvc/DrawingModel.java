package mvc;

import java.util.ArrayList;

import command.Command;
import geometry.Point;
import geometry.Shape;


public class DrawingModel {

	private ArrayList <Shape> shapeList = new ArrayList<Shape>();
	private int i;
	
	private ArrayList<Command> undoList = new ArrayList<>();
	private ArrayList<Command> redoList = new ArrayList<>();
	
	
	public void removeShape(Shape shape) { 
		
		shapeList.remove(shape);
	}
	
	public Shape getShape(int index) {
		 return shapeList.get(index);
	}
	
	public ArrayList<Command> getUndoList() {
		return undoList;
	}

	public void setUndoList(ArrayList<Command> undoList) {
		this.undoList = undoList;
	}

	public ArrayList<Command> getRedoList() {
		return redoList;
	}

	public void setRedoList(ArrayList<Command> redoList) {
		this.redoList = redoList;
	}

	public void setShape(int index, Shape shape) {
		shapeList.set(index, shape);
	}
	
	public ArrayList<Shape> getShapeList() {
		return shapeList;
	}
	
	public void deselect() {
		shapeList.forEach(shape -> shape.setSelected(false));
	}
	
	public void select(Point point) {
		for (i = shapeList.size()-1; i >= 0; i--) {
			if (shapeList.get(i).contains(point.getX(), point.getY())) {
				shapeList.get(i).setSelected(true);
				return;
			}
		}
		
	}
	
	public void removeSelected() {
		shapeList.removeIf(shape -> shape.isSelected());
		
	}
	
	public boolean isEmpty() {
		return shapeList.isEmpty();
	}
	
	public int getSelected() {
		for (i = shapeList.size()-1; i >= 0; i--) {
			if (shapeList.get(i).isSelected()) {
				return i;
			}
		}
		return -1;
	}
	public void addShape(Shape shape) {
		shapeList.add(shape);
	}
	
	public void addToUndoList (Command cmd) {
		System.out.println("Add to undoList" + cmd.getCmdName());
		if(!undoList.contains(cmd)) {
			undoList.add(cmd);
		}
	}
	
	public void deleteFromUndoList (int index) {
		undoList.remove(index);
	}
	
	public void deleteFromUndoList (Command cmd) {
		System.out.println("Delete from undoList "+cmd.getCmdName());
		undoList.remove(cmd);
	}
	
	public void addToRedoList (Command cmd) {
		System.out.println("Add to redoList " + cmd.getCmdName());
		if(!redoList.contains(cmd)) {
			redoList.add(cmd);
		}
	}
	
	public void deleteFromRedoList (Command cmd) {
		System.out.println("delete from redo "+ cmd.getCmdName());
		redoList.remove(cmd);
	}

}
