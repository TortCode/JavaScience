
public class Queen extends ChessPiece {

	public Queen(boolean tm, Square lc) {
		super(tm, lc);
	}

	public boolean isMoveLegal(Square dest, boolean testing) {
		int dr = rowDiff(dest);
		int dc = colDiff(dest);
		if (Math.abs(dr) == Math.abs(dc) || dr == 0 || dc == 0)
			return isPathClear(dest, true);
		return false;
	}

	public String pieceName() {
		return "Queen";
	}

}
