import java.awt.event.*;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.*;
import java.util.Random;

public class GameWindow extends JFrame implements ActionListener{
	/**
	 * 
	 */
	// not very important for this project
	// basically if I was saving data in an external file outside of this project, then I would need this
	// however I am not, but...
	// not having this will produce a warning...I only generated it to get rid of that....
	private static final long serialVersionUID = 1L;
	// globals
	JButton buttons[];									// Create the buttons in an array
	JLabel instructionsLabel, scoreLabel, timerLabel;				// create a label for instructions and the hud
	JPanel panel;										// The panel for the buttons - is global since we want to change the order of the buttons
	private int buttonClicked = -1;						// check which button is clicked. -1 = no button
	private int buttonOrder[] = {0, 1, 2, 3, 4};		// an array holding the indices of the buttons array - used to change the order of the buttons
	
	// accessors
	int getButtonClicked() { return buttonClicked; }
	
	// mutators
	void setButtonClicked(int i) {
		buttonClicked = i;
	}
	
	void setInstructionsLabel(String s) {
		instructionsLabel.setText(s);
	}
	
	void setScoreLabel(int score) {
		scoreLabel.setText("Score: " + score);
	}
	
	void setTimerLabel(int time) {
		timerLabel.setText("Time Remaining: " + time);
	}
	
	// constructor
	GameWindow(String title){
		 
		super(title);
		// center the window
		this.setLocationRelativeTo(null);
		this.init();
		this.pack();
		this.setVisible(true);
	}
	
	// initialize
	void init() {
		// ask if the player wants to the play game at first
		int start = playGame();
		if(start == 1) {
			System.exit(0);
		}
		// this can be set to anything except empty
		// the reason it can be anything is because we overwrite it immediately at the start of our game
		// the reason it cannot be empty is because if it is, the JLabel will have a font size of 0, so if we update it, we will see only the tops
		// of the letters
		instructionsLabel = new JLabel("T");
		
		// make a panel for the instructions
		JPanel instructionsPanel = new JPanel();
		instructionsPanel.add(instructionsLabel);
		
		// set the default score and time
		scoreLabel = new JLabel("Score: 0");
		timerLabel = new JLabel("Time Remaining: 60");
		
		// make a panel for the HUD
		JPanel hudPanel = new JPanel();
		hudPanel.add(scoreLabel);
		hudPanel.add(timerLabel);
		
		// add the buttons with the text
		// by default: 0 = red, 1 = green, 2 = blue, 3 = yellow, 4 = magenta
		buttons = new JButton[5];
		buttons[0] = new JButton("Red");
		buttons[1] = new JButton("Green");
		buttons[2] = new JButton("Blue");
		buttons[3] = new JButton("Yellow");
		buttons[4] = new JButton("Magenta");
		
		// set the buttons' font colors
		setDefaultButtonColors();
		
		// set the background of every button to gray
		// add an ActionListener to each button
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].setBackground(Color.gray);
			buttons[i].addActionListener(this);
		}
		
		// instantiate our panel for our buttons
		panel = new JPanel();
		
		// put the buttons in the panel in default order
		for(int i = 0; i < buttons.length; i++) {
			panel.add(buttons[buttonOrder[i]]);
		}
		
		// add the HUD to the north, the instructions in the center, and the buttons in the south
		this.add(hudPanel, BorderLayout.NORTH);
		this.add(instructionsPanel, BorderLayout.CENTER);
		this.add(panel, BorderLayout.SOUTH);
	}
	
	// used to ask if the player wants to play
	// also gives the players the instructions
	// returns 0 if yes and 1 if no
	int playGame() {
		return JOptionPane.showConfirmDialog(this, "To play, simply follow the instructions and click the correct buttons.\n"
				+ "You always click the button with the correct text.\nWould you like to play?", "Start Game", JOptionPane.YES_NO_OPTION); 
	}
	
	// Implementing Fisher–Yates shuffle to shuffle the button order
	void shuffleButtonOrderArray() {
		Random rnd = new Random();
		for (int i = buttonOrder.length - 1; i > 0; i--)
		{
			// get a random index to swap with - it may stay in the same place
			int index = rnd.nextInt(i + 1);
			// swap the two items in the array
			int temp = buttonOrder[index];
			buttonOrder[index] = buttonOrder[i];
			buttonOrder[i] = temp;
		}
	}
	
	// the following method is not used
	/*
	void resetButtonOrderArray() {
		for(int i = 0; i < buttonOrder.length; i++) {
			buttonOrder[i] = i;
		}
	}
	*/
	
	// sets the default button colors
	// 0 = red, 1 = green, 2 = blue, 3 = yellow, 4 = magenta
	void setDefaultButtonColors() {
		buttons[0].setForeground(Color.red);
		buttons[1].setForeground(Color.green);
		buttons[2].setForeground(Color.blue);
		buttons[3].setForeground(Color.yellow);
		buttons[4].setForeground(Color.magenta);
	}
	
	// PUBLIC METHODS
	
	// shuffle the colors of the buttons
	public void shuffleButtonColor() {
		// shuffle the order of the buttons to randomize the colors
		// the reason I'm using the buttonOrder array is so that I still only have one of each color
		shuffleButtonOrderArray();
		// set the colors of the buttons based on the data in the buttonOrder array
		buttons[buttonOrder[0]].setForeground(Color.red);
		buttons[buttonOrder[1]].setForeground(Color.green);
		buttons[buttonOrder[2]].setForeground(Color.blue);
		buttons[buttonOrder[3]].setForeground(Color.yellow);
		buttons[buttonOrder[4]].setForeground(Color.magenta);
	}
	
	// shuffles the order of the buttons
	public void shuffleButtonLocations() {
		// shuffle the buttonOrder array
		shuffleButtonOrderArray();
		// empty the button panel
		panel.removeAll();
		
		// add the buttons again, but in their new randomized order
		for(int i = 0; i < buttons.length; i++) {
			panel.add(buttons[buttonOrder[i]]);
		}
	}
	
	// sets the font color of all of the buttons to the color passed in
	public void setButtonFontColor(Color c) {
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].setForeground(c);
		}
	}
	
	// use a confirmDialog to ask if the player wants to play again
	// also outputs the users score, which we get from the main class
	public int playAgain(int score) {
		return JOptionPane.showConfirmDialog(this, "You got " + score + " points!\nWould you like to play again?", "Play again?", JOptionPane.YES_NO_OPTION); 
	}
	
	// runs if a button is clicked
	public void actionPerformed(ActionEvent e) {
		// set buttonClicked to the location of the button
		// 0 is the leftmost and 4 is the rightmost button
		if(e.getSource() == buttons[0]) {
			buttonClicked = 0;
		}
		else if(e.getSource() == buttons[1]) {
			buttonClicked = 1;
		}
		else if(e.getSource() == buttons[2]) {
			buttonClicked = 2;
		}
		else if(e.getSource() == buttons[3]) {
			buttonClicked = 3;
		}
		else if(e.getSource() == buttons[4]) {
			buttonClicked = 4;
		}
	}
}
