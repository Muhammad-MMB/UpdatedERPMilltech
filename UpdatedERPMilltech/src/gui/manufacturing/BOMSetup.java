package gui.manufacturing;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;

public class BOMSetup extends JFrame {
	private static final long serialVersionUID = 1L;

	public BOMSetup() {
		
		/** FRAME PROPERTIES **/
		this.setTitle("Setup Bill of Materials (BOM)");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 920, 1000);
		this.setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
		
		JPanel pnlTop = new JPanel();
		pnlTop.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlTop.setBounds(10, 11, 884, 161);
		getContentPane().add(pnlTop);
		pnlTop.setLayout(null);
		
		JLabel lblEndItem = new JLabel("End Item:");
		lblEndItem.setBounds(20, 38, 93, 14);
		pnlTop.add(lblEndItem);
		
		JComboBox cmbBoxEndItem = new JComboBox();
		cmbBoxEndItem.setBounds(123, 30, 238, 22);
		pnlTop.add(cmbBoxEndItem);
		
		JLabel lblMachineName = new JLabel("Machine Name:");
		lblMachineName.setBounds(496, 38, 93, 14);
		pnlTop.add(lblMachineName);
		
		JComboBox cmboBoxMachineName = new JComboBox();
		cmboBoxMachineName.setBounds(599, 30, 238, 22);
		pnlTop.add(cmboBoxMachineName);
		
		JButton btnAddTree = new JButton("Add into tree");
		ActionListener addTreeListener = new addTreeListener();
		btnAddTree.addActionListener(addTreeListener);
		btnAddTree.setBounds(680, 87, 157, 35);
		pnlTop.add(btnAddTree);
		
		JComboBox cmboBoxInFeedItem = new JComboBox();
		cmboBoxInFeedItem.setBounds(123, 87, 238, 22);
		pnlTop.add(cmboBoxInFeedItem);
		
		JLabel lblInFeedItem = new JLabel("In-Feed Item:");
		lblInFeedItem.setBounds(20, 95, 93, 14);
		pnlTop.add(lblInFeedItem);
		
		JPanel pnlMiddle = new JPanel();
		pnlMiddle.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlMiddle.setBounds(10, 183, 884, 252);
		getContentPane().add(pnlMiddle);
		pnlMiddle.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(57, 11, 161, 230);
		pnlMiddle.add(scrollPane);
		
		JTree BomTree = new JTree();
		scrollPane.setViewportView(BomTree);
	}
	
	class addTreeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					
				}
			});
		}
	}
}
