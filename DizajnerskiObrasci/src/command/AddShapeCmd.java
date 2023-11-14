package command;

import geometry.Shape;
import mvc.DrawingModel;

public class AddShapeCmd implements Command {

	private Shape shape;
	private DrawingModel model;
	
	public AddShapeCmd(Shape shape,DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}
	
	
	@Override
	public void execute() {
		model.addShape(shape);
		model.addToUndoList(this);
		
	}

	@Override
	public void unexecute() {
		model.removeShape(shape);
	}
	
	@Override
	public String getCmdName() {
		return " AddShapeCmd";
	}

}
