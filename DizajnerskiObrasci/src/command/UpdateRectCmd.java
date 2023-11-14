package command;

import geometry.Rectangle;
import mvc.DrawingModel;

public class UpdateRectCmd implements Command {
	
	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle original = new Rectangle();
	private DrawingModel model;
	
	public UpdateRectCmd (Rectangle oldS, Rectangle newS) {
		super();
		this.oldState = oldS;
		this.newState = newS;
	}
	
	public UpdateRectCmd (Rectangle oldS, Rectangle newS, DrawingModel model) {
		super();
		this.oldState = oldS;
		this.newState = newS;
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
		return " UpdateRectCmd";
	}

}
