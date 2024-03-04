package adapter;

import java.awt.Color;
import java.awt.Graphics;

import geometry.Point;
import geometry.SurfaceShape;
import hexagon.Hexagon;

public class HexagonAdapter extends SurfaceShape {
	
	private Hexagon hexagon = new Hexagon(0, 0, 0);
	Color borderColor;
	Color innerColor;
	
	
	public HexagonAdapter(int x, int y, int r) {
		this.hexagon = new Hexagon(x, y, r);
		
	}
	
	public HexagonAdapter(int x, int y, int r, boolean selected, Color borderColor, Color innerColor) {
		this.hexagon = new Hexagon(x, y, r);
		this.setSelected(selected);
		hexagon.setAreaColor(innerColor);
		hexagon.setBorderColor(borderColor);
		
	}
	
	public Color getBorderColor() {
		return borderColor;
	}


	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}


	public Color getInnerColor() {
		return innerColor;
	}


	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}


	
	public Hexagon getHexagon() {
		return hexagon;
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
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Point p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
