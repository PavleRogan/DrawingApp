package command;

import geometry.Donut;
import mvc.DrawingModel;

public class UpdateDonutCmd implements Command {

	private Donut oldState;
	private Donut newState;
	private Donut original = new Donut();
	private DrawingModel model;
	
	public UpdateDonutCmd(Donut oldState, Donut newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	public UpdateDonutCmd(Donut oldState, Donut newState, DrawingModel model) {
		this.oldState = oldState;
		this.newState = newState;
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
		return " UpdateDonutCmd";
	}

}
