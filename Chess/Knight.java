
public class Knight extends ChessPiece {

	public Knight(boolean tm, Square lc) {
		super(tm, lc);
	}

	public boolean isMoveLegal(Square dest, boolean testing) {
		int dr = rowDiff(dest);
		int dc = colDiff(dest);
		if (hasFriend(dest))
			return false;
		return Math.abs(dc) == 2 && Math.abs(dr) == 1
				|| Math.abs(dc) == 1 && Math.abs(dr) == 2;
	}

	public String pieceName() {
		return "Knight";
	}

}
