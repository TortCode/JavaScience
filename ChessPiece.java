import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JOptionPane;

public abstract class ChessPiece {
	private Image img;
	// variable for what team/color i am
	private boolean team;
	protected boolean firstMove = true;
	// variable for what Square I'm sitting on
	private Square location;

	// Constructor : you will need some more parameters!
	public ChessPiece(String im, boolean tm, Square lc) {
		loadImage(im);

		// set up your other variables
		team = tm;
		location = lc;
		lc.setPiece(this);

	}// end constructor

	// setters and getters
	public boolean getTeam() {
		return team;
	}
	
	public Square getSquare() {
		return location;
	}

	public void setSquare(Square lc) {
		location = lc;
	}

	// helper function for loading up your image
	private void loadImage(String im) {
		img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(im));

		MediaTracker tracker = new MediaTracker(new Component() {
		});
		tracker.addImage(img, 0);
		// block while reading image
		try {
			tracker.waitForID(0);
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, "Error reading file");
		}
	}// end loadImage

	public void draw(Graphics g) {
		g.drawImage(img, 0, 0, 90, 90, null, null);
	}

	public void move(Square dest) {
		location.setPiece(null);
		location = dest;
		GameBoard.dump = dest.getPiece();
		location.setPiece(this);
		firstMove = false;
	}

	public int rowDiff(Square dest) {
		return dest.getRow() - location.getRow();
	}

	public int colDiff(Square dest) {
		return dest.getCol() - location.getCol();
	}

	public int signum(int i) {
		if (i == 0)
			return 0;
		return Math.abs(i) / i;
	}

	public boolean isPathClear(Square dest, boolean captureAllowed) { // path must be vertical,horizontal,diagonal
		int r = location.getRow(), c = location.getCol();
		int endr = dest.getRow(), endc = dest.getCol();
		int dr = signum(endr - r), dc = signum(endc - c);
		r += dr;
		c += dc;
		while (r != endr || c != endc) {
			if (GameBoard.squares[r][c].getPiece() != null)
				return false;
			r += dr;
			c += dc;
		}
		if (dest.getPiece() != null)
			return captureAllowed && dest.getPiece().team != this.team;
		return true;
	}

	public boolean hasFriend(Square dest) {
		ChessPiece piece = dest.getPiece();
		if (piece != null && piece.team == this.team)
			return true;
		return false;
	}

	public boolean hasEnemy(Square dest) {
		ChessPiece piece = dest.getPiece();
		if (piece != null && piece.team != this.team)
			return true;
		return false;
	}

	public abstract boolean isMoveLegal(Square dest);

	public abstract String pieceName();
}
