package extras;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MessageWindowType {
	public enum MessageType {
		WARNING, ERROR, INFORMATION, PLAIN
	}

	public MessageWindowType(String message, int widthPosition, int heightPosition) {
			JFrame notificationFrame = new JFrame("Notification");
			notificationFrame.setLocationRelativeTo(null);
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
			int padding = 15;
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
	
	public static void showMessage(String message, MessageType messageType) {
		JOptionPane.showMessageDialog(null, message, messageType.toString(), getMessageType(messageType));
	}

	private static int getMessageType(MessageType messageType) {
		switch (messageType) {
		case WARNING:
			return JOptionPane.WARNING_MESSAGE;
		case ERROR:
			return JOptionPane.ERROR_MESSAGE;
		case INFORMATION:
			return JOptionPane.INFORMATION_MESSAGE;
		default:
			return JOptionPane.PLAIN_MESSAGE;
		}
	}

	/** CREATE CONFIRM DIALOGUE WINDOW **/
	public static int createConfirmDialogueWindow(String message, String titleMessage) {
		Image image = null;
		ImageIcon imageIcon = null;
		try {
			image = LoadResource.getImageFromResourceAsURL(AppConstants.QUESTION_MARK);
			image = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			imageIcon = new ImageIcon(image);
		} catch (Exception excpt) {
			excpt.printStackTrace();
		}
		int input = JOptionPane.showConfirmDialog(null, message, titleMessage, JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE, imageIcon);
		return input;
	}
}
