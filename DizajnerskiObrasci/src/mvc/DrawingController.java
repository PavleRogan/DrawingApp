package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;

import geometry.Point;

public class DrawingController {
	
	private DrawingFrame frame;
	private DrawingModel model;
	
	public DrawingController(DrawingFrame frame, DrawingModel model) {
		super();
		this.frame = frame;
		this.model = model;
	}

	public void mouseClicked(MouseEvent e) {
		
		Point p = new Point(e.getX(),e.getY());
		model.add(p);
		frame.repaint();
		
	}
	
	

}
