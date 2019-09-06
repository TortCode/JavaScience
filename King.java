
public class King extends ChessPiece {

	public King(String im, boolean tm, Square lc) {
		super(im, tm, lc);
	}

	public boolean isMoveLegal(Square dest) {
		int dr = rowDiff(dest);
		int dc = colDiff(dest);
		if(hasFriend(dest))
			return false;
		return Math.abs(dr) <= 1 && Math.abs(dc) <= 1;
	}

	public String pieceName() {
		return "King";
	}

}
