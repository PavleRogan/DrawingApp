package command;

import mvc.DrawingModel;

import java.util.ArrayList;

import geometry.IndexedShapeHelper;

public class RemoveMultipleCmd implements Command {
	
	private DrawingModel model;

	private ArrayList<IndexedShapeHelper> hlprShapes;
	
	private RemoveShapeCmd removeShapeCmd;
	
	 public RemoveMultipleCmd(ArrayList<IndexedShapeHelper> helperShapes, DrawingModel model) {
	    	
	    	this.hlprShapes = helperShapes;
	    	this.model = model;
	    }
	
	public ArrayList<IndexedShapeHelper> getHlprShapes() {
		return hlprShapes;
	}

	public void setHlprShapes(ArrayList<IndexedShapeHelper> hlprShapes) {
		this.hlprShapes = hlprShapes;
	}

	public DrawingModel getModel() {
		return model;
	}

	public void setModel(DrawingModel model) {
		this.model = model;
	}

	
    
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
		for (IndexedShapeHelper shape : hlprShapes) {
            model.removeShape(shape.getShape());
			//removeShapeCmd = new RemoveShapeCmd(shape.getShape(),model);
			//removeShapeCmd.execute();
        }
        //model.getUndoList().add(this);
		model.addToUndoList(this);

	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		
		for (int i = hlprShapes.size() - 1; i >= 0; i--) {
	        IndexedShapeHelper shape = hlprShapes.get(i);
	        model.addShapeToIndex(shape.getIndex(), shape.getShape());
	    }

	}

	@Override
	public String getCmdName() {
		// TODO Auto-generated method stub
		return " RemoveMultipleCmd";
	}

}
