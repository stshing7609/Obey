import javax.swing.*;
import java.util.Random;
import java.awt.Color;
import java.awt.event.*;

public class ObeyGame {
	// global variables
	// constants
	static final int MILLISECONDS_IN_SECOND = 1000;
	// the necessary score values before the game starts changing
	static final int START_BUTTON_SHUFFLE = 10;
	static final int START_FONT_WHITE = 20;
	static final int START_COLOR_SHUFFLE = 30;
	static final int START_FONT_GRAY = 50;
	
	static GameWindow gameWindow;	// an instance of the game window class
	static int timeLimit;			// how many seconds you have to play the game
	static Timer timer;				// the timer to update how much time you have left
	static boolean correct;			// true if you've clicked the right button, false if not - the game ends when this is false
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		startNewGame();
	}
	
	// creates an entirely new game with a new frame
	static void startNewGame() {
		gameWindow = new GameWindow("Obey!");
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game();
	}
	
	// our game loop
	static void game() {
		// set global variables
		timeLimit = 90; 										// the game will only last a minute and 30 seconds
		correct = true;											// you start off the game with the correct choice
		timer = new Timer(1000, new CountdownTimerListener());	// every 1000 milliseconds (1 second), update the timer
		int score = 0;											// you start with no points
		int buttonClicked = -1, targetButton = -1;				// the button you have clicked and the target both start at -1, meaning no buttons
		boolean newRound = true;								// start a new round
		Random rand = new Random();								// create a Random object to use
		timer.start();											// start the timer
		
		// the actual loop
		do {
			if(newRound) {
				// update the score
				gameWindow.setScoreLabel(score);
				
				// reset buttonClicked to 'no' button (-1)
				buttonClicked = -1;
				gameWindow.setButtonClicked(buttonClicked);
				
				// Randomly choose our next instruction
				targetButton = rand.nextInt(5);
				String newText = "";							// create an empty string to use in our switch statement
				// 0 = 'Red', 1 = 'Green', 2 = 'Blue', 3 = 'Yellow', 4 = 'Magenta'
				// You must always click the button with the text on it
				switch(targetButton) {
					case 0:
						newText = "Click the 'Red' button";
						break;
					case 1:
						newText = "Click the 'Green' button";
						break;
					case 2:
						newText = "Click the 'Blue' button";
						break;
					case 3:
						newText = "Click the 'Yellow' button";
						break;
					case 4:
						newText = "Click the 'Magenta' button";
						break;
					default:
						break;
				}
				// set our instructions
				gameWindow.setInstructionsLabel(newText);
				
				// if our score is greater than 10, start shuffle the order of the buttons
				if(score > START_BUTTON_SHUFFLE) {
					gameWindow.shuffleButtonLocations();
				}
				// if our score is equal to 20, set the font color to white
				// since we're not changing the font color later, this will be true until we change it next
				if(score == START_FONT_WHITE) {
					gameWindow.setButtonFontColor(Color.white);
				}
				// if our score is greater than 30 and less than 50, start changing the font color of the buttons
				if(score > START_COLOR_SHUFFLE && score < START_FONT_GRAY) {
					gameWindow.shuffleButtonColor();
				}
				// if our score is equal to 50, and for the same reason as changing the font color to white only once,
				// set the font color to gray (WHICH IS THE SAME AS THE BACKGROUND!)
				if(score == START_FONT_GRAY) {
					gameWindow.setButtonFontColor(Color.gray);
				}
				
				// we just started the round, so don't reset anything while we wait for user input
				newRound = false;
			}

			// get the current value of the button that's been clicked
			// -1 if no button has been clicked
			buttonClicked = gameWindow.getButtonClicked();
			
			// if we clicked a button
			if(buttonClicked > -1) {
				// check if the button is the correct one. if it is add to score and start a new round
				// if it isn't, the player loses
				if(buttonClicked == targetButton) {
					score++;
					newRound = true;
				}
				else {
					correct = false;
				}
			}
		} while(correct);
		
		// check if the player wants to play again
		int playAgainChoice = gameWindow.playAgain(score);
		// close the application is they say no
		if(playAgainChoice == 1) {
			System.exit(0);
		}
		// discard the current frame and start a new game
		else {
			gameWindow.dispose();
			startNewGame();
		}
	}
	
	// the ActionListener for the countdown
	static class CountdownTimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// if the time left is greater than 0, set the timerLabel in gameWindow to the accurate time
			if (timeLimit-- > 0) {
                gameWindow.setTimerLabel(timeLimit);
            } 
			// if the timeLimit is up, stop the timer and end the game by changing correct to false
            else {
                timer.stop();
                correct = false;
            }
		}
	}
	
}
