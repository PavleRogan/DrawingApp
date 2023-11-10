package mvc;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import drawing.PnlDrawing;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingFrame extends JFrame {
		
	private DrawingView view = new DrawingView();
	private DrawingController controller;
	
	
	private JPanel contentPane;
	
	public JToggleButton tglbtnPoint = new JToggleButton("Point");
	public JToggleButton tglbtnLine = new JToggleButton("Line");
	public JToggleButton tglbtnCircle = new JToggleButton("Circle");
	public JToggleButton tglbtnRectangle = new JToggleButton("Rectangle");
	public JToggleButton tglbtnDonut = new JToggleButton("Donut");
	public JToggleButton tglbtnDraw = new JToggleButton("Draw");
	public JToggleButton tglbtnMorD = new JToggleButton("M/D");
	private ButtonGroup btnsShapes = new ButtonGroup();
	private ButtonGroup btnsOperation = new ButtonGroup();
	public final JButton btnModify = new JButton("Modify");
	public final JButton btnDelete = new JButton("Delete");
	
	public JButton btnUndo = new JButton("UNDO");
	public  JButton btnRedo = new JButton("REDO");

	public Color innerColor = Color.WHITE;
	public  Color color = Color.BLACK;
	boolean waitingEndPoint = false;
	private Point startPoint;
		

	public DrawingFrame() {
		
		
		// from ooit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 1211, 718);
		JPanel contentPane= new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Pavle Rogan IT5/2020");
		setResizable(false);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//pnlDrawing.addMouseListener(pnlDrawingClickListener());
		
		contentPane.add(view, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		
		btnsOperation.add(tglbtnMorD);
		btnsOperation.add(tglbtnDraw);
		btnsShapes.add(tglbtnPoint);
		btnsShapes.add(tglbtnLine);
		btnsShapes.add(tglbtnCircle);
		btnsShapes.add(tglbtnRectangle);
		btnsShapes.add(tglbtnDonut);
		
		
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedUndo(e);
			}
		});
		
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedRedo(e);
			}
		});
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(tglbtnDonut, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(tglbtnRectangle, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(tglbtnCircle, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(tglbtnLine, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(tglbtnPoint, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(tglbtnDraw, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(tglbtnMorD, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(btnModify, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(btnUndo, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(btnRedo, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
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
					.addGap(52)
					.addComponent(btnUndo)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnRedo)
					.addContainerGap(298, Short.MAX_VALUE))
		);
		btnModify.setEnabled(false);
		btnDelete.setEnabled(false);
		tglbtnPoint.setEnabled(true);
		tglbtnLine.setEnabled(true);
		tglbtnRectangle.setEnabled(true);
		tglbtnCircle.setEnabled(true);
		tglbtnDonut.setEnabled(true);	
		
		tglbtnDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setDraw();
			}
		});
		tglbtnMorD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setMorD();
			}
		});
		btnModify.addActionListener(btnModifyClickListener());
		btnDelete.addActionListener(btnDeleteClickListener());
		tglbtnDraw.setSelected(true);
		
		
		
		panel.setLayout(gl_panel);
	
		
		
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.mouseClicked(e);
			}
		});
		
	
	}


	public DrawingView getView() {
		return view;
	}


	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	
	
	private ActionListener btnModifyClickListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedModify();
			}
		};
	}
			
	      private ActionListener btnDeleteClickListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedDelete();
			}
		  };
		}
}
