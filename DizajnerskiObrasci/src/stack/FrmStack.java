package stack;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import geometry.Rectangle;
import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmStack extends JFrame {
	
	JList lstStack = new JList();
	private JPanel contentPane;
	private DefaultListModel<Rectangle> dlm= new DefaultListModel<Rectangle>();


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmStack frame = new FrmStack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FrmStack() {
		setTitle("IT5/2020 Pavle Rogan");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 441, 358);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel pnlCenter = new JPanel();
		
		JScrollPane scrlPnStack = new JScrollPane();
		
		lstStack.setModel(dlm);
		scrlPnStack.setViewportView(lstStack);
		
		JPanel pnlWest = new JPanel();
		
		JButton btnAddRect = new JButton("Add Rectangle");
		btnAddRect.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				
				DlgStack dlgStack = new DlgStack();
				dlgStack.setVisible(true);
				
					if (dlgStack.getRectangle()!=null)
					{
						dlm.add(0, dlgStack.getRectangle());
					}
			}
		});
		
		JButton btnRemoveRect = new JButton("Remove Rectangle");
		btnRemoveRect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dlm.isEmpty()==false)
				{
				DlgStack dlg = new DlgStack();
				dlg.setRectangle(dlm.getElementAt(0));
				dlg.setVisible(true);
				dlm.removeElementAt(0);
				}
				else {
				JOptionPane.showMessageDialog(null,"Stack is empty!","WARNING!!!", JOptionPane.WARNING_MESSAGE);
				return;
				}	
			}
		});
		GroupLayout gl_pnlWest = new GroupLayout(pnlWest);
		gl_pnlWest.setHorizontalGroup(
			gl_pnlWest.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlWest.createSequentialGroup()
					.addGroup(gl_pnlWest.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnAddRect, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnRemoveRect, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pnlWest.setVerticalGroup(
			gl_pnlWest.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlWest.createSequentialGroup()
					.addGap(52)
					.addComponent(btnAddRect)
					.addGap(18)
					.addComponent(btnRemoveRect)
					.addContainerGap(151, Short.MAX_VALUE))
		);
		pnlWest.setLayout(gl_pnlWest);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(pnlWest, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(pnlCenter, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(22, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(40)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(pnlWest, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(pnlCenter, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
		gl_pnlCenter.setHorizontalGroup(
			gl_pnlCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCenter.createSequentialGroup()
					.addComponent(scrlPnStack, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnlCenter.setVerticalGroup(
			gl_pnlCenter.createParallelGroup(Alignment.LEADING)
				.addComponent(scrlPnStack, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
		);
		pnlCenter.setLayout(gl_pnlCenter);
		contentPane.setLayout(gl_contentPane);
	}
}