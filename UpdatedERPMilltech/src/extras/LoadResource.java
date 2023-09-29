package extras;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class LoadResource {

	/** RETURN IMAGE AS AN IMAGE **/
	public static Image getImageFromResourceAsURL(String fileName) throws Exception {
		Image image = null;
		try {
			URL imageURL = LoadResource.class.getClassLoader().getResource(fileName);
			if (imageURL != null) {
				image = ImageIO.read(imageURL);
			}
		} catch (IOException excpt) {
			excpt.printStackTrace();
		}
		return image;
	}

	/** RETRIEVE IMAGE ICON FROM IMAGE **/
	public static ImageIcon getImageIconFromImage(String filePath, int imageWidth, int imageHeight) {
		Image image = null;
		ImageIcon myIcon = null;
		try {
			image = LoadResource.getImageFromResourceAsURL(filePath);
			image = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
			myIcon = new ImageIcon(image);
		} catch (Exception excpt) {
		}
		return myIcon;
	}
}