package command;

import geometry.Point;
import mvc.DrawingModel;

public class UpdatePointCmd implements Command {

	private Point oldState;
	private Point newState;
	private Point original = new Point();
	private DrawingModel model;
	
	public UpdatePointCmd(Point oldState) {
		super();
		this.oldState = oldState;
	}
	
	public UpdatePointCmd(Point oldState, Point newState) {
		super();
		this.oldState = oldState;
		this.newState = newState;
		this.original = new Point();
	}
	
	public UpdatePointCmd(Point oldState, Point newState, DrawingModel model) {
		super();
		this.oldState = oldState;
		this.newState = newState;
		this.original = new Point();
		this.model = model;
	}
	
	
	
	@Override
	public void execute() {
		
		
		
		original.setX(oldState.getX());
		original.setY(oldState.getY());
		original.setColor(oldState.getColor());
		
		oldState.setX(newState.getX());
		oldState.setY(newState.getY());
		oldState.setColor(newState.getColor());
		
		model.addToUndoList(this);
		
		
	}

	@Override
	public void unexecute() {
		oldState.setX(original.getX());
		oldState.setY(original.getY());
		oldState.setColor(original.getColor());
		
	}


	@Override
	public String getCmdName() {
		return " UpdatePointCmd";
	}
}
