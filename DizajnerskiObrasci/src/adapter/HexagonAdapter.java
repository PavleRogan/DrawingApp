package adapter;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import geometry.Moveable;
import geometry.Point;
import geometry.SurfaceShape;
import hexagon.Hexagon;

public class HexagonAdapter extends SurfaceShape {
	
	
	Color color;
	Color innerColor;
	private Hexagon hexagon = new Hexagon(0, 0, 0);
	
	
	public HexagonAdapter() {
	}
	
	public HexagonAdapter(int x, int y, int r) {
		this.hexagon = new Hexagon(x, y, r);
		
	}
	
	public HexagonAdapter(int x, int y, int r, boolean selected, Color color, Color innerColor) {
		this.hexagon = new Hexagon(x, y, r);
		this.setSelected(selected);
		hexagon.setAreaColor(innerColor);
		hexagon.setBorderColor(color);
		
	}

	
	public Hexagon getHexagon() {
		return hexagon;
	}
	
	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	
	
	@Override
	public void moveBy(int byX, int byY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveTo(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof HexagonAdapter) {
			return hexagon.getR() - ((HexagonAdapter) o).getHexagon().getR();
		}
		return 0;
	}


	@Override
	public boolean contains(Point p) {
		return hexagon.doesContain(hexagon.getX(), hexagon.getY());
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}
	
	public HexagonAdapter clone(HexagonAdapter hexagon) {
		
		hexagon.getHexagon().setX(this.getHexagon().getX());
		hexagon.getHexagon().setY(this.getHexagon().getY());
		
		hexagon.setColor(this.getColor());
		hexagon.setInnerColor(this.getInnerColor());
		
		return hexagon;
		
	}
	

	@Override
	public void setInnerColor(Color innerColor) {
		hexagon.setAreaColor(innerColor);
		super.setInnerColor(innerColor);
	}

	@Override
	public Color getInnerColor() {
		return hexagon.getAreaColor();
	}

	@Override
	public void setColor(Color color) {
		hexagon.setBorderColor(color);
		super.setColor(color);
	}

	@Override
	public Color getColor() {
		return hexagon.getBorderColor();
	}
	
	
	@Override
	public boolean isSelected() {
		return hexagon.isSelected();
	}
	
	@Override
	public void setSelected(boolean selected) {
		hexagon.setSelected(selected);
		super.setSelected(selected);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		hexagon.paint(g);
		this.fill(g);
		
	}
	
	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HexagonAdapter) {
			Hexagon hexaFromObj = ((HexagonAdapter) obj).getHexagon();
			return hexagon.getX() == hexaFromObj.getX() && hexagon.getY() == hexaFromObj.getY()
					&& hexagon.getR() == hexaFromObj.getR();
		}
		return false;
	}
	
	public String toString() {
		return "X=" + hexagon.getX() +"Y="+ hexagon.getY() + "R=" + hexagon.getR();
	}
	
	

}
