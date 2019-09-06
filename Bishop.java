
public class Bishop extends ChessPiece {

	public Bishop(String im, boolean tm, Square lc) {
		super(im, tm, lc);
	}

	public boolean isMoveLegal(Square dest) {
		int dr = rowDiff(dest);
		int dc = colDiff(dest);
		if(Math.abs(dr) == Math.abs(dc))
			return isPathClear(dest,true);
		return false;
	}

	public String pieceName() {
		return "Bishop";
	}

}
