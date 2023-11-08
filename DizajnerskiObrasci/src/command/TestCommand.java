package command;

import java.awt.Color;

import geometry.Point;
import mvc.DrawingModel;

public class TestCommand {
	
	public static void main(String[] args) {
		DrawingModel model = new DrawingModel();
		Point p1 = new Point(10, 10, Color.RED);
		Point p2 = new Point(20, 20, Color.RED);

		AddShapeCmd addCmd1 = new AddShapeCmd(p1, model);
	
		AddShapeCmd addCmd2 = new AddShapeCmd(p2, model);

		addCmd1.execute();
		addCmd2.execute();
		
		System.out.println(model.getShapeList().size());
		
		//addCmd2.unexecute();
		
		//System.out.println(model.getShapeList().size());
		
		UpdatePointCmd updateCmd = new UpdatePointCmd(p1,p2);
		updateCmd.execute();
		
		System.out.println(p1);
		System.out.println(p2);
		
		

		
	}

}
