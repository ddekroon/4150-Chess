package game;

public class Pawn extends Piece {
	public Pawn (Player owner) {
		super(owner);
	}

	public String toString() {
		if (owner.color.equals("White")) {
			return "P";
		}
		return "p";
		
	}
}