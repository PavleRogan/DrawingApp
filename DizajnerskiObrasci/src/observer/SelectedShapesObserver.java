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
			
		}
		else if(numOfSelectedShapes == 1){
			
		} 
		else if(numOfSelectedShapes > 1) {
			
		}
	}

}
