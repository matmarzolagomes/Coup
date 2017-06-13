package ssc0103.coup.gui;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JPanelWithBackground extends JPanel {
	private Image backgroundImage;
	private Point start;
	
	public JPanelWithBackground(Image backgroundImage, int x, int y) {
		super(new GridBagLayout());
		this.backgroundImage = backgroundImage;
		start = new Point(x, y);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, start.x, start.y, this);
	}
}