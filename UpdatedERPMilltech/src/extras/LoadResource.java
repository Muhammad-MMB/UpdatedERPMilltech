package extras;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class LoadResource {

	/** RETURN IMAGE AS AN IMAGE  **/
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

	/** RETURN IMAGE AS AN IMAGE ICON  **/
	public static ImageIcon getImageIconFromResource(String fileName) {
		URL imageURL = LoadResource.class.getClassLoader().getResource(fileName);
		ImageIcon icon = null;
		if (imageURL != null) {
			icon = new ImageIcon(imageURL);
		}
		return icon;
	}
}