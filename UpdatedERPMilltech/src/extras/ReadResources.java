package extras;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ReadResources {

	public static Image getImageFromResourceAsURL(String fileName) throws Exception {
		Image image = null;
		try {
			URL imageURL = ReadResources.class.getClassLoader().getResource(fileName);
			if (imageURL == null) {
				System.out.println("No image found!");
			}
			else {
				image = ImageIO.read(imageURL);	
			}
		} catch (IOException excpt) {
			excpt.printStackTrace();
		}
		return image;
	}

	/*
	 * public ImageIcon getIcon(String fileName) { java.net.URL imageURL =
	 * ReadResources.class.getClassLoader().getResource(fileName); if(imageURL ==
	 * null) { System.out.println("Null URL icon from file Name"); } ImageIcon icon
	 * = null; icon = new ImageIcon(imageURL); return icon; }
	 */
}