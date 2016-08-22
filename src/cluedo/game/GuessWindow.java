package cluedo.game;

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
import cluedo.cards.Character;
import cluedo.cards.Room;

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
	JButton exit;
	
	// Listeners
	JButtonListener weaponListen, roomListen, characterListen, notedListen, exitListen;
	
	// Variables to hold user selections
	Weapon weaponChoice;
	Character characterChoice;
	Room roomChoice;
	
	// Controller for the game
	CluedoController controller;
	
	// Is this window for an accusation? Or a suggestion?
	boolean isSuggestion;
	
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
	
	public void startChoices(boolean suggestion){
		isSuggestion = suggestion;
		showWeaponButtons();
	}
	
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
	
	public void showRoomButtons(){
		selectionPanel.removeAll();
		selectionPanel.setPreferredSize(new Dimension(560, 435) );
		
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
		lounge.addActionListener(characterListen);
		study.addActionListener(characterListen);
		kitchen.addActionListener(characterListen);
		library.addActionListener(characterListen);
		conservatory.addActionListener(characterListen);
		dining.addActionListener(characterListen);
		hall.addActionListener(characterListen);
		ballroom.addActionListener(characterListen);
		billiard.addActionListener(characterListen);
		
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
	
	public void setRoom(Room room){
		roomChoice = room;
		displayAccusationResult();
	}
	
	public void displaySuggestionResult(){
		roomChoice = new Room(controller.getRoom() );
		System.out.println(roomChoice);
		
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
			pack();
		}
	}
	
	public void displayAccusationResult(){
		Solution suggestion = new Solution(weaponChoice, characterChoice, roomChoice);
		boolean correct = controller.makeAccusation(suggestion);
		if(correct){
			selectionPanel.removeAll();
			selectionPanel.add(new JLabel("Congratulations " + controller.getFocus().getName() + ", you won the game!"));
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		else{
			selectionPanel.removeAll();
			selectionPanel.add(new JLabel("That accusation was incorrect, you have lost the game.\n Better luck next time!") );
			exit = new JButton("OK");
			exitListen = new JButtonListener();
		}
	}
	
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
				setCharacter(new Character(Character.Colour.RED) );
			}
			else if(e.getSource() == study){
				setCharacter(new Character(Character.Colour.YELLOW) );
			}
			else if(e.getSource() == kitchen){
				setCharacter(new Character(Character.Colour.BLUE) );
			}
			else if(e.getSource() == library){
				setCharacter(new Character(Character.Colour.GREEN) );
			}
			else if(e.getSource() == conservatory){
				setCharacter(new Character(Character.Colour.PURPLE) );
			}
			else if(e.getSource() == dining){
				setCharacter(new Character(Character.Colour.WHITE) );
			}
			else if(e.getSource() == hall){
				setCharacter(new Character(Character.Colour.GREEN) );
			}
			else if(e.getSource() == ballroom){
				setCharacter(new Character(Character.Colour.PURPLE) );
			}
			else if(e.getSource() == billiard){
				setCharacter(new Character(Character.Colour.WHITE) );
			}
			
			if(e.getSource() == noted){
				dispose();
			}
		}
		
	}
}