package mvc;

import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;

import geometry.Shape;



public class DrawingView extends JPanel {
	private DrawingModel model;

	public void setModel(DrawingModel model) {
		this.model = model;
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Shape currentShape;
		if (model != null) {
			Iterator<Shape> it = model.getShapeList().iterator();

			while (it.hasNext()) {
				
				currentShape = it.next();
				
				currentShape.draw(g);
				
			}
		}
	}
}






