package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Drawing extends JPanel {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Drawng");
		frame.setSize(800, 600);
		Drawing drawing = new Drawing();
		frame.getContentPane().add(drawing);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public void paint(Graphics g) {
		
		Point p = new Point(50, 50, false, Color.BLUE);
		p.draw(g);
		
		Line l = new Line(new Point(70, 50), new Point(70, 140), true, Color.RED);
		l.draw(g);
		
		Circle c = new Circle(new Point(150, 200), 50, false, Color.WHITE, Color.RED);
		c.draw(g);
		
		Donut d = new Donut(new Point(300, 300), 100, 50, true, Color.CYAN, Color.BLACK);
		d.draw(g);
		 
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		shapes.add(p);
		shapes.add(l);
		shapes.add(d);
		shapes.add(2, c);
		HashMap<String, Shape> hm = new HashMap<String, Shape>();
		hm.put("point", p);
		System.out.println(hm.get("point"));
	
		
	}
	   
}



