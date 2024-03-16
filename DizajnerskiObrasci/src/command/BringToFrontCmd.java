package command;

import javax.swing.JOptionPane;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToFrontCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	private int position = 0;
	
	public BringToFrontCmd( Shape shape, DrawingModel model) {
		super();
		this.model = model;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		if(model.getShapeList().size() > 0) {
			
			for(int i = 0; i < model.getShapeList().size(); i++){
				
				if(model.getShapeList().get(i).isSelected()) {
					
					position = i;
					shape = model.getShapeList().get(i);
					
					if (i == model.getShapeList().size() - 1) {
						JOptionPane.showMessageDialog(null, "Shape is already at the front!");
						return;
	                }
					
					for(int j = i + 1; j < model.getShapeList().size(); j++) {
						
						model.getShapeList().set( j - 1, model.getShapeList().get(j));
					}
				}
			}
			model.getShapeList().set(model.getShapeList().size() - 1, shape);
			model.addToUndoList(this);
		}

	}
	

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
			
			shape = model.getShapeList().get(model.getShapeList().size()-1);
			
			if(model.getShapeList().size() > 0) {
				
				for(int i = model.getShapeList().size() - 1; i > position; i--) {
					
					model.getShapeList().set(i, model.getShapeList().get( i - 1 ));
				}
				
				model.getShapeList().set(position, shape);
			}

	}

	@Override
	public String getCmdName() {
		// TODO Auto-generated method stub
		return " BringToFrontCmd";
	}

}
