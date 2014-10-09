package game;
import java.io.*;

public class Controller {
	public View view = null;
	//public Rules rules = null;
	public Game game = null;
	
	public Controller(View view) {
		this.view = view;
	}
	
	public void play() {
		
		while (true) {
			
			if (game == null) {
				//start new game
				this.game = new Game();
			
			}
			
			if (game.rules == null) {
				//request rules
				//TODO: move this output to view
				System.out.println("What variation of Chess do you want to play?");
				System.out.println("1. Classic Chess");
				System.out.println("Enter the index of the variation.");
				
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String input = null;
				while (input == null) {
					try {
						 input = br.readLine();
					} catch (IOException e) {
						input = null;
						System.out.println("Input Error: Unable to read input.");
					}
					
					if (input != null) {
						try {
							int variation = Integer.parseInt(input);
							if (variation == 1) {

								game.rules = new ClassicChess();
							}
							else {
								input = null;
								System.out.println("Input Error: Not a valid variation index.");
							}
						}
						catch (Exception e)
						{
							input = null;
							System.out.println("Error loading variation.");
						}
					}
					
				}
				game.board = game.rules.SetStartingPositions(this.game.white, this.game.black);
			}
			
			if (game != null) {
				view.DisplayBoard(game.board);
				game.NextTurn();
				
				//TODO: move this output to view
				System.out.println("Turn #" + game.turnNumber + ": It's " + game.activePlayer.color + "'s turn.");
				System.out.println("Enter a move. (eg: a2-a3)");
				
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String input = null;
				while (input == null) {
					try {
						 input = br.readLine();
					} catch (IOException e) {
						input = null;
						System.out.println("Input Error: Unable to read input.");
					}
					
					if (input != null) {
						try {
							Move move = new Move(game.activePlayer, input, game.board);
							
							if (move.IsValid() && game.rules.ValidateMove(move)) {
								game.CompleteMove(move);
							}
							else {
								input = null;
								System.out.println("Input Error: Not a valid move.");
							}
						}
						catch (Exception e)
						{
							input = null;
							System.out.println("Input Error: Unable to parse input.");
						}
					}
					
				}
			}

		}
			
		
	}
	
}