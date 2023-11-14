package command;

import geometry.Shape;
import mvc.DrawingModel;

public class RemoveShapeCmd implements Command {


	private Shape shape;
	private DrawingModel model;
	
	public RemoveShapeCmd(Shape shape,DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}
	
	
	@Override
	public void execute() {
		model.removeSelected();
		model.addToUndoList(this);
	}

	@Override
	public void unexecute() {
		model.addShape(shape);
	}


	@Override
	public String getCmdName() {
		return " RemoveShapeCmd";
	}
}
