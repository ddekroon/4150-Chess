package game;

public class TakeAllChess extends Rules {
		public TakeAllChess() {
			super();
		}
		
		public Board SetStartingPositions(Player white, Player black) {
			
			Board board = new Board(8,8);
			
			board.tiles[0][1].addPiece(new Pawn(white));
			board.tiles[1][1].addPiece(new Pawn(white));
			board.tiles[2][1].addPiece(new Pawn(white));
			board.tiles[3][1].addPiece(new Pawn(white));
			board.tiles[4][1].addPiece(new Pawn(white));
			board.tiles[5][1].addPiece(new Pawn(white));
			board.tiles[6][1].addPiece(new Pawn(white));
			board.tiles[7][1].addPiece(new Pawn(white));
			board.tiles[3][0].addPiece(new Queen(white));
			
			board.tiles[0][6].addPiece(new Pawn(black));
			board.tiles[1][6].addPiece(new Pawn(black));
			board.tiles[2][6].addPiece(new Pawn(black));
			board.tiles[3][6].addPiece(new Pawn(black));
			board.tiles[4][6].addPiece(new Pawn(black));
			board.tiles[5][6].addPiece(new Pawn(black));
			board.tiles[6][6].addPiece(new Pawn(black));
			board.tiles[7][6].addPiece(new Pawn(black));
			board.tiles[3][7].addPiece(new Queen(black));
			
			return board;
		}
		
		
		// return true if it's a valid move, false if invalid
		public boolean validatePawnMove(Move move, Board board)
		{
			// get direction it can go in
			int direction = -1;
			
			// white on top
			if(move.activePlayer.color.equals("White")) {
				direction = 1;
			}
			
			int yDifference = move.endPosition.yCord - move.startPosition.yCord;
			int xDifference = Math.abs(move.endPosition.xCord - move.startPosition.xCord);
			if(yDifference == direction) {
				// pawn is moving straight by 1 space
				if(xDifference == 0) {
					// if there is no piece ahead, it's valid
					if(move.endPosition.piece == null) {
						return true;
					}
					else {
						return false;
					}
				}
				// pawn is moving diagonal by 1 space
				else if(xDifference == 1) {
					// can't move diagonal without a piece
					if(move.endPosition.piece == null) {
						//checking if the piece adjacent to the pawn moving was a pawn and had done a double jump the last turn (checking condition for En Passant)
						if(board.tiles[move.endPosition.xCord][move.startPosition.yCord].piece != null){
							if(board.tiles[move.endPosition.xCord][move.startPosition.yCord].piece.getClass() == Pawn.class){
								Pawn p = (Pawn)board.tiles[move.endPosition.xCord][move.startPosition.yCord].piece;
								if(p.lastMoveJump == true && p.owner != move.activePlayer){
									board.tiles[move.endPosition.xCord][move.startPosition.yCord].piece = null;
									return true;
								}
							}
						}
						return false;
					}
					// can only move diagonal if the piece is owned by the other player
					else if(move.endPosition.piece.owner != move.activePlayer) {
						return true;
					}
				}
			}
			else if(move.piece.hasMoved == false && yDifference == direction * 2) {
				// double jump for first move
				//checking that the final position is empty (does not contain a piece) and that the space in between is also empty.
				if(move.endPosition.piece == null && board.tiles[move.startPosition.xCord][move.startPosition.yCord + direction].piece == null) {
					return true;
				}
				else {
					return false;
				}
			}
			return false;
		}
		
		// return true if it's a valid move, false if invalid
		public boolean validateQueenMove(Move move, Board board)
		{
			// check what the move way is, horizontal or diagonal
			int xDiff = Math.abs(move.startPosition.xCord - move.endPosition.xCord);
			int yDiff = Math.abs(move.startPosition.yCord - move.endPosition.yCord);
			
			// if it attacks the same team
			if(move.endPosition.piece != null && move.endPosition.piece.owner == move.activePlayer) {
				return false;
			}
			
			// diagonal movement
			if(xDiff == yDiff) {
				int xDir = 1;
				int yDir = 1;
				
				// moving left
				if(move.startPosition.xCord - move.endPosition.xCord > 0) {
					xDir = -1;
				}
				// moving up
				if(move.startPosition.yCord - move.endPosition.yCord > 0) {
					yDir = -1;
				}
				
				for(int i=1; i < yDiff; i++ ) {
					Tile t = board.tiles[move.startPosition.xCord + (xDir * i)][move.startPosition.yCord + (yDir * i)];
					if(t.piece != null) {
						return false;
					}
				}
				return true;
			}
			// horizontal movement
			else if(xDiff == 0) {
				for(int i=1; i < xDiff; i++ ) {
					Tile t = board.tiles[move.startPosition.xCord + i][move.startPosition.yCord];
					if(t.piece != null) {
						return false;
					}
				}
				return true;
			}
			// vertical movement
			else if(yDiff == 0) {
				for(int i=1; i < yDiff; i++ ) {
					Tile t = board.tiles[move.startPosition.xCord][move.startPosition.yCord + i];
					if(t.piece != null) {
						return false;
					}
				}
				return true;
			}
			
			return false;
		}
		
		
		
		public boolean ValidateMove(Move move, Board board)
		{
			//TODO:	- ensure that the moving piece can legally get to the move end point.
			// 		- ensure that the requested move does not put the active player in check
			//		- return true if the move is valid, false otherwise
			
			if(move.piece.getClass() == Pawn.class) {
				return validatePawnMove(move, board);
			}
			else if(move.piece.getClass() == Queen.class) {
				return validateQueenMove(move, board);
			}
			
			
			return false;
		}
		
		public void setLastMoveJump(Player activePlayer, Board board){
			for(int y = 0; y < board.height; y++) {
				for(int x = 0; x < board.width; x++) {
					if (board.tiles[x][y].piece != null) {
						if(board.tiles[x][y].piece.getClass() == Pawn.class && board.tiles[x][y].piece.owner == activePlayer){
							Pawn p = (Pawn)board.tiles[x][y].piece;
							p.lastMoveJump = false;
						}
					}
				}
			}
		}
		
		public void ruleCompleteMove(Player activePlayer, Board board, Move move){
			setLastMoveJump(activePlayer, board);
			if(move.piece.getClass() == Pawn.class && Math.abs(move.startPosition.yCord - move.endPosition.yCord) == 2) {
				Pawn p = (Pawn)move.piece;
				p.lastMoveJump = true;
			}
		}
}
