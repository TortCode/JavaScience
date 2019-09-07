
public class Pawn extends ChessPiece {

	public Pawn(boolean tm, Square lc) {
		super(tm, lc);
	}

	public boolean isMoveLegal(Square dest) {
		int dir = getTeam() ? 1 : -1; // move down for black, up for white
		int dr = rowDiff(dest);
		int dc = colDiff(dest);
		if (dr == dir * 1 && dc == 0)
			return dest.getPiece() == null;
		if (dr == dir * 1 && Math.abs(dc) == 1) {
			// en passant check
			if (GameBoard.passable != null
					&& GameBoard.passable.location == enPassantSquare(dest)) {
				GameBoard.ENPASSANT = true;
				return true;
			}
			return hasEnemy(dest);
		}
		if (firstMove && dr == dir * 2 && dc == 0)
			return isPathClear(dest, false);
		return false;
	}

	private Square enPassantSquare(Square dest) {
		int dir = getTeam() ? 1 : -1;
		return GameBoard.squares[dest.getRow() - dir][dest.getCol()];
	}

	public void move(Square dest) {
		int dr = rowDiff(dest);
		if (Math.abs(dr) == 2)
			GameBoard.passable = this;
		if (GameBoard.ENPASSANT) {
			enPassantSquare(dest).setPiece(null);
			GameBoard.ENPASSANT = false;
		}
		super.move(dest);
	}

	public String pieceName() {
		return "Pawn";
	}

}
