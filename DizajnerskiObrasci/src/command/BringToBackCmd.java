package command;

import javax.swing.JOptionPane;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToBackCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	private int position = 0;

	public BringToBackCmd(Shape shape, DrawingModel model) {
		super();
		this.model = model;
		this.shape = shape;
	}
	
	@Override
	public void execute() {

		position = model.getSelected();
		
		shape = model.getShapeList().get(position);
		
		if(model.getShapeList().size()>0) {
			
			 if (position == 0) {
				 JOptionPane.showMessageDialog(null, "Shape is already at the bottom.");
					return;
		        }
			
			for(int i = position; i > 0; i--) {
				
				model.getShapeList().set(i, model.getShapeList().get(i-1));
				
			}
			model.getShapeList().set(0, shape);
			
			model.addToUndoList(this);
		}

	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		
		for(int i=0; i<position; i++) {
			
			model.getShapeList().set(i, model.getShapeList().get(i+1));
		}
		model.getShapeList().set(position, shape);

	}

	@Override
	public String getCmdName() {
		// TODO Auto-generated method stub
		return " BringToBackCmd";
	}

}
