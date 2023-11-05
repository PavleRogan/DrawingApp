package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Circle;
import geometry.Point;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgCircle extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtXCoord;
	private JTextField txtYCoord;
	private Color color = null;
	private Color innerColor = null;
	private Circle circle = null;
	private JTextField txtRadius;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgCircle dialog = new DlgCircle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgCircle() {
		setBounds(100, 100, 284, 302);
		getContentPane().setLayout(new BorderLayout());
		setTitle("Circle");
		setResizable(false);
		setModal(true);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblXCoord = new JLabel("X Coordinate:");
		
		JLabel lblYCoord = new JLabel("Y Coordinate:");
		
		JLabel lblColor = new JLabel("Set Color:");
		
		JLabel lblInnerColor = new JLabel("Set Inner Color:");
		
		txtXCoord = new JTextField();
		txtXCoord.setColumns(10);
		
		txtYCoord = new JTextField();
		txtYCoord.setColumns(10);
		
		JLabel lblRadius = new JLabel("Radius:");
		
		txtRadius = new JTextField();
		txtRadius.setColumns(10);
		
		JButton btnColor = new JButton("Color");
		btnColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = JColorChooser.showDialog(null, "Choose color", color);
				if (color == null) color = Color.BLACK;
			}
		});
		
		JButton btnInnerColor = new JButton("Inner Color");
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				innerColor = JColorChooser.showDialog(null, "Choose inner color", innerColor);
				if (innerColor == null) innerColor = Color.WHITE;
			}
		});
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(lblXCoord, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblYCoord, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblColor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(lblInnerColor)
						.addComponent(lblRadius))
					.addGap(38)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
							.addComponent(txtRadius)
							.addComponent(txtYCoord)
							.addComponent(txtXCoord)
							.addComponent(btnColor, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
						.addComponent(btnInnerColor))
					.addGap(46))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblXCoord)
						.addComponent(txtXCoord, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblYCoord)
						.addComponent(txtYCoord, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRadius)
						.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(14)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblColor)
						.addComponent(btnColor))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInnerColor)
						.addComponent(btnInnerColor))
					.addContainerGap(42, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							int x = Integer.parseInt(txtXCoord.getText());
							int y = Integer.parseInt(txtYCoord.getText());
							int radius = Integer.parseInt(txtRadius.getText());

							if(x < 0 || y < 0 || radius < 1) {
								JOptionPane.showMessageDialog(null, "Wrong value!", "WARNING!", JOptionPane.WARNING_MESSAGE);
								return;
							}
							circle = new Circle(new Point(x, y), radius, false, color, innerColor);
							dispose();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Wrong data type!", "Warning!", JOptionPane.WARNING_MESSAGE);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public void setPoint(Point point) {
		txtXCoord.setText("" + point.getX());
		txtYCoord.setText("" + point.getY());
		
	}
	public void setCircle(Circle circle) {
		txtXCoord.setText("" + circle.getCenter().getX());
		txtYCoord.setText("" + circle.getCenter().getY());
		txtRadius.setText("" + circle.getRadius());
		color = circle.getColor();
		innerColor = circle.getInnerColor();
	}

	public void setColors(Color innerColor, Color color) {
		this.color =color;
		this.innerColor = innerColor;
		
	}

	public Circle getCircle() {
		return circle;
	}
}
