package command;

import geometry.Shape;
import mvc.DrawingModel;

public class RemoveShapeCmd implements Command {


	private Shape shape;
	private DrawingModel model;
//	private int index;
	
	public RemoveShapeCmd(Shape shape,DrawingModel model) {
		super();
		this.shape = shape;
		this.model = model;
		
		//index=model.getShapeList().indexOf(shape);
	}
	
	
	@Override
	public void execute() {
		model.removeShape(shape);
		model.addToUndoList(this);
	}

	@Override
	public void unexecute() {
		model.addShape(shape);
		//model.getShapeList().add(index,shape);
	}


	@Override
	public String getCmdName() {
		return " RemoveShapeCmd";
	}
}
