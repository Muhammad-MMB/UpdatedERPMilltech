package extras;

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
}
