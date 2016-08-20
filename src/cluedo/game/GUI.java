package cluedo.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cluedo.boardpieces.Player;
import cluedo.cards.Character;
import cluedo.cards.Room;
import cluedo.cards.Weapon;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;

	// The board and game objects, as well as Cards for the game
	Board board;
	Game game;
	ArrayList<Character> characters;
	ArrayList<Weapon> weapons;
	ArrayList<Room> rooms;

	// Panels
	JPanel instructionPanel;
	JPanel boardPanel;
	JPanel buttonPanel;
	JPanel decisionPanel;
	JPanel feedbackPanel;
	JPanel[][] boardPanels;

	// JRadioButtons
	ButtonGroup group;
	JRadioButton red, yellow, blue, green, purple, white;

	// Labels
	JLabel decisionLabel;
	JLabel instructionLabel = new JLabel("Welcome To Cluedo");
	JLabel instructionLabel2 = new JLabel(" ");
	JLabel feedbackLabel = new JLabel(" ");

	// Buttons
	JButton startGame;
	JButton enter;
	JButton accuse;
	JButton suggest;
	JButton ready;
	JButton rollDice;

	// JTextFields
	JTextField names;

	// Listeners
	ItemListener jRadio;
	JButtonListener jButtonListen;

	// Variables for setup of game
	int numPlayers;
	int currentPlayer;

	// Players and Characters
	ArrayList<Player> players;
	ArrayList<Character> chars;

	public GUI(Board board, ArrayList<Character> characters, ArrayList<Weapon> weapons, ArrayList<Room> rooms) {
		this.board = board;
		this.chars = characters;
		this.characters = characters;
		this.weapons = weapons;
		this.rooms = rooms;
		setLayout();
		drawBoard(this.board);
		pack();
	}

	/**
	 * Called upon creation of the game. Essentially initializes the GUI and
	 * it's panels to be ready for the user to set up the game.
	 */
	public void setLayout() {
		// Set the button listener
		jButtonListen = new JButtonListener();
		// Initialize panels
		instructionPanel = new JPanel();
		boardPanel = new JPanel();
		buttonPanel = new JPanel();
		decisionPanel = new JPanel();
		feedbackPanel = new JPanel();
		boardPanels = new JPanel[25][25];

		// Initialize buttons
		accuse = new JButton("Accuse");
		suggest = new JButton("Suggest");
		ready = new JButton("Ready");
		rollDice = new JButton("Roll Dice");

		// Add buttons to the button panel
		buttonPanel.add(ready);
		buttonPanel.add(accuse);
		buttonPanel.add(suggest);
		buttonPanel.add(rollDice);
		ready.setVisible(false);
		accuse.setVisible(true);
		accuse.addActionListener(jButtonListen);
		suggest.setVisible(false);
		rollDice.setVisible(false);

		// Sets Layout / Size of JFrame, and adds our 4 panels
		setLayout(new BorderLayout(0, 0));
		setPreferredSize(new Dimension(800, 600));
		add(instructionPanel, BorderLayout.NORTH);
		add(feedbackPanel, BorderLayout.SOUTH);
		add(boardPanel, BorderLayout.CENTER);
		add(decisionPanel, BorderLayout.EAST);
		add(buttonPanel, BorderLayout.WEST);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		// Setting up layout for boardPanel
		GridLayout bpLayout = new GridLayout(25, 25, 0, 0);
		boardPanel.setLayout(bpLayout);

		// Setting up layout for decisionPanel
		decisionPanel.setLayout(new FlowLayout());

		// Sets the size and color of the border layout panels
		instructionPanel.setPreferredSize(new Dimension(700, 75));
		feedbackPanel.setPreferredSize(new Dimension(700, 75));
		decisionPanel.setPreferredSize(new Dimension(300, 400));
		buttonPanel.setPreferredSize(new Dimension(100, 400));

		feedbackPanel.add(feedbackLabel);
		instructionPanel.add(instructionLabel);

		makeMenu();
		validate();
	}

	public void makeMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Options");
		JMenuItem help = new JMenuItem("Help");
		JMenuItem exit = new JMenuItem("Exit");

		menu.add(help);
		menu.add(exit);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				JFrame helpFrame = new JFrame("Help");
//				JPanel helpPanel = new JPanel();
//				JLabel helpLabel = new JLabel();
//				JButton 
//				helpLabel.setText("Help info here");
//				helpFrame.setPreferredSize(new Dimension(100, 100));
				
				JOptionPane.showMessageDialog(null, "It is a long established fact that a reader will be distracted by the readable \n"
						+ "content of a page when looking at its layout. The point of using Lorem Ipsum is \n"
						+ "that it has a more-or-less normal distribution of letters, as opposed to using \n"
						+ "'Content here, content here', making it look like readable English. Many desktop \n"
						+ "publishing packages and web page editors now use Lorem Ipsum as their default model\n"
						+ " text, and a search for 'lorem ipsum' will uncover many web sites still in their \n"
						+ "infancy. Various versions have evolved over the years, sometimes by accident, \n"
						+ "sometimes on purpose (injected humour and the like).", "Cluedo For Dummies", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	/**
	 * Draws the board on the screen in the boardPanel JPanel Uses a
	 * GridLayout(25, 25).
	 * 
	 * @param boardObj
	 *            - The board object we want to draw
	 */
	public void drawBoard(Board boardObj) {
		for (int row = 0; row <= 24; row++) {
			for (int col = 0; col <= 24; col++) {
				boardPanels[row][col] = new JPanel(new GridLayout());

				JLabel label = new JLabel();
				boardPanel.add(boardPanels[row][col].add(label));
				if (boardObj.getBoard()[row][col] == null) {
					label.setIcon(new ImageIcon("black.png"));
				} else {
					label.setIcon(boardObj.getBoard()[row][col].getImageIcon());
				}
				label.setVisible(true);
			}
		}

		boardPanel.validate();
	}

	/**
	 * Called to allow the user's to select which character they wish to play
	 * as. Uses RadioButtons.
	 */
	public void pickCharacters(int numPlayers) {
		players = new ArrayList<Player>();
		jRadio = new JRadioListener();
		// Make our label for the panel
		currentPlayer = 0;
		decisionLabel = new JLabel("Player " + (currentPlayer + 1) + ", select the player you wish to play as.");
		decisionPanel.add(decisionLabel);

		// Make all the JRadioButtons
		red = new JRadioButton("Miss Scarlett");
		yellow = new JRadioButton("Colonel Mustard");
		blue = new JRadioButton("Miss Peacock");
		green = new JRadioButton("Reverend Green");
		purple = new JRadioButton("Professor Plum");
		white = new JRadioButton("Mrs. White");

		// Add the listener to all the JRadioButtons
		red.addItemListener(jRadio);
		yellow.addItemListener(jRadio);
		blue.addItemListener(jRadio);
		green.addItemListener(jRadio);
		purple.addItemListener(jRadio);
		white.addItemListener(jRadio);

		// Add all of the JRadioButtons to a group
		group = new ButtonGroup();
		group.add(red);
		group.add(yellow);
		group.add(blue);
		group.add(green);
		group.add(purple);
		group.add(white);

		// Add all the buttons to the decisionPanel
		decisionPanel.add(red);
		decisionPanel.add(yellow);
		decisionPanel.add(blue);
		decisionPanel.add(green);
		decisionPanel.add(purple);
		decisionPanel.add(white);

		decisionPanel.validate();
	}

	/**
	 * Displays an OptionPane to the user to get input on how many players will
	 * be taking part. If the user selects cancel, the game will exit.
	 * 
	 * @return - The number of players between 3 and 6, inclusive.
	 */
	public int getNumPlayers() {
		Object[] nums = { 3, 4, 5, 6 };
		Object input = JOptionPane.showInputDialog(this, "How many players are in this game", "Number of players",
				JOptionPane.QUESTION_MESSAGE, null, nums, nums[0]);

		if (input == null) {
			System.exit(0);
			return -1; // Shouldn't happen, needed to compile
		} else {
			numPlayers = (int) input;
			currentPlayer = 0;
			return numPlayers;
		}
	}

	/**
	 * Called when the JRadioListener detects a state change amongst the buttons
	 * 
	 * @param o
	 *            - The object that was the source of the state change
	 */
	public void setPlayer(Object o) {
		if (o == red) {
			players.add(new Player(getCharacter(Character.Colour.RED), true));
			decisionPanel.remove(red);
			currentPlayer++;
		} else if (o == yellow) {
			players.add(new Player(getCharacter(Character.Colour.YELLOW), true));
			decisionPanel.remove(yellow);
			currentPlayer++;
		} else if (o == blue) {
			players.add(new Player(getCharacter(Character.Colour.BLUE), true));
			decisionPanel.remove(blue);
			currentPlayer++;
		} else if (o == green) {
			players.add(new Player(getCharacter(Character.Colour.GREEN), true));
			decisionPanel.remove(green);
			currentPlayer++;
		} else if (o == purple) {
			players.add(new Player(getCharacter(Character.Colour.PURPLE), true));
			decisionPanel.remove(purple);
			currentPlayer++;
		} else if (o == white) {
			players.add(new Player(getCharacter(Character.Colour.WHITE), true));
			decisionPanel.remove(white);
			currentPlayer++;
		}

		if (currentPlayer != numPlayers) {
			decisionLabel.setText("Player " + (currentPlayer + 1) + ", select the player you wish to play as.");
			decisionPanel.validate();
		} else {
			// Fill the non human players
			fillRemainingPlayers();
			resetDecisionPanel();
		}
	}

	/**
	 * Fills the players array with the remaining characters that have not been
	 * chosen
	 */
	public void fillRemainingPlayers() {
		if (numPlayers == 6) {
			return;
		}

		for (Character c : chars) {
			players.add(new Player(c, false));
		}
	}

	public void resetDecisionPanel() {
		// Reset boardPanel and redraw with player locations
		boardPanel.removeAll();
		board = new Board(players);
		drawBoard(board);

		// Remove the decisionPanel from our components
		// Initalize our startGame button!
		remove(decisionPanel);
		decisionPanel.removeAll();
		setLayout();
		chooseNames();
		// prepareGame();
	}

	/**
	 * Creates the Text area and button for the user's to input their names
	 */
	public void chooseNames() {
		currentPlayer = 0;

		// Set the label
		decisionLabel.setText("Player " + (currentPlayer + 1) + ", enter your name and click enter.");
		decisionPanel.add(decisionLabel);

		// Set the JTextArea
		names = new JTextField(" ");
		names.setPreferredSize(new Dimension(150, 28));
		decisionPanel.add(names);
		names.setVisible(true);

		// Set the button
		enter = new JButton("Enter");
		decisionPanel.add(enter);

		
		enter.addActionListener(jButtonListen);
	}

	/**
	 * Gets a character and then removes it from our ArrayList as it has been
	 * used
	 * 
	 * @param col
	 * @return
	 */
	private Character getCharacter(Character.Colour col) {
		Character toReturn = null;
		for (Character c : chars) {
			if (c.colour == col) {
				toReturn = c;
			}
		}
		chars.remove(toReturn);
		return toReturn;
	}

	/**
	 * Called after players have chosen their names, creates the game, the
	 * solution and deals the cards
	 */
	public void prepareGame() {
		game = new Game(board, players, this);
		game.createSolution(characters, weapons, rooms);
		game.dealCards(characters, weapons, rooms);

		startLabel();
	}

	public void startLabel() {
		decisionLabel.setText("Press the button to start the game!");
		decisionPanel.add(decisionLabel);
		startGame = new JButton("Start");
		decisionPanel.add(startGame);
		validate();
	}

	public void takeTurn(Player player) {
		instructionLabel.setText("It is time to move " + player.getCharacter().name + " on the board");
		instructionLabel2.setText("Roll the dice");
		instructionPanel.add(instructionLabel);
		instructionPanel.add(instructionLabel2);
		rollDice.setVisible(true);
		validate();

	}

	public void gameWon(Player winner) {
		decisionPanel.removeAll();

		// Set the label
		decisionLabel.setText(winner.getName() + " has won the game");
		decisionPanel.add(decisionLabel);
	}

	// ============================================================================================================================================
	// LISTENER CLASSES LAY BEYOND THIS POINT
	// ============================================================================================================================================

	/**
	 * A listener for the JRadioButtons
	 */
	private class JRadioListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				setPlayer(e.getSource());
			}
		}
	}

	private class JButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == startGame) {
				game.run();
			}
			if (e.getSource() == rollDice) {
				int roll = game.rollDice();
				rollDice.setVisible(false);
				instructionLabel2.setText(" ");
				feedbackLabel.setText("You rolled " + roll);
			}
			if(e.getSource() == accuse){
				int r = (int) JOptionPane.showConfirmDialog(null, "Are you sure you want to submit an accusation?\n"
						+ "If you are incorrect you will be removed from the game", "Accuse?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				
			}
			if (e.getSource() == enter) {
				if (!names.getText().equals(" ")) {
					players.get(currentPlayer).setName(names.getText());
					currentPlayer++;
					decisionLabel.setText("Player " + (currentPlayer + 1) + ", enter your name and click enter.");
					names.setText(" ");
					if (currentPlayer == numPlayers) {
						decisionPanel.removeAll();
						decisionPanel.repaint(); // Repaints the panel to remove
													// the JTextArea / Button
						prepareGame();
					}
				} else {
					decisionLabel.setText("Please enter a name before clicking enter.");
				}
			}
		}
	}

}
