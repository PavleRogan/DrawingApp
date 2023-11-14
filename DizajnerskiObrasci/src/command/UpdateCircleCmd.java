package command;

import geometry.Circle;
import mvc.DrawingModel;

public class UpdateCircleCmd implements Command {

	private Circle oldState;
	private Circle newState;
	private Circle original = new Circle();
	private DrawingModel model;

	public UpdateCircleCmd (Circle oldS, Circle newS) {
		this.oldState = oldS;
		this.newState = newS;
	}
	
	public UpdateCircleCmd (Circle oldS, Circle newS, DrawingModel model) {
		this.oldState = oldS;
		this.newState= newS;
		this.model = model;
	}
	
	
	@Override
	public void execute() {
		original = oldState.deepCopy(original);
		oldState = newState.deepCopy(oldState);
		model.addToUndoList(this);

	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		oldState = original.deepCopy(oldState);

	}

	@Override
	public String getCmdName() {
		// TODO Auto-generated method stub
		return " UpdateCircleCmd";
	}

}
