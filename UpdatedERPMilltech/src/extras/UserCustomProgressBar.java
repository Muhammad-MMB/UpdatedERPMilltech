package extras;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.icon.*;
import org.jdesktop.swingx.painter.*;

import java.awt.*;
import java.awt.geom.*;

public class UserCustomProgressBar {

	static JXBusyLabel customLabel;
	public static JXBusyLabel createProgressLabel() {	
		customLabel = new JXBusyLabel(new Dimension(38, 38));
		BusyPainter painter = new BusyPainter(new Rectangle2D.Float(0, 0, 8.0f, 8.0f),
				new Rectangle2D.Float(5.5f, 5.5f, 27.0f, 27.0f));
		painter.setTrailLength(4);
		painter.setPoints(8);
		painter.setFrame(-1);
		painter.setBaseColor(UserCustomColors.LightBlue.color());
		painter.setHighlightColor(UserCustomColors.Orange.color());
		customLabel.setPreferredSize(new Dimension(38, 38));
		customLabel.setIcon(new EmptyIcon(38, 38));
		customLabel.setBusyPainter(painter);
		customLabel.setEnabled(false);
		return customLabel;
	}
	
	public static JXBusyLabel setStatus(boolean isEnable, boolean isBusy) {	
		customLabel.setEnabled(isEnable);
		customLabel.setBusy(isBusy);
		return customLabel;
	}
}
