package extras;

import java.awt.Image;

import javax.swing.*;

public class MessageWindow {
	public enum MessageType {
		WARNING, ERROR, INFORMATION, PLAIN
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
	
	/** CREATE CONFIRM DIALOGUE WINDOW  **/
	public static  int createConfirmDialogueWindow(String message, String titleMessage) {
		Image image = null;
		ImageIcon imageIcon = null;
		try {
			image = LoadResource.getImageFromResourceAsURL(AppConstants.QUESTION_MARK);
			image = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			imageIcon = new ImageIcon(image);
		} catch (Exception excpt) {
			excpt.printStackTrace();
		}
		int input = JOptionPane.showConfirmDialog(null, message,
				titleMessage, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, imageIcon);
		return input;
	}
	
}
