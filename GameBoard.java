import java.awt.*;
import javax.swing.*;

public class GameBoard extends JFrame {
	private static final int ROWS = 8, COLS = 8;
	private static boolean turn = false;
	// you'll need a 2d array
	public static Square[][] squares = new Square[ROWS][COLS];
	// you'll need variables to keep track of the 1st and 2nd squares that were
	// clicked
	private static Square orig, dest;
	public static ChessPiece dump;
	// critical pieces
	private static King wKing, bKing;

	public GameBoard() {
		super("CHESS");

		this.setLayout(new GridLayout(ROWS, COLS));
		boolean black = false;

		// now you'll need to birth each element of the array AND add it to the Frame
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLS; j++) {
				black = (i + j) % 2 == 1;
				squares[i][j] = new Square(i, j, this, black);
				this.add(squares[i][j]);
			}
		// Pawn
		for (int j = 0; j < COLS; j++)
			new Pawn("bPawn.png", true, squares[1][j]);
		for (int j = 0; j < COLS; j++)
			new Pawn("wPawn.png", false, squares[ROWS - 2][j]);
		// Rooks
		new Rook("bRook.png", true, squares[0][0]);
		new Rook("bRook.png", true, squares[0][COLS - 1]);
		new Rook("wRook.png", false, squares[ROWS - 1][0]);
		new Rook("wRook.png", false, squares[ROWS - 1][COLS - 1]);
		// Knights
		new Knight("bKnight.png", true, squares[0][1]);
		new Knight("bKnight.png", true, squares[0][COLS - 1 - 1]);
		new Knight("wKnight.png", false, squares[ROWS - 1][1]);
		new Knight("wKnight.png", false, squares[ROWS - 1][COLS - 1 - 1]);
		// Bishops
		new Bishop("bBishop.png", true, squares[0][2]);
		new Bishop("bBishop.png", true, squares[0][COLS - 1 - 2]);
		new Bishop("wBishop.png", false, squares[ROWS - 1][2]);
		new Bishop("wBishop.png", false, squares[ROWS - 1][COLS - 1 - 2]);
		// Royals
		new Queen("bQueen.png", true, squares[0][3]);
		bKing = new King("bKing.png", true, squares[0][4]);
		new Queen("wQueen.png", false, squares[ROWS - 1][3]);
		wKing = new King("wKing.png", false, squares[ROWS - 1][4]);
		// some finishing touches
		this.setSize(750, 750);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	// one of the squares will call this function to tell the board it was clicked
	public void clicked(Square sq) {
		if (orig == null && sq.getPiece() != null) {
			if (sq.getPiece().getTeam() == turn) {
				orig = sq;
				orig.setPieceHighlight(true);
				ChessPiece piece = orig.getPiece();
				for (int r = 0; r < ROWS; r++)
					for (int c = 0; c < COLS; c++)
						if (piece.isMoveLegal(squares[r][c]))
							squares[r][c].setHighlight(true);
			}
		} else if (orig != null && dest == null) {
			dest = sq;

			ChessPiece piece = orig.getPiece();
			if (piece.isMoveLegal(dest)) {
				piece.move(dest);
				JOptionPane.showMessageDialog(this, piece.pieceName() + " from " + orig + " to " + dest, "MOVE",
						JOptionPane.INFORMATION_MESSAGE);
				turn = !turn;
				if (checked((turn) ? bKing : wKing))
					JOptionPane.showMessageDialog(this, "Check for " + ((turn) ? "Black" : "White"), "CHECK",
							JOptionPane.INFORMATION_MESSAGE);
			}
			// reset highlight
			orig.setPieceHighlight(false);
			for (int r = 0; r < ROWS; r++)
				for (int c = 0; c < COLS; c++)
					squares[r][c].setHighlight(false);
			orig = null;
			dest = null;
		}
	}

	public boolean checked(King k) {
		Square sq = k.getSquare();
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++) {
				ChessPiece piece = squares[r][c].getPiece();
				if (piece != null && piece.isMoveLegal(sq))
					return true;
			}
		return false;
	}
	
	public void undo() {
		ChessPiece piece = dest.getPiece();
		orig.setPiece(piece);
		piece.setSquare(orig);
		dest.setPiece(dump);
	}

	// lame main
	public static void main(String[] args) {
		new GameBoard();
	}

}
