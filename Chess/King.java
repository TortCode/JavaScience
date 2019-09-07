
public class King extends ChessPiece {

	public King(boolean tm, Square lc) {
		super(tm, lc);
	}

	public boolean isMoveLegal(Square dest) {
		int dr = rowDiff(dest);
		int dc = colDiff(dest);
		if (Math.abs(dr) <= 1 && Math.abs(dc) <= 1)
			return isPathClear(dest, true);
		// castling
		if (firstMove && dr == 0 && Math.abs(dc) == 2) {
			ChessPiece rook = castlingRookSquare(dest).getPiece();
			if (rook instanceof Rook && rook.firstMove) {
				Square middle = castlingMiddleSquare(dest);
				// test if passing from, thru, into check
				if (this.isPathClear(dest, false)
						&& rook.isPathClear(middle, false)) {
					boolean legal = true;
					Square orig = this.location;
					if (this.underAttack())
						legal = false;
					move(middle);
					if (this.underAttack())
						legal = false;
					undoTest(orig, middle);
					move(dest);
					if (this.underAttack())
						legal = false;
					undoTest(orig, dest);
					firstMove = true; // ensure no move done yet
					if (legal)
						GameBoard.CASTLING = true;
					return legal;
				}
			}
		}
		return false;
	}
	public Square castlingMiddleSquare(Square dest) {
		return GameBoard.squares[location
				.getRow()][(location.getCol() + dest.getCol()) / 2];
	}
	public Square castlingRookSquare(Square dest) {
		int dc = colDiff(dest);
		return GameBoard.squares[location.getRow()][(dc > 0)
				? GameBoard.COLS - 1
				: 0];
	}

	public void move(Square dest) {
		if (GameBoard.CASTLING) {
			Square middle = castlingMiddleSquare(dest);
			ChessPiece rook = castlingRookSquare(dest).getPiece();
			rook.move(middle);
			GameBoard.CASTLING = false;
		}
		super.move(dest);
	}

	public String pieceName() {
		return "King";
	}

}
