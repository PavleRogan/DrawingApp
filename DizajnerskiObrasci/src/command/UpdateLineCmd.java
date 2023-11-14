package command;

import geometry.Line;
import mvc.DrawingModel;

public class UpdateLineCmd implements Command {

	private Line oldState;
	private Line newState;
	private Line original = new Line();
	private DrawingModel model;
	
	
	public UpdateLineCmd(Line oldState, Line newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	public UpdateLineCmd(Line oldState, Line newState, DrawingModel model) {
		this.oldState = oldState;
		this.newState = newState;
		this.model = model;
	}
	
	
	@Override
	public void execute() {
		
		original.getStartPoint().setX(oldState.getStartPoint().getX());
		original.getStartPoint().setY(oldState.getStartPoint().getY());
		original.getEndPoint().setX(oldState.getEndPoint().getX());
		original.getEndPoint().setY(oldState.getEndPoint().getY());
		original.setColor(oldState.getColor());
		
		
		oldState.getStartPoint().setX(newState.getStartPoint().getX());
		oldState.getStartPoint().setY(newState.getStartPoint().getY());
		oldState.getEndPoint().setX(newState.getEndPoint().getX());
		oldState.getEndPoint().setY(newState.getEndPoint().getY());
		oldState.setColor(newState.getColor());
		
		model.addToUndoList(this);
		
	}

	@Override
	public void unexecute() {
		
		oldState.getStartPoint().setX(original.getStartPoint().getX());
		oldState.getStartPoint().setY(original.getStartPoint().getY());
		oldState.getEndPoint().setX(original.getEndPoint().getX());
		oldState.getEndPoint().setY(original.getEndPoint().getY());
		 oldState.setColor(newState.getColor());
	}

	@Override
	public String getCmdName() {
		// TODO Auto-generated method stub
		return " UpdateLineCmd";
	}

	public Line getOldState() {
		return oldState;
	}

	public void setOldState(Line oldState) {
		this.oldState = oldState;
	}
	

}
