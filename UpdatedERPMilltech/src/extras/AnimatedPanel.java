package extras;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class AnimatedPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private float alpha = 0.0f;
	private Timer appearanceTimer;
	private Timer disappearanceTimer;
	private Color[] gradientColors;
	private int currentColorIndex = 0;

	public AnimatedPanel(Color[] gradientColors) {
		this.gradientColors = gradientColors;
		setOpaque(false);
	}

	public void animateAppearance() {
		int duration = 300; // Animation duration in milliseconds
		int steps = 30; // Number of animation steps
		float stepSize = 1.0f / steps;

		appearanceTimer = new Timer(duration / steps, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alpha += stepSize;
				repaint();
				if (alpha >= 1.0f) {
					alpha = 1.0f;
					((Timer) e.getSource()).stop();
				}
			}
		});
		appearanceTimer.start();
	}

	public void animateDisappearance() {
		int duration = 300; // Animation duration in milliseconds
		int steps = 30; // Number of animation steps
		float stepSize = 1.0f / steps;

		disappearanceTimer = new Timer(duration / steps, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alpha -= stepSize;
				repaint();
				if (alpha <= 0.0f) {
					alpha = 0.0f;
					((Timer) e.getSource()).stop();
					SwingUtilities.getWindowAncestor(AnimatedPanel.this).dispose();
				}
			}
		});
		disappearanceTimer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int width = getWidth();
		int height = getHeight();
		Color startColor = gradientColors[currentColorIndex];
		Color endColor = gradientColors[(currentColorIndex + 1) % gradientColors.length];
		GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, height, endColor);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(gradient);
		Shape roundedRectangle = new RoundRectangle2D.Float(0, 0, width, height, 20, 20);
		g2d.fill(roundedRectangle);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	}

	public void cycleGradientColors() {
		currentColorIndex = (currentColorIndex + 1) % gradientColors.length;
		repaint();
	}
}
