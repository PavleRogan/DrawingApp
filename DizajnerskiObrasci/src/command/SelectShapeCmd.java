package command;

import geometry.Shape;
import mvc.DrawingModel;

public class SelectShapeCmd implements Command {

	
	private DrawingModel model;
	private Shape shape;
	
	public SelectShapeCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shape.setSelected(true);
		model.addToUndoList(this);

	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		shape.setSelected(false);
		

	}

	@Override
	public String getCmdName() {
		// TODO Auto-generated method stub
		return " SelectShapeCmd";
	}

}
