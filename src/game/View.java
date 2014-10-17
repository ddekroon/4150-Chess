package game;

public class View {
	
	
	public void DisplayMenu() {
		//TODO: get the rules to know which ones to display
		System.out.println("What variation of Chess do you want to play?");
		System.out.println("1. Classic Chess");
		System.out.println("2. Peasants Revolt Chess");
		System.out.println("3. Take All Chess");
		System.out.println("4. Testing Suite");
		System.out.println("Enter the index of the variation.");
	}
	
	public void DisplayBoard(Board gameBoard) {
		
		System.out.print("   A B C D E F G H" + "\n");
		
		for(int y = 0; y < gameBoard.height; y++) {
			System.out.print(y + "  ");
			for(int x = 0; x < gameBoard.width; x++) {
				if (gameBoard.tiles[x][y].piece == null) {
					System.out.print("- ");
				}
				else {
					System.out.print(gameBoard.tiles[x][y].piece.toString() + " ");
				}
			}
			System.out.print(" " + y + "\n");
		}
		
		System.out.print("   A B C D E F G H" + "\n" );
		
	}
	
	public void DisplayTurnNotification(int turnNumber, String color) {
		System.out.println("Turn #" + turnNumber + ": It's " + color + "'s turn.");
		System.out.println("Enter a move. (eg: a2-a3)");
	}
	
	public void DisplaySurrenderMessage(String surrenderingColor) {
		String winningColor = "White";
		if(surrenderingColor.equals("White")) {
			winningColor = "Black";
		}
		System.out.println(surrenderingColor + " surrendered. " + winningColor + " is the winner.");
	}
	
	public void DisplayExitMessage() {
		System.out.println("Thanks for playing!");
	}
}