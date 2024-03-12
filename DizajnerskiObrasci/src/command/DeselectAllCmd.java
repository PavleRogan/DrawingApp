package command;

import java.util.ArrayList;

import geometry.Shape;
import mvc.DrawingModel;

public class DeselectAllCmd implements Command {


	private DrawingModel model;
	private ArrayList<Shape> shapeList;
	
	public DeselectAllCmd( ArrayList<Shape> shapeList,DrawingModel model) {
		this.shapeList = shapeList;
		this.model = model;
	}
	
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shapeList.forEach(shape -> shape.setSelected(false));
		model.addToUndoList(this);

	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		shapeList.forEach(shape -> shape.setSelected(true));
		

	}

	@Override
	public String getCmdName() {
		// TODO Auto-generated method stub
		return " DeselectAllCmd";
	}

}
