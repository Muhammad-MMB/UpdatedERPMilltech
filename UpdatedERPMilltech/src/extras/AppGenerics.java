package extras;

import java.awt.Component;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class AppGenerics {

	/**  SET PANEL STATUS & ITS COMPONENTS  **/
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
	
	/**  RETRIEVE USER MACHINE NAME  **/
	public static String getUserSystemName() throws Exception{
		return InetAddress.getLocalHost().getHostName();
	}
	
	/**  CREATE DYNAMIC COMBOBOX MODEL  **/
	public static JComboBox<String> createComboBox(ArrayList<String> items) {
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		for (String item : items) {
			comboBoxModel.addElement(item);
		}
		JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
		AutoCompleteDecorator.decorate(comboBox);
		comboBox.setEditable(true);
		return comboBox;
	}
}
