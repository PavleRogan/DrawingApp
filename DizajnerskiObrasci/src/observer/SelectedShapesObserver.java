package observer;

import mvc.DrawingFrame;
import mvc.DrawingModel;

public class SelectedShapesObserver implements Observer {
	
	private int numOfSelectedShapes;
	DrawingFrame frame;
	
	public SelectedShapesObserver(DrawingFrame frame) {
		this.frame = frame;
	}

	@Override
	public void update(int numOfSelectedShapes) {
		// TODO Auto-generated method stub
		
		this.numOfSelectedShapes = numOfSelectedShapes;
		changeBtnVisibility();

	}
	
	public void changeBtnVisibility() {
		if(numOfSelectedShapes==0) {
			
			//frame.getTglbtnDraw().setEnabled(true);
			frame.getBtnModify().setEnabled(false);
			frame.getBtnDelete().setEnabled(false);
			
		}
		else if(numOfSelectedShapes == 1){
			
			//frame.getTglbtnDraw().setEnabled(false);
			frame.getBtnModify().setEnabled(true);
			frame.getBtnDelete().setEnabled(true);
			
		} 
		else if(numOfSelectedShapes > 1) {

			//frame.getTglbtnDraw().setEnabled(false);
			frame.getBtnModify().setEnabled(false);
			frame.getBtnDelete().setEnabled(true);
			
		}
	}

}
