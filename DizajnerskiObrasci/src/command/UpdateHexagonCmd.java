package command;

import adapter.HexagonAdapter;
import mvc.DrawingModel;

public class UpdateHexagonCmd implements Command {

	private DrawingModel model;
	
	private HexagonAdapter originalHex = new HexagonAdapter();
	
	
	private HexagonAdapter oldHex;
	private HexagonAdapter newHex;
	
	public UpdateHexagonCmd() {
		
	}
	
	
	public UpdateHexagonCmd(HexagonAdapter oldHex, HexagonAdapter newHex) {
		this.oldHex = oldHex;
		this.newHex = newHex;
	}
	
	public UpdateHexagonCmd(HexagonAdapter oldHexagon, HexagonAdapter newHexagon, DrawingModel model) {
		this.oldHex = oldHexagon;
		this.newHex = newHexagon;
		this.model = model;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		originalHex = oldHex.clone(originalHex);
		oldHex = newHex.clone(oldHex);
		
		model.addToUndoList(this);
		if(model.getShapeList().size()>0) {
			int index = model.getShapeList().indexOf(oldHex);
			model.setShape(index, newHex);
		}

	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		if(model.getShapeList().size()>0) {
			int index = model.getShapeList().indexOf(newHex);
			oldHex = originalHex.clone(oldHex);
			model.setShape(index, oldHex);
		}

	}

	@Override
	public String getCmdName() {
		return " UpdateHexagonCmd";
	}

}
