package cluedo.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class GUI extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Create and initalize JPanels for outer borderLayout
	  private JPanel top = new JPanel ();
	  private JPanel center = new JPanel ();
	  private JPanel right = new JPanel ();
	  private JPanel bottom = new JPanel ();
		
	  private JLabel instructionLabel = new JLabel("Welcome to Cluedo");
	  private JLabel feedbackLabel = new JLabel("");
	  private JPanel[][] board = new JPanel[25][25];
	  ImageIcon floorTile = new ImageIcon("floorTile.png");
		  
	  public GUI(){
		  setLayout();
	  }
	
	//method sets the layout of the GUI
	  public void setLayout(){
	//creates a new border layout-adds only the top, bottom and center panels
	    setLayout(new BorderLayout());
	    setPreferredSize(new Dimension(700,600));
	    add(top, BorderLayout.NORTH);
	    add(bottom, BorderLayout.SOUTH);
	    add(center, BorderLayout.CENTER);
	    add(right, BorderLayout.EAST); 
	//sets the size and colour of the border layout pannels
	    top.setPreferredSize(new Dimension(700,100));
	    bottom.setPreferredSize(new Dimension(700,100));
	    right.setPreferredSize(new Dimension(300, 400));
	    top.setBackground(Color.magenta);
	    bottom.setBackground(Color.green);
	    right.setBackground(Color.yellow);
	    
	    top.add(instructionLabel);
	    bottom.add(feedbackLabel);
	    center.setLayout(new GridLayout(25, 25));
	    
	    for(int row = 0; row <= 24; row++){
	    	for(int col = 0; col <= 24; col++){
	    		board[row][col] = new JPanel();
	    	}
	    }
	    
	    for(int row = 0; row <= 24; row++){
	    	for(int col = 0; col <= 24; col++){
	    		JLabel label = new JLabel();
	    		//label.setPreferredSize(new Dimension(3, 3));
	    		label.setBackground(Color.blue);
	    		center.add(board[row][col].add(label));
	    		
	    		label.setIcon(floorTile);
	    		label.setVisible(true);
	    	}
	    }
	  }
}
