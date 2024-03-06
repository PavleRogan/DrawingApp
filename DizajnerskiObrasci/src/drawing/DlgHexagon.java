package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import adapter.HexagonAdapter;
import hexagon.Hexagon;
import geometry.Point;

public class DlgHexagon extends JDialog {
	
	private HexagonAdapter hexagonAdapter = null;
	private JLabel lblX;
	private JLabel lblY;
	private JLabel lnlNewRadius;
	private JButton btnColor;
	private Color  innerColor = null;
	private Color color = null;
	private JTextField txtXCoord;
	private JTextField txtYCoord;
	private JTextField txtNewRadius;
	

	public DlgHexagon() {
		setResizable(false);
		setTitle("Hexagon");
		setBounds(100, 100, 304, 302);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel pnlCenter = new JPanel();
			getContentPane().add(pnlCenter, BorderLayout.CENTER);
			{
				lblX = new JLabel("X coordinate", SwingConstants.CENTER);
			}
			{
				txtXCoord = new JTextField();
				txtXCoord.setColumns(10);
			}
			{
				lblY = new JLabel("Y coordinate");
				lblY.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				txtYCoord = new JTextField();
				txtYCoord.setColumns(10);
			}
			{
				lnlNewRadius = new JLabel("Radius");
				lnlNewRadius.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				txtNewRadius = new JTextField();
				txtNewRadius.setColumns(10);
			}
			{
				btnColor = new JButton("Inner color");
				btnColor.setHorizontalAlignment(SwingConstants.CENTER);
				btnColor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						innerColor = JColorChooser.showDialog(null, "Choose inner color", innerColor);
						if (innerColor == null)
							innerColor = Color.WHITE;
					}
				});
			}
			
			JButton btnEdgeColor = new JButton("Edge color");
			btnEdgeColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					color = JColorChooser.showDialog(null, "Choose color", color);
					if (color == null)
						color = Color.BLACK;
				}
			});
			GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
			gl_pnlCenter.setHorizontalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addComponent(lblX, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtXCoord, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addComponent(lblY, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtYCoord, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
									.addComponent(lnlNewRadius, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_pnlCenter.createSequentialGroup()
										.addGap(10)
										.addComponent(btnColor, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
									.addComponent(txtNewRadius, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_pnlCenter.createSequentialGroup()
										.addGap(10)
										.addComponent(btnEdgeColor, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)))))
						.addGap(0))
			);
			gl_pnlCenter.setVerticalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGap(1)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addComponent(lblX, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtXCoord, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addComponent(lblY, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtYCoord, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addComponent(lnlNewRadius, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtNewRadius, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnColor, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
							.addComponent(btnEdgeColor))
						.addGap(16))
			);
			pnlCenter.setLayout(gl_pnlCenter);
		}
		{
			JPanel pnlSouth = new JPanel();
			getContentPane().add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							int newXCoord = Integer.parseInt(txtXCoord.getText());
							int newYCoord = Integer.parseInt(txtYCoord.getText());
							int newRadius = Integer.parseInt(txtNewRadius.getText());

							if(newXCoord < 0 || newYCoord < 0 || newRadius < 1) {
								JOptionPane.showMessageDialog(null, "Wrong info!", "ERROR!", JOptionPane.ERROR_MESSAGE);
								return;
							}
							if (innerColor == null)
							{
								JOptionPane.showMessageDialog(null, "Choose a color!", "ERROR!", JOptionPane.ERROR_MESSAGE);
	                        return;
							}
							
							hexagonAdapter = new HexagonAdapter(newXCoord, newYCoord, newRadius, false, color, innerColor);
							dispose();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Wrong info!", "ERROR!", JOptionPane.ERROR_MESSAGE);
						}
						
					}
				});
				pnlSouth.add(btnOk);
			}
			{
				JButton btnExit = new JButton("Cancel");
				btnExit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				pnlSouth.add(btnExit);
			}
		}
	}
	
	
	public void setColors(Color edgeColor, Color innerColor) {
		this.color=edgeColor;
		this.innerColor = innerColor;
	}

	public void setHexagonAdapter(HexagonAdapter hexagonAdapter) {
		txtXCoord.setText("" + hexagonAdapter.getHexagon().getX());
		txtYCoord.setText("" + hexagonAdapter.getHexagon().getY());
		txtNewRadius.setText("" + hexagonAdapter.getHexagon().getR());
		
		innerColor = hexagonAdapter.getInnerColor();
		color= hexagonAdapter.getColor();
	}
	
	
	
	public HexagonAdapter getHexagonAdapter() {
		return hexagonAdapter;
	}

	
	public void setPoint(Point point) {
		txtXCoord.setText("" + point.getX());
		txtYCoord.setText("" + point.getY());
	}
	

	
	
}

