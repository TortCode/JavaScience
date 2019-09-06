
public class Pawn extends ChessPiece {

	public Pawn(String im, boolean tm, Square lc) {
		super(im, tm, lc);

	}

	public boolean isMoveLegal(Square dest) {
		int dir = (getTeam()) ? 1 : -1; // move down for black, up for white
		int dr = rowDiff(dest), dc = colDiff(dest);

		if (dr == dir * 1 && dc == 0) {
			return dest.getPiece() == null;
		}
		if (dr == dir * 1 && Math.abs(dc) == 1)
			return hasEnemy(dest);
		if (firstMove && dr == dir * 2 && dc == 0)
			return isPathClear(dest, false);

		return false;
	}

	public String pieceName() {
		return "Pawn";
	}

}
