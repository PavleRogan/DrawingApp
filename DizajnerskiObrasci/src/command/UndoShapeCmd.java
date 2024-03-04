package command;

import mvc.DrawingModel;

public class UndoShapeCmd implements Command {

	private DrawingModel model;

	public UndoShapeCmd (DrawingModel model) {
		super();
		this.model = model;
	}
	
	@Override
	public void execute() {
		int size = model.getUndoList().size();
		if(size>0) {
			Command command = model.getUndoList().get(size - 1);
			model.deleteFromUndoList(command);
			model.addToRedoList(command);
			command.unexecute();
		}
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCmdName() {
		// TODO Auto-generated method stub
		return "UndoShapeCmd";
	}

}
