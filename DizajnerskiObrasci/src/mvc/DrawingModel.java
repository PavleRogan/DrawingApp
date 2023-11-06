package mvc;

import java.util.ArrayList;

import geometry.Shape;


public class DrawingModel {

	private ArrayList <Shape> shapeList = new ArrayList<Shape>();
	
	public void add (Shape shape) {
		shapeList.add(shape);
	}
	
	public void remove(Shape shape) { 
		
		shapeList.remove(shape);
	}
	
	public Shape get(int index) {
		 return shapeList.get(index);
	}
	
	public ArrayList<Shape> getShapeList() {
		return shapeList;
	}
}
