import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

public abstract class ChessPiece {
	private Image img;
	// team true for black, false for white
	private boolean team;
	boolean firstMove = true;
	Square location;

	public ChessPiece(boolean tm, Square lc) {
		loadImage("images/" + (tm ? "b" : "w") + this.pieceName() + ".png");
		team = tm;
		location = lc;
		lc.setPiece(this);
	}
	private void loadImage(String im) {
		img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(im));

		MediaTracker tracker = new MediaTracker(new Component() {});
		tracker.addImage(img, 0);
		// block while reading image
		try {
			tracker.waitForID(0);
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, "Error reading file");
		}
	}
	public boolean getTeam() {
		return team;
	}
	public void draw(Graphics g) {
		g.drawImage(img, 0, 0, 125, 125, null, null);
	}
	// movement
	public void move(Square dest) {
		location.setPiece(null);
		location = dest;
		ChessPiece cp = dest.getPiece();
		if (cp != null)
			GameBoard.dump = cp;
		location.setPiece(this);
		firstMove = false;
	}
	public void undoTest(Square src, Square dest) {
		src.setPiece(this);
		this.location = src;
		dest.setPiece(null);
	}

	// utility
	public boolean underAttack() {
		ChessPiece cp;
		for (int r = 0; r < GameBoard.ROWS; r++) {
			for (int c = 0; c < GameBoard.COLS; c++) {
				cp = GameBoard.squares[r][c].getPiece();
				if (cp != null && cp.isMoveLegal(this.location)) {
					// ensure flags deasserted
					GameBoard.ENPASSANT = false;
					GameBoard.CASTLING = false;
					return true;
				}
			}
		}
		return false;
	}

	// path must be vertical, horizontal, diagonal
	public boolean isPathClear(Square dest, boolean captureAllowed) {
		int r = location.getRow(), c = location.getCol();
		int endr = dest.getRow(), endc = dest.getCol();
		int rdir = (int) Math.signum(endr - r),
				cdir = (int) Math.signum(endc - c);
		r += rdir;
		c += cdir;
		while (r != endr || c != endc) {
			if (GameBoard.squares[r][c].getPiece() != null)
				return false;
			r += rdir;
			c += cdir;
		}
		if (dest.getPiece() != null)
			return captureAllowed && dest.getPiece().team != this.team;
		return true;
	}
	public boolean hasFriend(Square dest) {
		ChessPiece cp = dest.getPiece();
		if (cp != null && cp.team == this.team)
			return true;
		return false;
	}
	public boolean hasEnemy(Square dest) {
		ChessPiece cp = dest.getPiece();
		if (cp != null && cp.team != this.team)
			return true;
		return false;
	}
	public int rowDiff(Square dest) {
		return dest.getRow() - location.getRow();
	}
	public int colDiff(Square dest) {
		return dest.getCol() - location.getCol();
	}

	public abstract boolean isMoveLegal(Square dest);

	public abstract String pieceName();
}
