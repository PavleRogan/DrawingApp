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
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class DrawingFrame extends JFrame {
		
	private DrawingView view = new DrawingView();
	private DrawingController controller;
	
	
	private JPanel contentPane;
	
	public JToggleButton tglbtnPoint = new JToggleButton("Point");
	public JToggleButton tglbtnLine = new JToggleButton("Line");
	public JToggleButton tglbtnCircle = new JToggleButton("Circle");
	public JToggleButton tglbtnRectangle = new JToggleButton("Rectangle");
	public JToggleButton tglbtnDonut = new JToggleButton("Donut");
	public JToggleButton tglbtnHexagon = new JToggleButton("Hexagon");

	public JToggleButton tglbtnDraw = new JToggleButton("Draw");
	public JToggleButton tglbtnMorD = new JToggleButton("M/D");
	private ButtonGroup btnsShapes = new ButtonGroup();
	private ButtonGroup btnsOperation = new ButtonGroup();
	public final JButton btnModify = new JButton("Modify");
	private JButton btnBorderColor = new JButton("BORDER");
	private JButton btnInnerColor = new JButton("INNER");

	public final JButton btnDelete = new JButton("Delete");
	
	public JButton btnUndo = new JButton("UNDO");
	public  JButton btnRedo = new JButton("REDO");

	//public Color innerColor = Color.WHITE;
	//public  Color color = Color.BLACK;
	boolean waitingEndPoint = false;
	public Point startPoint;
	private final JPanel panelEast = new JPanel();
	private final JButton btnToFront = new JButton("To Front");
	private final JButton btnToBack = new JButton("To Back");
	
	
	public JButton getBtnColor() {
		return btnBorderColor;
	}


	public void setBtnColor(JButton btnColor) {
		this.btnBorderColor = btnColor;
	}


	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}


	public void setBtnInnerColor(JButton btnInnerColor) {
		this.btnInnerColor = btnInnerColor;
	}


	public JToggleButton getTglbtnDraw() {
		return tglbtnDraw;
	}


	public void setTglbtnDraw(JToggleButton tglbtnDraw) {
		this.tglbtnDraw = tglbtnDraw;
	}


	public JButton getBtnModify() {
		return btnModify;
	}


	public JButton getBtnDelete() {
		return btnDelete;
	}

		

	public DrawingFrame() {
		
		
		// from ooit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 900);
		JPanel contentPane= new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Pavle Rogan IT5/2020");
		setResizable(true);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		view.setBackground(Color.WHITE);
		
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
		btnsShapes.add(tglbtnHexagon);
		
		
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
		
		JLabel lblColors = new JLabel("Colors:");
		lblColors.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnInnerColor, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
						.addComponent(btnBorderColor, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
						.addComponent(lblColors, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
						.addComponent(btnRedo, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
						.addComponent(btnUndo, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
						.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
						.addComponent(btnModify, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
						.addComponent(tglbtnMorD, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
						.addComponent(tglbtnHexagon, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
						.addComponent(tglbtnDonut, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
						.addComponent(tglbtnRectangle, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
						.addComponent(tglbtnCircle, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
						.addComponent(tglbtnLine, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
						.addComponent(tglbtnPoint, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
						.addComponent(tglbtnDraw, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(23)
					.addComponent(tglbtnDraw)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnPoint)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnLine)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnCircle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnRectangle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnDonut)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnHexagon)
					.addGap(18)
					.addComponent(tglbtnMorD)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnModify)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDelete)
					.addGap(39)
					.addComponent(btnUndo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRedo)
					.addGap(18)
					.addComponent(lblColors)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnBorderColor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnInnerColor)
					.addContainerGap(364, Short.MAX_VALUE))
		);
		btnModify.setEnabled(false);
		btnDelete.setEnabled(false);
		tglbtnPoint.setEnabled(true);
		tglbtnLine.setEnabled(true);
		tglbtnRectangle.setEnabled(true);
		tglbtnCircle.setEnabled(true);
		tglbtnDonut.setEnabled(true);
		tglbtnHexagon.setEnabled(true);	
		btnToFront.setEnabled(false);
		btnToBack.setEnabled(false);


		
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
		
		

		btnBorderColor.setBackground(Color.BLACK);
		btnBorderColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.chooseBorderColor();
			}
		});
		
		btnInnerColor.setBackground(Color.WHITE);
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.chooseInnerColor();
			}
		});
		
		
		panel.setLayout(gl_panel);
		
		contentPane.add(panelEast, BorderLayout.EAST);
		btnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toFront();
			}
		});
		btnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toBack();
			}
		});
		
		JButton btnBringToFront = new JButton("Bring to front");
		btnBringToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnBringToBack = new JButton("Bring to back");
		btnBringToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		
		
		
		GroupLayout gl_panelEast = new GroupLayout(panelEast);
		gl_panelEast.setHorizontalGroup(
			gl_panelEast.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelEast.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelEast.createParallelGroup(Alignment.LEADING)
						.addComponent(btnToFront, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
						.addComponent(btnToBack, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
						.addComponent(btnBringToFront)
						.addComponent(btnBringToBack))
					.addContainerGap())
		);
		gl_panelEast.setVerticalGroup(
			gl_panelEast.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelEast.createSequentialGroup()
					.addGap(47)
					.addComponent(btnToFront)
					.addGap(18)
					.addComponent(btnToBack)
					.addGap(18)
					.addComponent(btnBringToFront)
					.addGap(18)
					.addComponent(btnBringToBack)
					.addGap(650))
		);
		panelEast.setLayout(gl_panelEast);
	
		
		
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.mouseClicked(e);
			}
		});
		
	
	}


	public JButton getBtnToFront() {
		return btnToFront;
	}


	public JButton getBtnToBack() {
		return btnToBack;
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
