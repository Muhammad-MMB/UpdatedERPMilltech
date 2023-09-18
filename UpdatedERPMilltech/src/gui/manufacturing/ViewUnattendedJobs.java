package gui.manufacturing;

import java.awt.Color;
import javax.swing.JFrame;

public class ViewUnattendedJobs extends JFrame  {

	private static final long serialVersionUID = 1L;
	
	public ViewUnattendedJobs(){
		
		/** JFRAME PROPERTIES **/
		this.setTitle("View All Unattended Jobs");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1332, 1000);
		this.setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
	}
}
