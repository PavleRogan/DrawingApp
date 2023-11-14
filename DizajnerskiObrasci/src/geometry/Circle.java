package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends SurfaceShape {
	
	protected Point center = new Point();
	private int radius;
	
	public Circle() {
		
	}
	
	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}
	
	public Circle(Point center, int radius, boolean selected) {
		this(center, radius);
		setSelected(selected);
	}
	
	public Circle(Point center, int radius, boolean selected, Color color) {
		this(center, radius, selected);
		this.setColor(color);
	}
	
	public Circle(Point center, int radius, boolean selected, Color color, Color innerColor) {
		this(center, radius, selected, color);
		this.setInnerColor(innerColor);
	}
	
	@Override
	public int compareTo(Object o) {
		if(o instanceof Circle) {
			return this.radius - ((Circle)o).radius;
		}
		return 0;
	}

	
	@Override
	public void moveBy(int byX, int byY) {
		this.center.moveBy(byX, byY);
		
	}
	
	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillOval(this.center.getX()-this.radius+1, this.center.getY()-this.radius+1, this.radius*2-2, this.radius*2-2);
		
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawOval(this.center.getX()-this.radius, this.center.getY()-this.radius, this.radius*2, this.radius*2);
		this.fill(g);
		
		if(this.isSelected()) {
			g.setColor(Color.blue);
			g.drawRect(this.center.getX()-2, this.center.getY()-2, 4, 4);
			g.drawRect(this.center.getX()-this.radius-2, this.center.getY()-2, 4, 4);
			g.drawRect(this.center.getX()+this.radius-2, this.center.getY()-2, 4, 4);
			g.drawRect(this.center.getX()-2, this.center.getY()-this.radius-2, 4, 4);
			g.drawRect(this.center.getX()-2, this.center.getY()+this.radius-2, 4, 4);
			
		}
	}
	
	public boolean contains(int x, int y) {
		return this.center.distance(x, y) <= this.radius;
	}
	
	
	public boolean contains(Point p) {
		return this.center.distance(p.getX(), p.getY()) <= this.radius;
	}
	
	public double area() {
		return radius * radius * Math.PI;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Circle) {
			Circle pomocni = (Circle) obj;
			if(this.center.equals(pomocni.center) && this.radius == pomocni.radius) {
				return true;
			}else
				return false;
		}else
			return false;
	}
	
	

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) throws Exception {
		if(radius<0) {
			throw new Exception("Radius mora biti veci od nule!");
		}
		this.radius = radius;
		
	}

	
	public String toString() {
		return "Center=" + center + ", Radius=" + radius;
	}

	@Override
	public void moveTo(int x, int y) {
		this.center.moveTo(x, y);
		
	}
	

	public Circle deepCopy(Circle circle) {
		
		circle.getCenter().setX(this.getCenter().getX());
		circle.getCenter().setY(this.getCenter().getY());
		try {
			circle.setRadius(this.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		circle.setColor(this.getColor());
		circle.setInnerColor(this.getInnerColor());
		return circle;
	}

	
}
