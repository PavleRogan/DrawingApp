package command;

import mvc.DrawingModel;

public class RedoShapeCmd implements Command {

	private DrawingModel model;

	public RedoShapeCmd (DrawingModel model) {
		super();
		this.model = model;
	}
	
	
	@Override
	public void execute() {
		int size = model.getRedoList().size();
		if(size>0) {
			Command cmd = model.getRedoList().get(size - 1);
			model.deleteFromRedoList(cmd);
			model.addToUndoList(cmd);
			cmd.execute();
		}
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCmdName() {
		// TODO Auto-generated method stub
		return "RedoShapeCmd";
	}

}
