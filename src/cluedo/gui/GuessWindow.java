package cluedo.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cluedo.cards.Card;
import cluedo.cards.Weapon;
import cluedo.controller.CluedoController;
import cluedo.game.Solution;
import cluedo.cards.Character;
import cluedo.cards.Room;

/**
 * A specific JFrame that is used to help the user declare suggestions and accusations
 */
public class GuessWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
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
	
	// Panels
	JPanel selectionPanel;
		
	// Buttons
	JButton candlestick, rope, dagger, leadpipe, revolver, wrench;
	JButton red, yellow, blue, green, purple, white;
	JButton lounge, study, kitchen, library, conservatory, dining, hall, ballroom, billiard;
	JButton noted;
	JButton ok;
	
	// Listeners
	JButtonListener weaponListen, roomListen, characterListen, notedListen, okListen;
	
	// Variables to hold user selections
	Weapon weaponChoice;
	Character characterChoice;
	Room roomChoice;
	
	// Controller for the game
	CluedoController controller;
	
	// Is this window for an accusation? Or a suggestion?
	boolean isSuggestion;
	
	/**
	 * Constructor for a GuessWindow
	 * @param text - The tile for the window
	 * @param controller - The controller for the game
	 */
	public GuessWindow(String text, CluedoController controller){
		this.controller = controller;
		
		// Add elements to the JFrame
		this.setTitle(text);
		selectionPanel = new JPanel(new GridLayout(2, 3) );
		selectionPanel.setPreferredSize(new Dimension(300, 300) );
		add(selectionPanel); 
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);
	}
	
	/**
	 * starts the chain of method calls to guide the user through their
	 * declaration of their suggestion or accusation 
	 * 
	 * @param suggestion - Is this window for a suggestion
	 */
	public void startChoices(boolean suggestion){
		isSuggestion = suggestion;
		showWeaponButtons();
	}
	
	/**
	 * Displays the weapon buttons to the user
	 */
	public void showWeaponButtons(){
		candlestick = new JButton(candlestickIcon);
		rope = new JButton(ropeIcon);
		dagger = new JButton(daggerIcon);
		leadpipe = new JButton(leadpipeIcon);
		revolver = new JButton(revolverIcon);
		wrench = new JButton(wrenchIcon );
		
		weaponListen = new JButtonListener();
		candlestick.addActionListener(weaponListen);
		rope.addActionListener(weaponListen);
		dagger.addActionListener(weaponListen);
		leadpipe.addActionListener(weaponListen);
		revolver.addActionListener(weaponListen);
		wrench.addActionListener(weaponListen);
		
		selectionPanel.add(candlestick);
		selectionPanel.add(rope);
		selectionPanel.add(dagger);
		selectionPanel.add(leadpipe);
		selectionPanel.add(revolver);
		selectionPanel.add(wrench);
	}
	
	/**
	 * Displays the character buttons to the user
	 */
	public void showCharacterButtons(){
		selectionPanel.removeAll();
		selectionPanel.setPreferredSize(new Dimension(560, 435) );
		
		red = new JButton(redIcon);
		yellow = new JButton(yellowIcon);
		blue = new JButton(blueIcon);
		green = new JButton(greenIcon);
		purple = new JButton(purpleIcon);
		white = new JButton(whiteIcon );
		
		characterListen = new JButtonListener();
		red.addActionListener(characterListen);
		yellow.addActionListener(characterListen);
		blue.addActionListener(characterListen);
		green.addActionListener(characterListen);
		purple.addActionListener(characterListen);
		white.addActionListener(characterListen);
		
		selectionPanel.add(red);
		selectionPanel.add(yellow);
		selectionPanel.add(blue);
		selectionPanel.add(green);
		selectionPanel.add(purple);
		selectionPanel.add(white);
		
		selectionPanel.validate();
		selectionPanel.repaint();
	}
	
	/**
	 * Displays the room buttons to the user
	 */
	public void showRoomButtons(){
		selectionPanel.setPreferredSize(new Dimension(650, 500) );
		selectionPanel.removeAll();
		selectionPanel.setLayout(new GridLayout(2, 5));
		
		lounge = new JButton(loungeIcon);
		study = new JButton(studyIcon);
		kitchen = new JButton(kitchenIcon);
		library = new JButton(libraryIcon);
		conservatory = new JButton(conservatoryIcon);
		dining = new JButton(diningIcon);
		hall = new JButton(hallIcon);
		ballroom = new JButton(ballroomIcon);
		billiard = new JButton(billiardIcon);
		
		roomListen = new JButtonListener();
		lounge.addActionListener(roomListen);
		study.addActionListener(roomListen);
		kitchen.addActionListener(roomListen);
		library.addActionListener(roomListen);
		conservatory.addActionListener(roomListen);
		dining.addActionListener(roomListen);
		hall.addActionListener(roomListen);
		ballroom.addActionListener(roomListen);
		billiard.addActionListener(roomListen);
		
		selectionPanel.add(lounge);
		selectionPanel.add(study);
		selectionPanel.add(kitchen);
		selectionPanel.add(library);
		selectionPanel.add(conservatory);
		selectionPanel.add(dining);
		selectionPanel.add(hall);
		selectionPanel.add(ballroom);
		selectionPanel.add(billiard);
		
		selectionPanel.validate();
		selectionPanel.repaint();
	}
	
	/**
	 * Sets the chosen weapon
	 * @param weapon - the weapon chosen
	 */
	public void setWeapon(Weapon weapon){
		weaponChoice = weapon;
		showCharacterButtons();
	}
	
	public void setCharacter(Character character){
		characterChoice = character;
		if(isSuggestion){
			displaySuggestionResult();
		}
		else{
			showRoomButtons();
		}
	}
	
	/**
	 * Sets the chosen room
	 * @param room - The chosen room
	 */
	public void setRoom(Room room){
		roomChoice = room;
		displayAccusationResult();
	}
	
	/**
	 * Displays to the user the result of their suggestion
	 */
	public void displaySuggestionResult(){
		roomChoice = new Room(controller.getRoom() );
		
		Solution suggestion = new Solution(weaponChoice, characterChoice, roomChoice);
		Card result = controller.makeSuggestion(suggestion);
		
		if(result != null){ // The suggestion was good 
			setTitle("Successful suggestion!");
			selectionPanel.removeAll();
			selectionPanel.setPreferredSize(new Dimension(350, 200) );
			selectionPanel.setLayout(new FlowLayout() );
			JLabel text = new JLabel("Your suggestion worked! A player reveals their card.");
			selectionPanel.add(text);
			
			JLabel card = new JLabel();
			card.setIcon(result.getImageIcon() );
			selectionPanel.add(card);
			
			noted = new JButton("Got it");
			selectionPanel.add(noted);
			notedListen = new JButtonListener();
			noted.addActionListener(notedListen);
			selectionPanel.validate();
			
			controller.movePlayerToRoom(characterChoice, roomChoice.name);
			pack();
		}
		else{ // The suggestion yielded no fruit
			setTitle("Interesting...");
			selectionPanel.removeAll();
			selectionPanel.setPreferredSize(new Dimension(300, 100) );
			selectionPanel.setLayout(new FlowLayout() );
			JLabel text = new JLabel("The other players do not have any of those cards.");
			selectionPanel.add(text);
			selectionPanel.validate();
			
			controller.movePlayerToRoom(characterChoice, roomChoice.name);
		}
	}
	
	/**
	 * Displays to the user the result of their accusation
	 */
	public void displayAccusationResult(){
		Solution suggestion = new Solution(weaponChoice, characterChoice, roomChoice);
		boolean correct = controller.makeAccusation(suggestion);
		if(correct){
			selectionPanel.setPreferredSize(new Dimension(250, 100));
			selectionPanel.removeAll();
			setTitle("Winner!");
			selectionPanel.add(new JLabel("Congratulations " + controller.getFocus().getName() + ", you won the game!"));
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			selectionPanel.validate();
			selectionPanel.repaint();
			pack();
		}
		else{
			selectionPanel.removeAll();
			selectionPanel.setLayout(new FlowLayout() );
			selectionPanel.add(new JLabel("That accusation was incorrect.\nYou have lost the game.") );
			ok = new JButton("OK");
			okListen = new JButtonListener();
			ok.addActionListener(okListen);
			selectionPanel.add(ok);
			selectionPanel.validate();
			selectionPanel.repaint();
		}
	}
	
	// ============================================================================================================================
	//                                          LISTENER IMPLEMENTATION
	// ============================================================================================================================
	
	/**
	 * Listener for the JButtons
	 */
	private class JButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == candlestick){
				setWeapon(new Weapon(Weapon.Type.CANDLESTICK) );
			}
			else if(e.getSource() == rope){
				setWeapon(new Weapon(Weapon.Type.ROPE) );
			}
			else if(e.getSource() == dagger){
				setWeapon(new Weapon(Weapon.Type.DAGGER) );
			}
			else if(e.getSource() == leadpipe){
				setWeapon(new Weapon(Weapon.Type.LEADPIPE) );
			}
			else if(e.getSource() == revolver){
				setWeapon(new Weapon(Weapon.Type.REVOLVER) );
			}
			else if(e.getSource() == wrench){
				setWeapon(new Weapon(Weapon.Type.SPANNER) );
			}
			
			
			if(e.getSource() == red){
				setCharacter(new Character(Character.Colour.RED) );
			}
			else if(e.getSource() == yellow){
				setCharacter(new Character(Character.Colour.YELLOW) );
			}
			else if(e.getSource() == blue){
				setCharacter(new Character(Character.Colour.BLUE) );
			}
			else if(e.getSource() == green){
				setCharacter(new Character(Character.Colour.GREEN) );
			}
			else if(e.getSource() == purple){
				setCharacter(new Character(Character.Colour.PURPLE) );
			}
			else if(e.getSource() == white){
				setCharacter(new Character(Character.Colour.WHITE) );
			}
			
			if(e.getSource() == lounge){
				setRoom(new Room(Room.Name.LOUNGE) );
			}
			else if(e.getSource() == study){
				setRoom(new Room(Room.Name.STUDY) );
			}
			else if(e.getSource() == kitchen){
				setRoom(new Room(Room.Name.KITCHEN) );
			}
			else if(e.getSource() == library){
				setRoom(new Room(Room.Name.LIBRARY) );
			}
			else if(e.getSource() == conservatory){
				setRoom(new Room(Room.Name.CONSERVATORY) );
			}
			else if(e.getSource() == dining){
				setRoom(new Room(Room.Name.DININGROOM) );
			}
			else if(e.getSource() == hall){
				setRoom(new Room(Room.Name.HALL) );
			}
			else if(e.getSource() == ballroom){
				setRoom(new Room(Room.Name.BALLROOM) );
			}
			else if(e.getSource() == billiard){
				setRoom(new Room(Room.Name.BILLIARD) );
			}
			
			if(e.getSource() == noted){
				dispose();
			}
			
			if(e.getSource() == ok){
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				dispose();
			}
		}
		
	}
}