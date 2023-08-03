package extras;

import java.awt.Component;
import java.net.InetAddress;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public  class Generics {

	/** SET PANEL STATUS & ITS COMPONENTS **/
	public static void setPanelStatusForComponents(JPanel panel, Boolean isEnabled) {
		panel.setEnabled(isEnabled);
		Component[] components = panel.getComponents();
		for (int i = 0; i < components.length; i++) {
			if (components[i].getClass().getName() == "javax.swing.JPanel") {
				setPanelStatusForComponents((JPanel) components[i], isEnabled);
			}
			if (components[i] instanceof JCheckBox) {
				JCheckBox chckbox = (JCheckBox) components[i];
				chckbox.setSelected(false);
			}
			components[i].setEnabled(isEnabled);
		}
	}
	
	public static String getUserSystemName() throws Exception{
		return InetAddress.getLocalHost().getHostName();
	}
}
