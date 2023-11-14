package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape {
	
	private Point startPoint = new Point();
	private Point endPoint = new Point();
	
	public Line() {
		
	}
	
	public Line(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	public Line(Point startPoint, Point endPoint, boolean selected) {
		this(startPoint, endPoint);
		setSelected(selected);
	}
	
	public Line(Point startPoint, Point endPoint, boolean selected, Color color) {
		this(startPoint, endPoint, selected);
		this.setColor(color);
	}
	
	@Override
	public int compareTo(Object o) {
		if(o instanceof Line) {
			return (int) (this.lenght() - ((Line)o).lenght());
		}
		return 0;
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		this.startPoint.moveBy(byX, byY);
		this.endPoint.moveBy(byX, byY);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(this.startPoint.getX(), this.startPoint.getY(), this.endPoint.getX(), this.endPoint.getY());
		
		if(this.isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.startPoint.getX()-2, this.startPoint.getY()-2, 4, 4);
			g.drawRect(this.endPoint.getX()-2, this.endPoint.getY()-2, 4, 4);
		}
		
	}
	
	public boolean contains(int x, int y) {
		if((startPoint.distance(x, y) + endPoint.distance(x, y)) - lenght() <= 0.05) {
			return true;
		}else {
			return false;
		}
			
	}
	
	public double lenght() {
		return startPoint.distance(endPoint.getX(), endPoint.getY());
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Line) {
			Line pomocna = (Line) obj;
			if(this.startPoint.equals(pomocna.startPoint) && this.endPoint.equals(pomocna.endPoint))
				return true;
			else
				return false;
		}else
			return false;
	}
	
	public Point getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}
	public Point getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}
	
	public String toString() {
		return startPoint + "-->" + endPoint;
	}

	@Override
	public void moveTo(int x, int y) {
		
	}

	
	

}
