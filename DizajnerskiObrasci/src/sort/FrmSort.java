package sort;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.util.Iterator;
import geometry.Rectangle;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;



public class FrmSort extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmSort frame = new FrmSort();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private ArrayList <Rectangle> arrayList=new ArrayList<Rectangle>();
	private DefaultListModel<Rectangle> dlm= new DefaultListModel<Rectangle>();
	JList lstRect = new JList();

	/**
	 * Create the frame.
	 */
	public FrmSort() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Pavle Rogan IT5/2020");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlWest = new JPanel();
		contentPane.add(pnlWest, BorderLayout.WEST);
		
		JButton btnAdd = new JButton("Add Rectangle");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgSort dlg = new DlgSort();
				dlg.setVisible(true);
				
				if(dlg.getRectangle()!= null) {
					arrayList.add(dlg.getRectangle());
				}
				arrayList.sort(null);
				lstRect.setModel(sorted());
			}

		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GroupLayout gl_pnlWest = new GroupLayout(pnlWest);
		gl_pnlWest.setHorizontalGroup(
			gl_pnlWest.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlWest.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlWest.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnAdd))
					.addContainerGap())
		);
		gl_pnlWest.setVerticalGroup(
			gl_pnlWest.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlWest.createSequentialGroup()
					.addGap(67)
					.addComponent(btnAdd)
					.addGap(18)
					.addComponent(btnCancel)
					.addContainerGap(120, Short.MAX_VALUE))
		);
		pnlWest.setLayout(gl_pnlWest);
		
		JPanel pnlCenter = new JPanel();
		contentPane.add(pnlCenter, BorderLayout.CENTER);
		
		JScrollPane scrPnRect = new JScrollPane();
		GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
		
		
		scrPnRect.setViewportView(lstRect);
		pnlCenter.setLayout(gl_pnlCenter);
		
		
		gl_pnlCenter.setHorizontalGroup(
			gl_pnlCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCenter.createSequentialGroup()
					.addGap(18)
					.addComponent(scrPnRect, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(33, Short.MAX_VALUE))
		);
		gl_pnlCenter.setVerticalGroup(
			gl_pnlCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCenter.createSequentialGroup()
					.addGap(19)
					.addComponent(scrPnRect, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(31, Short.MAX_VALUE))
		);
		
		
	}
	private DefaultListModel<Rectangle> sorted()
	{
		Iterator<Rectangle> iterator = arrayList.iterator();
		DefaultListModel<Rectangle> dlm = new DefaultListModel<Rectangle>();
		while(iterator.hasNext()) {
			dlm.addElement(iterator.next());
		}	
		
		return dlm;
	}
}
