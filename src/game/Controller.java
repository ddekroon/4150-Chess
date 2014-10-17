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
				// TODO: pass rules to display menu
				view.DisplayMenu();
				
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
							if(input.equalsIgnoreCase("exit")) {
								view.DisplayExitMessage();
								System.exit(0);
							}
							
							int variation = Integer.parseInt(input);
							if (variation == 1) {
								game.rules = new ClassicChess();
							} else if (variation == 2) {
								//game.rules = new PeasantsRevoltChess();
							}  else if (variation == 3) {
								game.rules = new TakeAllChess();
							}  else if (variation == 4) {
								//game.rules = new TakeAllChess();
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
				view.DisplayTurnNotification(game.turnNumber, game.activePlayer.color);
				
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
							
							if(input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
								view.DisplaySurrenderMessage(game.activePlayer.color);
								game = null;
								break;
							}
							
							if(input.equalsIgnoreCase("exit")) {
								view.DisplayExitMessage();
								System.exit(0);
							}
							
							Move move = new Move(game.activePlayer, input, game.board);
							
							if (move.IsValid() && game.rules.ValidateMove(move, game.board)) {
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