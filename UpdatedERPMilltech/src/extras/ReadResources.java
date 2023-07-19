package extras;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

public class ReadResources {
	private static InputStream inStream;

	public static Image getFileFromResourceAsStream(String fileName) throws Exception {
		inStream = ReadResources.class.getResourceAsStream(fileName);
		BufferedInputStream bis = new BufferedInputStream(inStream);
		byte[] byBuf = new byte[100000];
		bis.read(byBuf, 0, 100000);
		Image img = Toolkit.getDefaultToolkit().createImage(byBuf);
		return img;
	}

	public void cleanUp() throws IOException {
		inStream.close();
	}
	
	public ImageIcon getIcon(String imagePath) {
		Image img = new ImageIcon(this.getClass().getResource(imagePath)).getImage();
		Image scaled = img.getScaledInstance(300, 100, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(scaled);
		return icon;
		}
	}