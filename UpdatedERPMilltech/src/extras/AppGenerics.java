package extras;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class AppGenerics {

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

	/** RETRIEVE USER MACHINE NAME **/
	public static String getUserSystemName() throws Exception {
		return InetAddress.getLocalHost().getHostName();
	}

	/** CREATE DYNAMIC COMBOBOX MODEL **/
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

	/** CREATE MESSAGE NOTIFICATION ALERT **/
	public static void setMessageAlert(String message, int widthPosition, int heightPosition) {
		JFrame notificationFrame = new JFrame("Notification");
		notificationFrame.setUndecorated(true);
		notificationFrame.setBackground(new Color(0, 0, 0, 0));
		Color[] gradientColors = { new Color(204, 229, 255), // Light Sky Blue
				new Color(204, 204, 255), // Light Lavender
				new Color(255, 204, 204), // Light Salmon
				new Color(255, 223, 186), // Light Orange
				new Color(224, 206, 255), // Light Lilac
				new Color(173, 216, 230), // Light Blue
		};
		AnimatedPanel panel = new AnimatedPanel(gradientColors);
		panel.setLayout(new BorderLayout());
		notificationFrame.add(panel);

		JLabel label = new JLabel(message);
		label.setForeground(Color.WHITE);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 14));

		panel.add(label, BorderLayout.CENTER);
		FontMetrics fontMetrics = label.getFontMetrics(label.getFont());

		int textWidth = fontMetrics.stringWidth(message);
		int textHeight = fontMetrics.getHeight();
		int padding = 10;
		Dimension preferredSize = new Dimension(textWidth + padding, textHeight + padding);
		notificationFrame.setPreferredSize(preferredSize);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - preferredSize.width) / widthPosition;
		int y = (screenSize.height - preferredSize.height) / heightPosition;
		notificationFrame.setLocation(x, y);
		notificationFrame.pack();
		notificationFrame.setVisible(true);
		panel.animateAppearance();

		int delay = 3000;
		Timer closeTimer = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.animateDisappearance();
			}
		});
		closeTimer.setRepeats(false);
		closeTimer.start();
		Timer colorCycleTimer = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.cycleGradientColors();
			}
		});
		colorCycleTimer.start();
	}
}
