
package cluedo.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import cluedo.boardpieces.Player;
import cluedo.cards.Character;

public class GUI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Create and initalize JPanels for outer borderLayout
	private JPanel top = new JPanel();
	private JPanel boardPanel = new JPanel();
	private JPanel right = new JPanel();
	private JPanel bottom = new JPanel();

	private JButton doneButton = new JButton("Done");

	private JLabel instructionLabel = new JLabel();
	private JPanel[][] board = new JPanel[25][25];
	ImageIcon outOfBoundsTile = new ImageIcon("black.png");

	private ButtonGroup characterGroup = new ButtonGroup();
	private JRadioButton[] characterButtons = { new JRadioButton(" "), new JRadioButton(" "), new JRadioButton(" "),
			new JRadioButton(" "), new JRadioButton(" "), new JRadioButton(" ") };
	JButton submitButton = new JButton("Sumbit");
	JButton nextButton = new JButton("Next Player");
	int playerIndex;
	int numPlayers;

	// Create listener event
	AnswerListener listener = new AnswerListener();
	JComboBox<String> numList;
	ArrayList<Player> players;
	ArrayList<Character> characters;

	public GUI(ArrayList<Character> characters) {
		this.characters = characters;
		setLayout();

	}

	public void drawBoard(Board boardObj) {
		for (int row = 0; row <= 24; row++) {
			for (int col = 0; col <= 24; col++) {
				board[row][col] = new JPanel();
			}
		}

		for (int row = 0; row <= 24; row++) {
			for (int col = 0; col <= 24; col++) {
				JLabel label = new JLabel();
				// label.setPreferredSize(new Dimension(3, 3));
				label.setBackground(Color.blue);
				boardPanel.add(board[row][col].add(label));
				if (boardObj.getBoard()[row][col] == null) {
					label.setIcon(outOfBoundsTile);
				} else {
					label.setIcon(boardObj.getBoard()[row][col].getImageIcon());
				}

				label.setVisible(true);
			}
		}

	}

	public void getNumPlayers() {
		String[] options = new String[] { "3", "4", "5", "6" };

		// add the listener to the done button and add button to the pannel
		doneButton.addActionListener(listener);
		bottom.add(doneButton);

		numList = new JComboBox<>(options);

		instructionLabel.setText("How many players are in this game?");
		top.add(numList);
		top.validate();

	}

	// method sets the layout of the GUI
	public void setLayout() {
		// creates a new border layout-adds only the top, bottom and boardPanel
		// panels
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(700, 600));
		add(top, BorderLayout.NORTH);
		add(bottom, BorderLayout.SOUTH);
		add(boardPanel, BorderLayout.CENTER);
		add(right, BorderLayout.EAST);
		// sets the size and colour of the border layout pannels
		top.setPreferredSize(new Dimension(700, 100));
		bottom.setPreferredSize(new Dimension(700, 100));
		right.setPreferredSize(new Dimension(300, 400));
		top.setBackground(Color.magenta);
		bottom.setBackground(Color.green);
		right.setBackground(Color.yellow);

		top.add(instructionLabel);
		bottom.add(doneButton);
		boardPanel.setLayout(new GridLayout(25, 25));

	}

	private class AnswerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == doneButton) {
				 numPlayers = Integer.parseInt((String) numList.getSelectedItem());

				doneButton.setVisible(false); // hide done button
				numList.setVisible(false); // hide the text field
				playerIndex = 0;
				pickCharacter();

			}
			

			for (JRadioButton characterButton : characterButtons) {
				// ensures user has selected an answer before they can move on
				if (characterButton.isSelected()) {
					// when the user selects a character show the submit button
					instructionLabel.setText("Click Submit");
					submitButton.setVisible(true);
				}
				if (e.getSource() == nextButton) {
					String selectedCharacter = characterButton.getText();
					Character character = null;
					for(Character c: characters){
						if(character.name.equals(selectedCharacter)){
							character = c;
						}
					}
					players.add(new Player(character, true));
					playerIndex ++;
					if(playerIndex < numPlayers ){
						pickCharacter();
					}
				}
			}
		}

		private void pickCharacter() {
			right.setLayout(new GridLayout(7, 1));
			JLabel title = new JLabel();
			characterGroup.clearSelection();
			title.setText("Player " + playerIndex + " select your character");
			right.add(title);
			for (int i = 0; i < characterButtons.length; i++) {
				characterButtons[i].setText(characters.get(i).name);
			}
			for (JRadioButton characterButton : characterButtons) {
				characterGroup.add(characterButton);
				right.add(characterButton);
				characterButton.addActionListener(listener);
			}
		}
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
}