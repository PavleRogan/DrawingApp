package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Point;
import geometry.Rectangle;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgRectangle extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtXCoord;
	private JTextField txtYCoord;
	private JTextField txtHeight;
	private JTextField txtWidth;
	private Rectangle rectangle = null;
	private Color color = null;
	private Color innerColor = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgRectangle dialog = new DlgRectangle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgRectangle() {
		setBounds(100, 100, 286, 329);
		setTitle("Rectangle");
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblXCoord = new JLabel("X Coordinate:");
		JLabel lblYCoord = new JLabel("Y Coordinate:");
		JLabel lblHeight = new JLabel("Height:");
		JLabel lblWidth = new JLabel("Width:");
		JLabel lblColor = new JLabel("Color:");
		JLabel lblInnerColor = new JLabel("Inner Color:");
		txtXCoord = new JTextField();
		txtXCoord.setColumns(10);
		txtYCoord = new JTextField();
		txtYCoord.setColumns(10);
		txtHeight = new JTextField();
		txtHeight.setColumns(10);
		txtWidth = new JTextField();
		txtWidth.setColumns(10);
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
						.addComponent(lblXCoord)
						.addComponent(lblYCoord)
						.addComponent(lblHeight)
						.addComponent(lblWidth)
						.addComponent(lblColor)
						.addComponent(lblInnerColor))
					.addGap(49)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnInnerColor, 0, 0, Short.MAX_VALUE)
						.addComponent(btnColor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(txtWidth)
						.addComponent(txtHeight)
						.addComponent(txtYCoord)
						.addComponent(txtXCoord))
					.addContainerGap(59, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblXCoord)
						.addComponent(txtXCoord, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblYCoord)
						.addComponent(txtYCoord, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHeight)
						.addComponent(txtHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWidth)
						.addComponent(txtWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblColor)
						.addComponent(btnColor))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInnerColor)
						.addComponent(btnInnerColor))
					.addContainerGap(58, Short.MAX_VALUE))
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
							int height = Integer.parseInt(txtHeight.getText());
							int width = Integer.parseInt(txtWidth.getText());

							if(x < 0 || y < 0 || height < 1 || width < 1) {
								JOptionPane.showMessageDialog(null, "Wrong value!", "WARNING!", JOptionPane.WARNING_MESSAGE);
								return;
							}
							rectangle = new Rectangle(new Point(x, y), width, height,false, color, innerColor);
							dispose();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Wrong data type!", "WARNING!", JOptionPane.WARNING_MESSAGE);
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
	
	public void setRectangle(Rectangle rect) {
		txtXCoord.setText("" + rect.getUpperLeftPoint().getX());
		txtYCoord.setText("" + rect.getUpperLeftPoint().getY());
		txtHeight.setText("" + rect.getHeight());
		txtWidth.setText("" + rect.getWidth());
		color = rect.getColor();
		innerColor = rect.getInnerColor();
	}

	public void setColors(Color color, Color innerColor) {
		this.color = color;
		this.innerColor = innerColor;
	}

	public Rectangle getRectangle() {

		return rectangle;
	}

}
