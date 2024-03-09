package geometry;

public class IndexedShapeHelper {
	
	private int index;
	private Shape shape;
	
	public IndexedShapeHelper(Shape shape,int index) {
		
		this.shape = shape;
		this.index = index;
		
	}
	
	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}


}
