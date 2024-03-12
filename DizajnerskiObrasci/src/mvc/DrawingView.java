package mvc;

import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;

import geometry.Point;
import geometry.Shape;



public class DrawingView extends JPanel {
	
	private DrawingModel model;

	public void setModel(DrawingModel model) {
		this.model = model;
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (model != null) {
			Iterator<Shape> it = model.getShapeList().iterator();
			while (it.hasNext()) {
				it.next().draw(g);;				
			}
		}
	}


	public boolean isOnShape(Point mouseClick) {
		// TODO Auto-generated method stub
		
		for (int i = model.getShapeList().size()-1; i >= 0; i--) {
			if (model.getShape(i).contains(mouseClick.getX(), mouseClick.getY())) {
				return true;
			}
		}
		return false;
	}
}






