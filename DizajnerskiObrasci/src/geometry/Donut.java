package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.Graphics2D;

public class Donut extends Circle {
	
	private int innerRadius;
	
	public Donut() {
		
	}
	
	public Donut(Point center, int radius, int innerRadius) {
		super (center, radius);
		this.innerRadius = innerRadius;
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		setSelected(selected);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) {
		this(center, radius, innerRadius, selected);
		this.setColor(color);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) {
		this(center, radius, innerRadius, selected, color);
		this.setInnerColor(innerColor);
	}
	
	@Override
	public int compareTo(Object o) {
		if(o instanceof Donut) {
			return (int) (this.area() - ((Donut)o).area());
		}
		return 0;
	}
	
	@Override
	public void fill(Graphics g) {
	//  g.setColor(getInnerColor());
	//	super.fill(g);
	//	g.setColor(Color.WHITE);
	//	g.fillOval(this.getCenter().getX()-this.innerRadius+1, this.getCenter().getY()-this.innerRadius+1, this.innerRadius*2-2, this.innerRadius*2-2);
		
		Ellipse2D inner = new Ellipse2D.Float(center.getX() - innerRadius, center.getY() - innerRadius, 2 * innerRadius, 2 * innerRadius);
		Ellipse2D outer = new Ellipse2D.Float(center.getX() - getRadius(), center.getY() - getRadius(), 2 * getRadius(), 2 * getRadius());
		
		Area outer2 = new Area(outer);
		Area inner2 = new Area(inner);
		
		outer2.subtract(inner2);
		g.setColor(getInnerColor());
		((Graphics2D)g).fill (outer2);
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		g.drawOval(this.getCenter().getX()-this.innerRadius, this.getCenter().getY()-this.innerRadius, this.innerRadius*2, this.innerRadius*2);
		this.fill(g);
	}
	
	public double area() {
		return super.area() - innerRadius*innerRadius*Math.PI;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Donut) {
			Donut pomocna = (Donut) obj;
			if(this.getCenter().equals(pomocna.getCenter()) && this.getRadius() == pomocna.getRadius() 
					&& this.innerRadius == pomocna.innerRadius) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	
	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return super.contains(x, y) && dFromCenter > innerRadius;
	}
	
	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(), p.getY()) && dFromCenter > innerRadius;
	}
	
	public int getInnerRadius() {
		return innerRadius;
	}
	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}
	
	public String toString() {
		return super.toString() + "Inner Radius=" + innerRadius;
	}
	
	public Donut deepCopy(Donut donut) {
		donut.getCenter().setX(this.getCenter().getX());
		donut.getCenter().setY(this.getCenter().getY());
		try {
			donut.setRadius(this.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		donut.setInnerRadius(this.getInnerRadius());
		donut.setInnerColor(this.getInnerColor());
		donut.setColor(this.getColor());
		return donut;
	}

}
