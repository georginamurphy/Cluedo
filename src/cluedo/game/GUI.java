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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import cluedo.boardpieces.RoomTile;
import cluedo.cards.Character;
import cluedo.cards.Room;
import cluedo.cards.Weapon;
import javafx.scene.image.Image;
import cluedo.game.Game.Direction;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;

	// The board and game objects, as well as Cards for the game
	Board board;
	Game game;
	ArrayList<Character> characters;
	ArrayList<Weapon> weapons;
	ArrayList<Room> rooms;
	Player focusPlayer;
	int movesLeft;

	// Image Icons
	Icon redIcon = new ImageIcon("MissScarlett.png");
	Icon yellowIcon = new ImageIcon("ColonelMustard.png");
	Icon blueIcon = new ImageIcon("MrsPeacock.png");
	Icon greenIcon = new ImageIcon("MrGreen.png");
	Icon purpleIcon = new ImageIcon("ProfessorPlum.png");
	Icon whiteIcon = new ImageIcon("MrsWhite.png");

	Icon ropeIcon = new ImageIcon("Rope.png");
	Icon candlestickIcon = new ImageIcon("Candelstick.png");
	Icon knifeIcon = new ImageIcon("Knife.png");
	Icon revolverIcon = new ImageIcon("Revolver.png");
	Icon spannerIcon = new ImageIcon("Wrench.png");
	Icon leadpipeIcon = new ImageIcon("LeadPipe.png");

	Icon loungeIcon = new ImageIcon("Lounge.png");
	Icon studyIcon = new ImageIcon("Study.png");
	Icon kitchenIcon = new ImageIcon("Kitchen.png");
	Icon libraryIcon = new ImageIcon("Library.png");
	Icon conservertoryIcon = new ImageIcon("Observertory.png");
	Icon diningIcon = new ImageIcon("DiningRoom.png");
	Icon hallIcon = new ImageIcon("Hall.png");
	Icon ballroomIcon = new ImageIcon("Ballroom.png");
	Icon billiardIcon = new ImageIcon("BilliardRoom.png");
	
	Icon doorIcon = new ImageIcon("Door.png");

	// Panels
	JPanel instructionPanel;
	JPanel boardPanel;
	JPanel buttonPanel;
	JPanel decisionPanel;
	JPanel feedbackPanel;
	JPanel[][] boardPanels;

	// JRadioButtons
	ButtonGroup characterGroup;
	ButtonGroup roomGroup;
	ButtonGroup weaponGroup;
	JRadioButton lounge, study, kitchen, library, conservertory, dining, hall, ballroom, billiard, red, yellow, blue,
			green, purple, white, leadpipe, candlestick, dagger, rope, revolver, spanner;

	// Labels
	JLabel decisionLabel;
	JLabel instructionLabel = new JLabel("Welcome To Cluedo");
	JLabel feedbackLabel = new JLabel(" ");

	// Buttons
	JButton startGame;
	JButton enter;
	JButton accuse;
	JButton suggest;
	JButton ready;
	JButton rollDice;
	JButton submitGuessButton;

	// JTextFields
	JTextField names;

	// Listeners
	ItemListener jRadio;
	JButtonListener enterListen;
	JButtonListener accuseListen;
	JButtonListener startListen;
	JButtonListener rollListen;
	JButtonListener suggestListen;
	JKeyListener moveListen;

	// Variables for setup of game
	int numPlayers;
	int currentPlayer;

	// Players and Characters
	ArrayList<Player> humanPlayers;
	ArrayList<Player> players;
	ArrayList<Character> chars;

	JComboBox<String> roomComboBox;
	JComboBox<String> characterComboBox;
	JComboBox<String> weaponComboBox;

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
		buttonPanel.add(suggest);
		buttonPanel.add(rollDice);
		buttonPanel.add(accuse);
		ready.setVisible(false);
		accuse.setVisible(false);
		accuseListen = new JButtonListener();
		accuse.addActionListener(accuseListen);
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

		// Set up the layout for feedbackPanel
		feedbackPanel.setLayout(new FlowLayout());

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

				JOptionPane.showMessageDialog(null,
						"It is a long established fact that a reader will be distracted by the readable \n"
								+ "content of a page when looking at its layout. The point of using Lorem Ipsum is \n"
								+ "that it has a more-or-less normal distribution of letters, as opposed to using \n"
								+ "'Content here, content here', making it look like readable English. Many desktop \n"
								+ "publishing packages and web page editors now use Lorem Ipsum as their default model\n"
								+ " text, and a search for 'lorem ipsum' will uncover many web sites still in their \n"
								+ "infancy. Various versions have evolved over the years, sometimes by accident, \n"
								+ "sometimes on purpose (injected humour and the like).",
						"Cluedo For Dummies", JOptionPane.INFORMATION_MESSAGE);
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
		boardPanel.removeAll();
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
		boardPanel.repaint();
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
		red = new JRadioButton(redIcon);
		yellow = new JRadioButton(yellowIcon);
		blue = new JRadioButton(blueIcon);
		green = new JRadioButton(greenIcon);
		purple = new JRadioButton(purpleIcon);
		white = new JRadioButton(whiteIcon);

		// Add the listener to all the JRadioButtons
		red.addItemListener(jRadio);
		yellow.addItemListener(jRadio);
		blue.addItemListener(jRadio);
		green.addItemListener(jRadio);
		purple.addItemListener(jRadio);
		white.addItemListener(jRadio);

		// Add all of the JRadioButtons to a group
		characterGroup = new ButtonGroup();
		characterGroup.add(red);
		characterGroup.add(yellow);
		characterGroup.add(blue);
		characterGroup.add(green);
		characterGroup.add(purple);
		characterGroup.add(white);

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
			decisionPanel.repaint();
		} else {
			// Record the non human players then
			// Fill the non human players
			humanPlayers = new ArrayList<Player>();
			humanPlayers.addAll(players);
			for (Player p : humanPlayers) {
				System.out.println(p);
			}
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

		// Set the button listener
		enterListen = new JButtonListener();
		enter.addActionListener(enterListen);
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
		startListen = new JButtonListener();
		startGame.addActionListener(startListen);
		validate();
	}

	public void takeTurn(Player player) {
		accuse.setVisible(true);
		focusPlayer = player;
		// Reset the decision panel
		decisionPanel.removeAll();
		decisionPanel.validate();
		decisionPanel.repaint();

		boardPanel.removeKeyListener(moveListen);

		// Setup the instruction panel
		instructionLabel.setText("It is time to move " + player.getName() + " on the board. Roll the dice");
		instructionPanel.add(instructionLabel);

		feedbackLabel.setVisible(false);

		// Show the rollDice button to the user
		rollDice.setVisible(true);
		rollListen = new JButtonListener();
		rollDice.addActionListener(rollListen);
		validate();
	}
	
	public void enteredRoom(Player player){
		instructionLabel.setText(focusPlayer.getName() + ", you have entered a room, please make an accusation or a suggestion.");
		feedbackLabel.setVisible(false);
		suggest.setVisible(true);
		suggestListen = new JButtonListener();
		for(ActionListener ls : suggest.getActionListeners() ){
			suggest.removeActionListener(ls);
		}
		suggest.addActionListener(suggestListen);
		validate();
	}
	
	public void leaveRoom(Player player){
		focusPlayer = player;
		boolean cantLeaveRoom = focusPlayer.startTurnInRoom();
		drawBoard(board);
		if(!cantLeaveRoom){
			takeTurn(focusPlayer);
		}
		else{
			
		}
	}
	
	public int getDoorNumber(int numOfDoors){
		Object[] nums = new Object[numOfDoors];
		int i = 1;
		for(int x = 0; x < numOfDoors; x++){
			nums[x] = i;
			i++;
		}
		Object input = JOptionPane.showInputDialog(this, "Choose the door number you wish to exit from", "Choose door number",
				JOptionPane.QUESTION_MESSAGE, null, nums, nums[0]);
		
		if (input == null) {
			System.exit(0);
			return -1; // Shouldn't happen, needed to compile
		} else {
			return (int) input;
		}
	}

	public void makeGuess(Player player) {
		JFrame guessFrame = new JFrame("Construct Guess");
		guessFrame.setPreferredSize(new Dimension(200, 200));
		guessFrame.setLayout(new GridLayout(5, 1));
		guessFrame.setVisible(true);
		guessFrame.validate();
		JPanel panelOne = new JPanel();
		JPanel panelTwo = addRooms();
		JPanel panelThree = addCharacters();
		JPanel panelFour = addWeapons();
		decisionLabel = new JLabel("Construct your guess");
		panelOne.add(decisionLabel);
		guessFrame.add(panelOne);
		guessFrame.add(panelTwo);
		guessFrame.add(panelThree);
		guessFrame.add(panelFour);
		submitGuessButton = new JButton("Submit Guess");
		guessFrame.add(submitGuessButton);
		guessFrame.validate();

		submitGuessButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Room room = new Room(game.getRoomName((String) roomComboBox.getSelectedItem()));
				Weapon weapon = new Weapon(game.getWeaponName((String) weaponComboBox.getSelectedItem()));
				System.out.println((String) characterComboBox.getSelectedItem());
				Character character = new Character(
						game.getCharacterColour((String) characterComboBox.getSelectedItem()));
				if (game.checkSolution(new Solution(weapon, character, room), focusPlayer)) {
					gameWon(focusPlayer);
				} else {
					feedbackLabel.setText("Your guess was incorrect. You have been removed from the game");
					validate();
				}
				guessFrame.dispose();
			}
		});

	}

	public JPanel addRooms() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200, 50));

		String[] items = new String[9];
		for (int i = 0; i < 8; i++) {
			items[i] = rooms.get(i).toString();
		}
		roomComboBox = new JComboBox<String>(items);
		panel.add(roomComboBox);

		panel.validate();
		return panel;
	}

	public JPanel addCharacters() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200, 50));

		String[] items = new String[6];
		for (int i = 0; i < 2; i++) {
			items[i] = characters.get(i).toString();
		}
		roomComboBox = new JComboBox<String>(items);
		panel.add(roomComboBox);

		panel.validate();
		return panel;
	}

	public JPanel addWeapons() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200, 50));

		String[] items = new String[6];
		for (int i = 0; i < 5; i++) {
			items[i] = weapons.get(i).toString();
		}
		weaponComboBox = new JComboBox<String>(items);
		panel.add(weaponComboBox);

		panel.validate();
		return panel;
	}

	public Player getNextPlayer() {
		int index = humanPlayers.indexOf(focusPlayer);
		index++;
		if (index == humanPlayers.size()) {
			index = 0;
		}
		while (index < humanPlayers.size()) {
			if (!humanPlayers.get(index).getDead()) {
				return humanPlayers.get(index);
			}
			if (index == humanPlayers.size() - 1) {
				index = 0;
			}
		}
		return null; // hELLO?
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
			// If the user hit the startGame button
			if (e.getSource() == startGame) {
				game.run();
			}

			// If the user hit the rollDice button
			if (e.getSource() == rollDice) {
				int roll = game.rollDice();
				movesLeft = roll;
				// Remove rollDice button from view
				rollDice.setVisible(false);
				feedbackLabel.setVisible(true);
				feedbackLabel.setText("You rolled " + roll);
				feedbackPanel.validate();
				feedbackPanel.repaint();

				// Let the user begin to move
				moveListen = new JKeyListener();

				buttonPanel.addKeyListener(moveListen);

				// Set our buttonPanel to have focus so keyListener triggers
				// events
				buttonPanel.setFocusable(true);
				buttonPanel.requestFocus();

				for (KeyListener k : boardPanel.getKeyListeners()) {
					boardPanel.removeKeyListener(k);
				}
				boardPanel.addKeyListener(moveListen);

				// Set our buttonPanel to have focus so keyListener triggers
				// events
				boardPanel.setFocusable(true);
				boardPanel.requestFocus();
			}
			
			if (e.getSource() == accuse) {
				int r = (int) JOptionPane.showConfirmDialog(null,
						"Are you sure you want to submit an accusation?\n"
								+ "If you are incorrect you will be removed from the game",
						"Accuse?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (r == JOptionPane.YES_OPTION) {
					game.constructGuess(focusPlayer, true);
				}

			}
			
			if(e.getSource() == suggest){
				 //game.constructGuess(focusPlayer, false);
				takeTurn(getNextPlayer() );
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
					decisionLabel.setText((currentPlayer + 1) + ", please enter a name before clicking enter.");
				}
			}
		}
	}

	private class JKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				if (game.checkValidMove(focusPlayer, Direction.UP)) {
					game.applyMove(focusPlayer, Direction.UP);
					movesLeft--;
					feedbackLabel.setText("You have " + movesLeft + " moves remaining.");
				}
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				if (game.checkValidMove(focusPlayer, Direction.DOWN)) {
					game.applyMove(focusPlayer, Direction.DOWN);
					movesLeft--;
					feedbackLabel.setText("You have " + movesLeft + " moves remaining.");
				}
			} else if (e.getKeyCode() == KeyEvent.VK_A) {
				if (game.checkValidMove(focusPlayer, Direction.LEFT)) {
					game.applyMove(focusPlayer, Direction.LEFT);
					movesLeft--;
					feedbackLabel.setText("You have " + movesLeft + " moves remaining.");
				}
			} else if (e.getKeyCode() == KeyEvent.VK_D) {
				if (game.checkValidMove(focusPlayer, Direction.RIGHT)) {
					game.applyMove(focusPlayer, Direction.RIGHT);
					movesLeft--;
					feedbackLabel.setText("You have " + movesLeft + " moves remaining.");
				}
			}

			// Has the user entered a room?
			if(game.isInRoom(focusPlayer) ){
				enteredRoom(focusPlayer);
			}
			// Else have they ended their turn not in a room? Move onto next
			// player
			else if (movesLeft == 0 && !game.isInRoom(focusPlayer)) {
				Player nextPlayer = getNextPlayer();
				if(nextPlayer == null){System.out.println("NEXT PLAYER WAS NULL???");}
				else{
					if(game.isInRoom(nextPlayer) ){
						feedbackLabel.setText(" ");
						instructionLabel.setText(nextPlayer.getName() + ", you must now leave this room, choose which door you would like to exit from.");
						leaveRoom(nextPlayer);
					}
					else{
						takeTurn(nextPlayer);
					}
				}
			}
		}

	}

}
