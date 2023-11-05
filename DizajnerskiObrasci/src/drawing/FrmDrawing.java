package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmDrawing extends JFrame {

	private JPanel contentPane;
	private final int operationDraw = 1;
	private final int operationMorD = 0;
	private int activeOperation = operationDraw;
	private JToggleButton tglbtnPoint = new JToggleButton("Point");
	private JToggleButton tglbtnLine = new JToggleButton("Line");
	private JToggleButton tglbtnCircle = new JToggleButton("Circle");
	private JToggleButton tglbtnRectangle = new JToggleButton("Rectangle");
	private JToggleButton tglbtnDonut = new JToggleButton("Donut");
	private JToggleButton tglbtnDraw = new JToggleButton("Draw");
	private JToggleButton tglbtnMorD = new JToggleButton("M/D");
	private ButtonGroup btnsOperation = new ButtonGroup();
	private ButtonGroup btnsShapes = new ButtonGroup();
	private PnlDrawing pnlDrawing = new PnlDrawing();
	private Color innerColor = Color.WHITE;
	private  Color color = Color.BLACK;
	boolean waitingEndPoint = false;
	private Point startPoint;
	private final JButton btnModify = new JButton("Modify");
	private final JButton btnDelete = new JButton("Delete");
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmDrawing frame = new FrmDrawing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmDrawing() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 1211, 718);
		JPanel contentPane= new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Pavle Rogan IT5/2020");
		setResizable(false);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		pnlDrawing.addMouseListener(pnlDrawingClickListener());
		contentPane.add(pnlDrawing, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		
		btnsOperation.add(tglbtnMorD);
		btnsOperation.add(tglbtnDraw);
		btnsShapes.add(tglbtnPoint);
		btnsShapes.add(tglbtnLine);
		btnsShapes.add(tglbtnCircle);
		btnsShapes.add(tglbtnRectangle);
		btnsShapes.add(tglbtnDonut);
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(tglbtnDonut, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(tglbtnRectangle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tglbtnCircle, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(tglbtnLine, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(tglbtnPoint, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(tglbtnDraw, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(tglbtnMorD, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(btnModify, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(tglbtnDraw)
					.addGap(9)
					.addComponent(tglbtnPoint)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnLine)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnCircle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnRectangle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnDonut)
					.addGap(35)
					.addComponent(tglbtnMorD)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnModify)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDelete)
					.addContainerGap(392, Short.MAX_VALUE))
		);
		tglbtnDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDraw();
			}
		});
		tglbtnMorD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMorD();
			}
		});
		btnModify.addActionListener(btnModifyClickListener());
		btnDelete.addActionListener(btnDeleteClickListener());
		tglbtnDraw.setSelected(true);
		
		setDraw();
		
		panel.setLayout(gl_panel);
	}

	private MouseListener pnlDrawingClickListener() {
		
		return new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				
				Point mouseClick = new Point(e.getX(), e.getY());
				pnlDrawing.deselect();
				
				if (activeOperation == operationMorD) {
					pnlDrawing.select(mouseClick);
					return;
				}
				
				if (tglbtnPoint.isSelected()) {
					DlgPoint dlgPoint = new DlgPoint();
					dlgPoint.setPoint(mouseClick);
					dlgPoint.setColor(color);
					dlgPoint.setVisible(true);
					if(dlgPoint.getPoint() != null) pnlDrawing.addShape(dlgPoint.getPoint());
					return;
				}else if(tglbtnLine.isSelected()) {
					if(waitingEndPoint) {
						Line line = new Line(startPoint,mouseClick);
						DlgLine dlgLine = new DlgLine();
						dlgLine.setLine(line);
						dlgLine.setColor(color);
						dlgLine.setVisible(true);
						if(dlgLine.getLine()!= null) pnlDrawing.addShape(dlgLine.getLine());
						waitingEndPoint=false;
						return;
					}
					startPoint = mouseClick;
					waitingEndPoint=true;
					return;
				}else if(tglbtnCircle.isSelected()) {
					DlgCircle dlgCircle = new DlgCircle();
					dlgCircle.setPoint(mouseClick);
					dlgCircle.setColors(innerColor, color);
					dlgCircle.setVisible(true);
					if(dlgCircle.getCircle() != null) pnlDrawing.addShape(dlgCircle.getCircle());
					return;
				}else if(tglbtnRectangle.isSelected()) {
					DlgRectangle dlgRectangle = new DlgRectangle();
					dlgRectangle.setPoint(mouseClick);
					dlgRectangle.setColors(color, innerColor);
					dlgRectangle.setVisible(true);
					if(dlgRectangle.getRectangle() != null) pnlDrawing.addShape(dlgRectangle.getRectangle());
					return;
				}else if(tglbtnDonut.isSelected()) {
					DlgDonut dlgDonut = new DlgDonut();
					dlgDonut.setPoint(mouseClick);
					dlgDonut.setColors(color, innerColor);
					dlgDonut.setVisible(true);
					if(dlgDonut.getDonut() != null) pnlDrawing.addShape(dlgDonut.getDonut());
					return;	
				}
				
		};
	};
	}
	
	private void setDraw() {
		activeOperation = operationDraw;
		pnlDrawing.deselect();
		btnModify.setEnabled(false);
		btnDelete.setEnabled(false);
		tglbtnPoint.setEnabled(true);
		tglbtnLine.setEnabled(true);
		tglbtnRectangle.setEnabled(true);
		tglbtnCircle.setEnabled(true);
		tglbtnDonut.setEnabled(true);	
		
	}
	private void setMorD() {
		activeOperation = operationMorD;
		btnModify.setEnabled(true);
		btnDelete.setEnabled(true);
		tglbtnPoint.setEnabled(false);
		tglbtnLine.setEnabled(false);
		tglbtnRectangle.setEnabled(false);
		tglbtnCircle.setEnabled(false);
		tglbtnDonut.setEnabled(false);
	}
	
	private ActionListener btnModifyClickListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = pnlDrawing.getSelected();
				if (index == -1) return;
				Shape shape = pnlDrawing.getShape(index);
				if (shape instanceof Point) {
					DlgPoint dlgPoint = new DlgPoint();
					dlgPoint.setPoint((Point)shape);
					dlgPoint.setVisible(true);
					if(dlgPoint.getPoint() != null) {
						pnlDrawing.setShape(index, dlgPoint.getPoint());
						pnlDrawing.repaint();
					}
				}else if (shape instanceof Line) {
					DlgLine dlgLine = new DlgLine();
					dlgLine.setLine((Line)shape);
					dlgLine.setVisible(true);					
					if(dlgLine.getLine() != null) {
						pnlDrawing.setShape(index, dlgLine.getLine());
						pnlDrawing.repaint();
					}
				}else if (shape instanceof Rectangle) {
					DlgRectangle dlgRectangle = new DlgRectangle();
					dlgRectangle.setRectangle((Rectangle)shape);
					dlgRectangle.setVisible(true);
					
					if(dlgRectangle.getRectangle() != null) {
						pnlDrawing.setShape(index, dlgRectangle.getRectangle());
						pnlDrawing.repaint();
					}
				}else if (shape instanceof Donut) {
						DlgDonut dlgDonut = new DlgDonut();
						dlgDonut.setDonut((Donut)shape);
						dlgDonut.setVisible(true);
						
						if(dlgDonut.getDonut() != null) {
							pnlDrawing.setShape(index, dlgDonut.getDonut());
							pnlDrawing.repaint();
						}
				}else if (shape instanceof Circle) {
					DlgCircle dlgCircle = new DlgCircle();
					dlgCircle.setCircle((Circle)shape);
					dlgCircle.setVisible(true);
					
					if(dlgCircle.getCircle() != null) {
						pnlDrawing.setShape(index, dlgCircle.getCircle());
						pnlDrawing.repaint();
					    }
				    } 
				}
				  
		    };
		}
			
	      private ActionListener btnDeleteClickListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pnlDrawing.isEmpty()) return;
				if (JOptionPane.showConfirmDialog(null, "Do you really want to delete shape?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) pnlDrawing.removeSelected();
			}
		  };
		}
}
