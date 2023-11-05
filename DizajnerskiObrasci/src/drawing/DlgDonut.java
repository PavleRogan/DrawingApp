package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Donut;
import geometry.Point;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgDonut extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtXCoord;
	private JTextField txtYCoord;
	private JTextField txtRadius;
	private JTextField txtInnerRadius;
	private Donut donut = null;
	private Color color = null;
	private Color innerColor = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgDonut dialog = new DlgDonut();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgDonut() {
		setBounds(100, 100, 274, 286);
		getContentPane().setLayout(new BorderLayout());
		setTitle("Donut");
		setModal(true);
		setResizable(false);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblXCoord = new JLabel("X Coordinate:");
		JLabel lblYCoord = new JLabel("Y Coordinate:");
		JLabel lblRadius = new JLabel("Radius:");
		JLabel lblInnerRadius = new JLabel("Inner Radius:");
		JLabel lblColor = new JLabel("Color:");
		JLabel lblInnerColor = new JLabel("Inner Color:");
		txtXCoord = new JTextField();
		txtXCoord.setColumns(10);
		txtYCoord = new JTextField();
		txtYCoord.setColumns(10);
		txtRadius = new JTextField();
		txtRadius.setColumns(10);
		txtInnerRadius = new JTextField();
		txtInnerRadius.setColumns(10);
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
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblXCoord)
						.addComponent(lblYCoord)
						.addComponent(lblRadius)
						.addComponent(lblInnerRadius)
						.addComponent(lblColor)
						.addComponent(lblInnerColor))
					.addGap(49)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnInnerColor, 0, 0, Short.MAX_VALUE)
						.addComponent(txtInnerRadius)
						.addComponent(txtRadius)
						.addComponent(txtYCoord)
						.addComponent(txtXCoord)
						.addComponent(btnColor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(57, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblXCoord)
						.addComponent(txtXCoord, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblYCoord)
						.addComponent(txtYCoord, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRadius)
						.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInnerRadius)
						.addComponent(txtInnerRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblColor)
						.addComponent(btnColor))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblInnerColor)
						.addComponent(btnInnerColor))
					.addContainerGap(33, Short.MAX_VALUE))
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
							int innerRadius = Integer.parseInt(txtInnerRadius.getText());

							if(x < 0 || y < 0 || radius < 1 || innerRadius < 1 || innerRadius >= radius) {
								JOptionPane.showMessageDialog(null,  "Wrong value!!", "WARNINGG!", JOptionPane.WARNING_MESSAGE);
								return;
							}
							donut = new Donut(new Point(x, y), radius, innerRadius,false, color, innerColor);
							dispose();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null,  "Wrong data type!", "WARNING!", JOptionPane.WARNING_MESSAGE);
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
	public void setDonut(Donut donut) {
		txtXCoord.setText("" + donut.getCenter().getX());
		txtYCoord.setText("" + donut.getCenter().getY());
		txtRadius.setText("" + donut.getRadius());
		txtInnerRadius.setText("" + donut.getInnerRadius());
		color = donut.getColor();
		innerColor = donut.getInnerColor();
	}

	public void setColors(Color color, Color innerColor) {
		this.color = color;
		this.innerColor = innerColor;
	}

	public Donut getDonut() {
	
		return donut;
	}

}
