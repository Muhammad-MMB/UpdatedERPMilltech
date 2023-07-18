package appConfig;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import javax.swing.JFrame;
import com.formdev.flatlaf.FlatIntelliJLaf;
import dao.DataSource;
import java.awt.Toolkit;
import java.sql.SQLException;

public class AppStartUp {

	private JFrame applicationWindow;
	AppMenuSetup appMenuObject;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlatIntelliJLaf.setup();
					AppStartUp window = new AppStartUp();
					window.applicationWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AppStartUp() throws Exception {
		appMenuObject = new AppMenuSetup();
		setupGUI();
	}

	private void setupGUI() throws Exception {

		applicationWindow = new JFrame(" Milltech Martin Bright - ERP System");
		applicationWindow.setIconImage(
				Toolkit.getDefaultToolkit().getImage(AppStartUp.class.getResource("/resources/images/appIcon.png")));
		applicationWindow.getContentPane().setBackground(new Color(255, 255, 255));
		applicationWindow.setJMenuBar(appMenuObject.createAppMenu());
		applicationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		applicationWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
		applicationWindow.setLocationRelativeTo(null);
		applicationWindow.setSize(750, 550);
		applicationWindow.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.out.println("Close database connection");
				try {
					DataSource.closeConnection();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});

	}
}
