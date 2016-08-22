package cluedo.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A special class that allows you to set the background of a JPanel to an image.
 * This is specifically used for the feedback and instruction panels.
 */

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	// The background image
	private Image img;
	
	// The JLabel this JPanel currently holds
	private JLabel label;
	
	/**
	 * Constructor for an ImagePanel
	 * @param img - Name of file to use for image
	 */
	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}
	
	/**
	 * Constructor for an ImagePanel
	 * @param img - The Image object for the background
	 */
	public ImagePanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}
	
	/**
	 * Paints the image, and any text in the JLabel
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
		if(label != null){
			g.drawString(label.getText(), 250, 40);
		}
	}
	
	/**
	 * Adds a JLabel to our ImagePanel
	 * @param jl - The JLabel to add
	 */
	public void add(JLabel jl){
		label = jl;
	}
}