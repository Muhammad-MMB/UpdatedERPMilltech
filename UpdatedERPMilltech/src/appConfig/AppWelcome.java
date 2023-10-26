package appConfig;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import dao.DataSource;
import extras.AppConstants;
import extras.AppGenerics;
import extras.LoadResource;

public class AppWelcome {

	private JFrame applicationWindow;
	AppMenuSetup appMenuObject;

	/** CONSTRUCTOR INVOKE **/
	public AppWelcome() throws Exception {
		appMenuObject = new AppMenuSetup();
		createAndShowGUI();
	}

	/** CREATE AND SETUP GUI **/
	private void createAndShowGUI() throws Exception {
		applicationWindow = new JFrame(" Milltech Martin Bright - ERP System");
		applicationWindow.setIconImage(LoadResource.getImageFromResourceAsURL("appIcon.png"));
		applicationWindow.getContentPane().setBackground(new Color(255, 255, 255));
		applicationWindow.setJMenuBar(appMenuObject.createAppMenu());
		applicationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		applicationWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
		applicationWindow.setLocationRelativeTo(null);
		applicationWindow.setSize(750, 550);
		applicationWindow.setVisible(true);
		AppConstants.USER_SYSTEM_NAME = AppGenerics.getUserSystemName();
		applicationWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					DataSource.closeConnection();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
	}
}
