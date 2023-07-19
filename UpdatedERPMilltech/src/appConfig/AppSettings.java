package appConfig;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class AppSettings {

	public AppSettings(JFrame frame) {

		/** DEFAULT FRAME PROPERTIES */
		frame.getContentPane().setBackground(Color.white);
		frame.setBackground(Color.red);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setTitle("Test Title");
	}
}
