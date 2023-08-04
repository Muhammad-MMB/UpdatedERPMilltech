package gui.manufacturing;

import java.awt.Color;
import javax.swing.JFrame;


public class BOMSetup extends JFrame {
	private static final long serialVersionUID = 1L;

	public BOMSetup() {
		/** FRAME PROPERTIES **/
		this.setTitle("Setup Bill of Materials (BOM)");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1600, 1000);
		this.setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
	}
}
