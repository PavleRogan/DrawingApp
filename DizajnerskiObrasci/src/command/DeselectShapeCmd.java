package command;

import geometry.Shape;
import mvc.DrawingModel;

public class DeselectShapeCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	
	public DeselectShapeCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shape.setSelected(false);
		model.addToUndoList(this);

	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		shape.setSelected(true);

	}

	@Override
	public String getCmdName() {
		// TODO Auto-generated method stub
		return " DeselectShapeCmd";
	}

}
