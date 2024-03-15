package command;

import javax.swing.JOptionPane;

import geometry.Shape;
import mvc.DrawingModel;

public class ToFrontCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	
	public ToFrontCmd(DrawingModel model, Shape shape) {
		super();
		this.model = model;
		this.shape = shape;
	}
	
	public DrawingModel getModel() {
		return model;
	}

	public void setModel(DrawingModel model) {
		this.model = model;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		for(int i = 0; i < model.getShapeList().size(); i++){
			try {
				if(model.getShapeList().get(i).isSelected()) {
					
					shape = model.getShapeList().get(i);
					
					if( i+1 == model.getShapeList().size()) {
						
						JOptionPane.showMessageDialog(null, "Shape is already at the top.");
						
						return;
					}
					model.getShapeList().set(i, model.getShapeList().get(i+1));
					model.getShapeList().set(i+1, shape);
					model.addToUndoList(this);
					return;
				}
			} catch (Exception ex) {
				
				ex.printStackTrace();
				System.out.println(ex.getMessage());
			}
			
		}

	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		for(int i = 0; i < model.getShapeList().size(); i++){
			try {
				if(model.getShapeList().get(i).isSelected()) {
					shape = model.getShapeList().get(i);
					if( i-1 < 0 ) {
						return;
					}
					model.getShapeList().set( i, model.getShapeList().get(i-1));
					model.getShapeList().set( i-1, shape);
					return;
				}
			} catch (Exception ex){
				ex.printStackTrace();
				System.out.println(ex.getMessage());
			}
			
		}

	}

	@Override
	public String getCmdName() {
		// TODO Auto-generated method stub
		return " ToFrontCmd";
	}

}
