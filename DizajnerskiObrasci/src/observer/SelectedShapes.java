package observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SelectedShapes implements Observable {
	
	private int numOfSelectedShapes;

	public int getNumOfSelectedShapes() {
		return numOfSelectedShapes;
	}

	public void setNumOfSelectedShapes(int numOfSelectedShapes) {
		this.numOfSelectedShapes = numOfSelectedShapes;
		notifyObservers();
	}

	private List<Observer> observers = new ArrayList<>();
	

	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
		
		observers.add(observer);

	}

	@Override
	public void removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		observers.remove(observer);

	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		Iterator<Observer> it = observers.iterator();
		while (it.hasNext())
			it.next().update(numOfSelectedShapes);

	}

}
