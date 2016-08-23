package cluedo.gui;

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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
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

import cluedo.boardpieces.*;
import cluedo.cards.Card;
import cluedo.cards.Character.Colour;
import cluedo.controller.CluedoController;
import cluedo.game.Board;
import cluedo.game.Game.Direction;

public class GUI extends JFrame{
	private static final long serialVersionUID = 1L;

	// The controller
	CluedoController controller;
	
	// Panels
	ImagePanel instructionPanel;
	ImagePanel feedbackPanel;
	JPanel boardPanel;
	JPanel buttonPanel;
	JPanel decisionPanel;
	JPanel decisionBottom;
	JPanel[][] boardPanels;
	
	// JRadioButtons
	ButtonGroup characterGroup;
	ButtonGroup roomGroup;
	ButtonGroup weaponGroup;
	JRadioButton lounge, study, kitchen, library, conservertory, dining, hall, ballroom, billiard, red, yellow, blue,
			green, purple, white, leadpipe, dagger, rope, revolver, wrench;
	
	// Labels
	JLabel decisionLabel;
	JLabel instructionLabel;
	JLabel feedbackLabel;
	

	// Buttons
	JButton startGame;
	JButton enter;
	JButton accuse;
	JButton suggest;
	JButton ready;
	JButton rollDice;
	JButton submitGuessButton;
	JButton submiteAccusationButton;

	// JTextFields
	JTextField names;

	// Listeners
	ItemListener jRadio;
	JButtonListener enterListen;
	JButtonListener accuseListen;
	JButtonListener startListen;
	JButtonListener rollListen;
	JButtonListener suggestListen;
	JButtonListener weaponListen;
	JKeyListener moveListen;
	JWindowListener windowListen;
	JMouseListener mouseListen;
	ShortCutListener shortcut;
	
	// Image Icons
	Icon redIcon = new ImageIcon("MissScarlett.png");
	Icon yellowIcon = new ImageIcon("ColonelMustard.png");
	Icon blueIcon = new ImageIcon("MrsPeacock.png");
	Icon greenIcon = new ImageIcon("MrGreen.png");
	Icon purpleIcon = new ImageIcon("ProfessorPlum.png");
	Icon whiteIcon = new ImageIcon("MrsWhite.png");

	Icon ropeIcon = new ImageIcon("Rope.png");
	Icon candlestickIcon = new ImageIcon("Candlestick.png");
	Icon daggerIcon = new ImageIcon("Dagger.png");
	Icon revolverIcon = new ImageIcon("Revolver.png");
	Icon wrenchIcon = new ImageIcon("Wrench.png");
	Icon leadpipeIcon = new ImageIcon("LeadPipe.png");

	Icon loungeIcon = new ImageIcon("Lounge.png");
	Icon studyIcon = new ImageIcon("Study.png");
	Icon kitchenIcon = new ImageIcon("Kitchen.png");
	Icon libraryIcon = new ImageIcon("Library.png");
	Icon conservatoryIcon = new ImageIcon("Conservatory.png");
	Icon diningIcon = new ImageIcon("DiningRoom.png");
	Icon hallIcon = new ImageIcon("Hall.png");
	Icon ballroomIcon = new ImageIcon("Ballroom.png");
	Icon billiardIcon = new ImageIcon("BilliardRoom.png");
		
	Icon doorIcon = new ImageIcon("Door.png");
	
	// Variable to help with initialization
	int playerCounter;
	
	/**
	 * Constructor for a GUI 
	 * @param controller - The controller coordinating our interaction with Game
	 */
	public GUI(CluedoController controller) {
		this.controller = controller;
		setLayout();
		drawBoard();
		pack();
	}
	
	/**
	 * Called upon creation of the game. Essentially initializes the GUI and
	 * it's panels to be ready for the user to set up the game.
	 */
	public void setLayout() {
		// Initialize panels
		instructionPanel = new ImagePanel(new ImageIcon("InstructionPanel.png").getImage() );
		boardPanel = new JPanel();
		buttonPanel = new JPanel();
		decisionPanel = new JPanel();
		feedbackPanel = new ImagePanel(new ImageIcon("FeedbackPanel.png").getImage() );
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
		setResizable(false);
		setVisible(true);

		// Setting up layout for boardPanel
		GridLayout bpLayout = new GridLayout(25, 25, 0, 0);
		boardPanel.setLayout(bpLayout);
		boardPanel.setBackground(Color.LIGHT_GRAY);
		mouseListen = new JMouseListener();
		boardPanel.addMouseListener(mouseListen);

		// Setting up layout for decisionPanel
		decisionPanel.setLayout(new FlowLayout());
		decisionPanel.setBackground(Color.LIGHT_GRAY);
		
		// Set up the layout for feedbackPanel
		feedbackPanel.setLayout(new FlowLayout());
		
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		// Sets the size and color of the border layout panels
		instructionPanel.setPreferredSize(new Dimension(700, 75));
		feedbackPanel.setPreferredSize(new Dimension(700, 75));
		decisionPanel.setPreferredSize(new Dimension(300, 400));
		buttonPanel.setPreferredSize(new Dimension(100, 400));
		
		// Setup feedback / instruction labels and add to appropriate panels
		feedbackLabel = new JLabel();
		instructionLabel = new JLabel("Welcome to Cluedo");
		feedbackPanel.add(feedbackLabel);
		instructionPanel.add(instructionLabel);

		makeMenu();
		validate();
	}
	
	/**
	 * Draws the board on the screen in the boardPanel JPanel Uses a
	 * GridLayout(25, 25).
	 * 
	 * @param boardObj
	 *            - The board object we want to draw
	 */
	public void drawBoard() {
		Board boardObj = controller.getBoard();
		boardPanel.removeAll();
		
		// Loop over each cell in board and create a JPanel for it
		// Add a JLabel with an ImageIcon to that panel
		// Update and display the panel
		for (int row = 0; row <= 24; row++) {
			for (int col = 0; col <= 24; col++) {
				boardPanels[row][col] = new JPanel(new GridLayout());

				JLabel label = new JLabel();
				boardPanel.add(boardPanels[row][col].add(label));
				if (boardObj.getBoard()[row][col] == null) {
					label.setIcon(new ImageIcon("black.png"));
				} 
				else if(controller.isRoomFloor(row, col)) {
					label.setIcon(new ImageIcon("woodFloor.png"));
				} 
				else{
					label.setIcon(boardObj.getBoard()[row][col].getImageIcon());
				}
				label.setVisible(true);
			}
		}

		boardPanel.validate();
		boardPanel.repaint();
	}
	
	/**
	 * Makes the Menu for the GUI
	 */
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
				int input = JOptionPane.showConfirmDialog(null, "Do you really want to close the game?", "Are you sure", JOptionPane.OK_CANCEL_OPTION);
				if(input == 0){
					System.exit(0);
				}
			}
		});

		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null,
						"Welcome to Cluedo!\n"
						+ "The aim of the game is make an accusation that correctly matches the solution for\n"
						+ "this game.  Be careful though!  Making an incorrect accusation will result in you\n"
						+ "losing the game.  In order to deduce the correct solution, you should move around\n"
						+ "the map to different rooms and make suggestions.  At any point during your suggestion\n"
						+ "or accusation, if you close the popup window you will void your turn!  Below are a\n"
						+ "shortcut or two that you may find useful while playing,\n"
						+ "Good luck detectives!\n"
						+ "\n"
						+ "WASD Keys - Move your player around the map\n"
						+ "F Key - Starts a suggestion\n"
						+ "G Key - Starts an accusation\n"
						+ "R Key - Rolls the dice", "Important Information!", JOptionPane.INFORMATION_MESSAGE);
			}
		});
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
			return (int) input;
		}
	}
	
	/**
	 * Creates the Text area and button for the user's to input their names
	 */
	public void chooseNames() {
		playerCounter = 0;

		// Set the label
		decisionLabel = new JLabel();
		decisionLabel.setText("Player " + (playerCounter + 1) + ", enter your name and click enter.");
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
		
		validate();
	}
	
	/**
	 * Called to allow the user's to select which character they wish to play
	 * as. Uses RadioButtons.
	 */
	public void pickCharacters() {
		jRadio = new JRadioListener();
		// Make our label for the panel
		playerCounter = 0;
		controller.resetFocus();
		decisionLabel = new JLabel(controller.getHumanPlayers().get(0).getName() + ", select the character you wish to play as.");
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
	 * Called when the JRadioListener detects a state change amongst the buttons
	 * 
	 * @param o
	 *            - The object that was the source of the state change
	 */
	public void setPlayer(Object o) {
		if (o == red) {
			controller.addCharacterToPlayer(controller.getFocus(), controller.getCharacter(Colour.RED) );
			decisionPanel.remove(red);
			playerCounter++;
		} 
		else if (o == yellow) {
			controller.addCharacterToPlayer(controller.getFocus(), controller.getCharacter(Colour.YELLOW) );
			decisionPanel.remove(yellow);
			playerCounter++;
		}
		else if (o == blue) {
			controller.addCharacterToPlayer(controller.getFocus(), controller.getCharacter(Colour.BLUE) );
			decisionPanel.remove(blue);
			playerCounter++;
		} 
		else if (o == green) {
			controller.addCharacterToPlayer(controller.getFocus(), controller.getCharacter(Colour.GREEN) );
			decisionPanel.remove(green);
			playerCounter++;
		} 
		else if (o == purple) {
			controller.addCharacterToPlayer(controller.getFocus(), controller.getCharacter(Colour.PURPLE) );
			decisionPanel.remove(purple);
			playerCounter++;
		} 
		else if (o == white) {
			controller.addCharacterToPlayer(controller.getFocus(), controller.getCharacter(Colour.WHITE) );
			decisionPanel.remove(white);
			playerCounter++;
		}
		
		controller.setFocus(controller.getNextPlayer() );
		if (playerCounter != controller.getNumPlayers() ) {
			decisionLabel.setText(controller.getFocus().getName() +  ", select the character you wish to play as.");
			decisionPanel.validate();
			decisionPanel.repaint();
		} 
		else {
			// Record the non human players then
			// Fill the non human players
			controller.fillRemainingPlayers();
			controller.createBoardWithPlayers();
			resetDecisionPanel();
		}
	}
	
	/**
	 * Reset the decisionPanel and display the start button
	 */
	public void resetDecisionPanel() {
		// Reset boardPanel and redraw with player locations
		boardPanel.removeAll();
		drawBoard();

		// Remove the decisionPanel from our components
		// Initialize our startGame button
		remove(decisionPanel);
		decisionPanel.removeAll();
		setLayout();
		
		decisionLabel.setText("Press the button to start the game.");
		JOptionPane.showMessageDialog(null, "We strongly advise that all players read the help tab"
				+ " in the options menu.  It displays some useful shortcut keys and\n"
				+ "the basic rules of Cluedo.  Good luck players!", "Read me please!", JOptionPane.INFORMATION_MESSAGE);
		startGame = new JButton("Start");
		startListen = new JButtonListener();
		startGame.addActionListener(startListen);
		decisionLabel.add(startGame);
		decisionPanel.add(decisionLabel);
		decisionPanel.add(startGame);
		
		repaint();
		validate();
	}
	
	/**
	 * Displays the focusPlayer's hand in the decisionPanel
	 */
	public void displayHand(){
		
		for(Card c : controller.getFocus().getCards() ){
			JLabel label = new JLabel();
			label.setIcon(c.getImageIcon() );
			decisionBottom.add(label);
		}
		decisionPanel.validate();
		decisionPanel.repaint();
	}
	
	/**
	 * Resets the decisionPanel so it no longer display's focusPlayer's hand
	 */
	public void unDisplayHand(){
		decisionPanel.removeAll();
		decisionPanel.validate();
		decisionPanel.repaint();
	}
	
	/**
	 * Called when the rollDice button is clicked or called
	 * Rolls the dice, displays a users hand and sets up listeners / buttons
	 * for the players turn
	 */
	public void rollDice(){
		controller.rollDice();
		// Remove rollDice button from view
		rollDice.setVisible(false);
		feedbackLabel.setVisible(true);
		feedbackLabel.setText("You rolled " + controller.getMovesLeft());
		feedbackPanel.validate();
		feedbackPanel.repaint();
		
		displayHand();

		// Let the user begin to move
		moveListen = new JKeyListener();
		
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
		instructionLabel.setText(controller.getFocus().getName() + ", use the WASD keys to move around the board.");
		instructionPanel.repaint();
		validate();
	}
	
	/**
	 * Start's the focusPlayer's turn, sets up rollDice and accusation buttons
	 */
	public void takeTurn() {
		if(controller.isInRoom(controller.getFocus() ) ){
			leaveRoom();
		}
		
		suggest.setVisible(false);
		accuse.setVisible(true);
		// Reset the decision panel
		decisionPanel.removeAll();
		decisionPanel.setLayout(new BorderLayout());
		JLabel one = new JLabel(controller.getFocus().getName() +  " it is your turn.");
		JLabel two = new JLabel(" Your character: " + controller.getFocus().getCharacter().name);
		JLabel three = new JLabel(" Your characters colour: " + controller.getFocus().getCharacter().colour.toString());
		JPanel decisionTop = new JPanel();
		JPanel decisionLeft = new JPanel();
		decisionBottom = new JPanel();
		decisionLeft.setBackground(Color.LIGHT_GRAY);
		decisionTop.setBackground(Color.LIGHT_GRAY);
		decisionBottom.setBackground(Color.LIGHT_GRAY);
		decisionPanel.add(decisionTop, BorderLayout.NORTH);
		decisionPanel.add(decisionLeft, BorderLayout.WEST);
		decisionPanel.add(decisionBottom, BorderLayout.CENTER);
		decisionLeft.setPreferredSize(new Dimension(10, 300));
		decisionTop.setLayout(new GridLayout(3, 1));
		decisionTop.add(one);
		decisionTop.add(two);
		decisionTop.add(three);
		decisionPanel.validate();
		decisionPanel.repaint();
		
		for(KeyListener kl : boardPanel.getKeyListeners() ){
			boardPanel.removeKeyListener(kl);
		}
		boardPanel.removeKeyListener(moveListen);
		boardPanel.removeKeyListener(shortcut);
		shortcut = new ShortCutListener();
		boardPanel.addKeyListener(shortcut);
		boardPanel.requestFocus();
		

		// Setup the instruction panel
		instructionLabel.setText(controller.getFocus().getName() + " roll the dice");
		instructionPanel.repaint();
		feedbackLabel.setVisible(false);
		
		drawBoard();

		// Show the rollDice button to the user
		rollDice.removeActionListener(rollListen);
		rollDice.setVisible(true);
		rollListen = new JButtonListener();
		rollDice.addActionListener(rollListen);
		validate();
	}
	
	/**
	 * Prompts user input to select the door they wish to exit their room from
	 * @param numOfDoors
	 * @return
	 */
	public int getDoorNumber(int numOfDoors){
		Object[] nums = new Object[numOfDoors];
		int i = 1;
		for(int x = 0; x < numOfDoors; x++){
			nums[x] = i;
			i++;
		}
		Object input = JOptionPane.showInputDialog(this, "Doors are ordered left to right, top to bottom.\n"
				+ "Please select a door to leave from.", controller.getFocus().getName() + ", you need to leave this room!",
				JOptionPane.QUESTION_MESSAGE, null, nums, nums[0]);
		
		if (input == null) {
			System.exit(0);
			return -1; // Shouldn't happen, needed to compile
		} else {
			return (int) input;
		}
	}
	
	/**
	 * Updates the feedbackPanel with the correct message for making moves
	 * @param successfulMove - if the move was successful
	 */
	public void updateFeedbackMoves(boolean successfulMove){
		if(successfulMove){
			feedbackLabel.setText("You have " + controller.getMovesLeft() + " moves remaining.");
			if(controller.getMovesLeft() != 0){
				feedbackPanel.repaint();
			}
			else{
				feedbackLabel.setText(" ");
				feedbackPanel.repaint();
			}
		}
		else{
			feedbackLabel.setText("That was an invalid move! Try again " + controller.getFocus().getName() );
			feedbackPanel.repaint();
		}
		feedbackLabel.validate();
		feedbackLabel.repaint();
	}
	
	/**
	 * Updates the labels in our GUI to display correctly when a user needs to leave a room
	 */
	public void updateLabelsLeavingRoom(){
		feedbackLabel.setText(" ");
		feedbackPanel.repaint();
		instructionLabel.setText(controller.getFocus().getName() + ", you must now leave this room, choose which door you would like to exit from.");
		instructionPanel.repaint();
	}
	
	/**
	 * Called when a player enters a room, displays accuse and suggest buttons
	 */
	public void enteredRoom(){
		feedbackLabel.setText(" ");
		feedbackPanel.repaint();
		instructionLabel.setText(controller.getFocus().getName() + ", you have entered a room, please make an accusation or a suggestion.");
		instructionPanel.repaint();
		suggest.setVisible(true);
		suggestListen = new JButtonListener();
		for(ActionListener ls : suggest.getActionListeners() ){
			suggest.removeActionListener(ls);
		}
		suggest.addActionListener(suggestListen);
		validate();
	}
	
	/**
	 * Sets the GUI to display the correct information when a player leaves a room
	 */
	public void leaveRoom(){
		unDisplayHand();
		drawBoard();
		controller.startTurnInRoom();
		drawBoard();
	}
	
	/**
	 * When a player's turn has been skipped because they can't leave a room, this notifies them
	 * @param player
	 */
	public void displaySkippedTurn(Player player) {
		JOptionPane.showMessageDialog(null, player.getName() + ", all your exit doors are blocked, you cannot leave!\n"
				+ "It is now " + controller.getFocus().getName() + "'s turn.");
	}
	
	/**
	 * Sets up a GuessWindow to allow the user to make a suggestion
	 */
	public void constructSuggestion(){
		GuessWindow guessFrame = new GuessWindow("Pick your suggestions", controller);
		windowListen = new JWindowListener();
		guessFrame.addWindowListener(windowListen);
		guessFrame.startChoices(true);
	}
	
	/**
	 * Sets up a GuessWindow to all the user to make an accusation
	 */
	public void constructAccusation(){
		GuessWindow guessFrame = new GuessWindow("Pick your accusations", controller);
		windowListen = new JWindowListener();
		guessFrame.addWindowListener(windowListen);
		guessFrame.startChoices(false);
	}
	
	// =========================================================================================================================
	//                                                    PRIVATE CLASSES
	// =========================================================================================================================
	
	/**
	 * A listener for the JRadioButtons in the GUI
	 */
	private class JRadioListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				setPlayer(e.getSource());
			}
		}
	}
	
	/**
	 * A listener for the buttons in the GUI
	 */
	private class JButtonListener implements ActionListener{
		
		/**
		 * Responds to buttons within the GUI being pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// If the button clicked was the enter button
			if(e.getSource() == enter){
				if (!names.getText().equals(" ")) {
					controller.addPlayer(new Player(names.getText(), true) );
					playerCounter++;
					decisionLabel.setText("Player " + (playerCounter + 1) + ", enter your name and click enter.");
					names.setText(" ");
					if (playerCounter == controller.getNumPlayers() ) {
						decisionPanel.removeAll();
						decisionPanel.repaint();
						pickCharacters();
					}
				} 
				else {
					decisionLabel.setText((playerCounter + 1) + ", please enter a name before clicking enter.");
				}
			}
			
			else if(e.getSource() == rollDice){
				rollDice();
			}
			
			else if(e.getSource() == startGame){
				controller.startGame();
			}
			
			else if(e.getSource() == suggest){
				constructSuggestion();
			}
			
			else if(e.getSource() == accuse){
				int input = JOptionPane.showConfirmDialog(null, "Making an incorrect accusation loses you the game.\n"
						+ "Are you sure you want to make an accusation?", "Are you sure", JOptionPane.OK_CANCEL_OPTION);
				if(input == 0){
					constructAccusation();
				}
			}

		}
	}
	
	/**
	 * A listener for the keyboard, responds to WASD keys, and accuse / suggest shortcuts
	 */
	private class JKeyListener implements KeyListener{

		/**
		 * Responds to the keyListener when WASD are pressed
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				controller.doMoveIfValid(Direction.UP);
			} 
			else if (e.getKeyCode() == KeyEvent.VK_S) {
				controller.doMoveIfValid(Direction.DOWN);
			} 
			else if (e.getKeyCode() == KeyEvent.VK_A) {
				controller.doMoveIfValid(Direction.LEFT);
			} 
			else if (e.getKeyCode() == KeyEvent.VK_D) {
				controller.doMoveIfValid(Direction.RIGHT);
			}
			else if (e.getKeyCode() == KeyEvent.VK_G){
				if(accuse.isVisible() ){
					constructAccusation();
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_F){
				if(suggest.isVisible() ){
					constructSuggestion();
				}
			}
			validate();
		}
		
		// Unimplemented methods required for compilation
		@Override
		public void keyPressed(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
		
	}
	
	/**
	 * An implementation of KeyListener, used to detect short cuts used by the user
	 * when buttonPanel does not have focus.
	 */
	private class ShortCutListener implements KeyListener{

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_G){
				if(accuse.isVisible() ){
					int input = JOptionPane.showConfirmDialog(null, "Making an incorrect accusation loses you the game.\n"
							+ "Are you sure you want to make an accusation?", "Are you sure", JOptionPane.OK_CANCEL_OPTION);
					if(input == 0){
						constructAccusation();
					}
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_F){
				if(suggest.isVisible() ){
					constructSuggestion();
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_R){
				if(rollDice.isVisible() ){
					rollDice();
				}
			}
		}
		
		@Override
		public void keyPressed(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
		
	}
	
	/**
	 * An implementation of MouseListener.
	 * Used when a user clicks on the Board, will pop-up with 
	 * a message describing what they clicked on
	 */
	private class JMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = (e.getY() - 10 ) / 15;
			int col = (e.getX()  - 10 ) / 15;
			
			if(row < 0 || row > 24 || col < 0 || col > 24){return;}
			
			BoardPiece clickedOn = controller.getBoard().getBoard()[row][col];
			String start = "That is an ";
			String descrip = "out of bounds area";
			if(clickedOn instanceof Player){
				descrip = ((Player) clickedOn).getName();
				start = "That is ";
			}
			else if(clickedOn instanceof RoomTile){
				if(((RoomTile) clickedOn).isDoor() ){
					start = "That door belongs to ";
					descrip = ((RoomTile) clickedOn).getName().toString();
				}
				else{
					start = "That is a ";
					descrip = ((RoomTile) clickedOn).getName().toString() + " tile";
				}
			}
			else if(clickedOn instanceof Hallway){
				start = "That is a ";
				descrip = "Hallway";
			}
			JOptionPane.showMessageDialog(null, start + descrip, "You clicked", JOptionPane.INFORMATION_MESSAGE);
			
		}

		// Unimplemented methods required for compilation
		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	
	/**
	 *	A listener for the GuessWindow, responds to the window closing
	 */
	private class JWindowListener implements WindowListener{

		/**
		 * Responds to a user closing their suggestion window, either intentionally
		 * by completing their suggestion.
		 * Or unintentionally, in which they will void their suggestion and the game
		 * will skip to the next player's turn
		 */
		@Override
		public void windowClosed(WindowEvent e) {
			drawBoard();
			controller.setFocus(controller.getNextPlayer() );
			takeTurn();
		}

		// These are all unimplemented methods that are required for compilation
		@Override
		public void windowActivated(WindowEvent e) {}
		
		@Override
		public void windowClosing(WindowEvent e) {}

		@Override
		public void windowDeactivated(WindowEvent e) {}

		@Override
		public void windowDeiconified(WindowEvent e) {}

		@Override
		public void windowIconified(WindowEvent e) {}

		@Override
		public void windowOpened(WindowEvent e) {}
	}
}
