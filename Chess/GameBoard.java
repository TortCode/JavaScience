
import java.awt.*;
import javax.swing.*;

public class GameBoard extends JFrame {
	public static final int ROWS = 8, COLS = 8;
	private static final String[] promoteOptions = {"Queen", "Knight", "Rook",
			"Bishop"};
	private static final int QUEEN = 0, KNIGHT = 1, ROOK = 2, BISHOP = 3;
	private static boolean turn = false;
	static Square[][] squares = new Square[ROWS][COLS];
	// move squares
	private static Square src, dest;
	// critical pieces
	private static King wKing, bKing;
	static Pawn passable;
	// last piece taken
	static ChessPiece dump;
	// flags asserted by piece upon special move validation
	// must be deasserted after test move
	// deasserted upon move completion
	static boolean CASTLING = false;
	static boolean ENPASSANT = false;

	public GameBoard() {
		super("CHESS");
		this.setLayout(new GridLayout(ROWS, COLS));
		// add squares
		boolean black = false;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				black = (i + j) % 2 == 1;
				squares[i][j] = new Square(i, j, this, black);
				this.add(squares[i][j]);
			}
		}
		// Pawns
		for (int j = 0; j < COLS; j++)
			new Pawn(true, squares[1][j]);
		for (int j = 0; j < COLS; j++)
			new Pawn(false, squares[ROWS - 2][j]);
		// Rooks
		new Rook(true, squares[0][0]);
		new Rook(true, squares[0][COLS - 1]);
		new Rook(false, squares[ROWS - 1][0]);
		new Rook(false, squares[ROWS - 1][COLS - 1]);
		// Knights
		new Knight(true, squares[0][1]);
		new Knight(true, squares[0][COLS - 1 - 1]);
		new Knight(false, squares[ROWS - 1][1]);
		new Knight(false, squares[ROWS - 1][COLS - 1 - 1]);
		// Bishops
		new Bishop(true, squares[0][2]);
		new Bishop(true, squares[0][COLS - 1 - 2]);
		new Bishop(false, squares[ROWS - 1][2]);
		new Bishop(false, squares[ROWS - 1][COLS - 1 - 2]);
		// Monarchs
		new Queen(true, squares[0][3]);
		new Queen(false, squares[ROWS - 1][3]);
		bKing = new King(true, squares[0][4]);
		wKing = new King(false, squares[ROWS - 1][4]);
		// some finishing touches
		this.setSize(1000, 1000);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	// square alerts board of click
	public void clicked(Square sq) {
		if (src == null && sq.getPiece() != null) {
			if (sq.getPiece().getTeam() == turn) {
				src = sq;
				src.setPieceHighlight(true);
				ChessPiece cp = src.getPiece();
				// highlight possible moves
				for (int r = 0; r < ROWS; r++)
					for (int c = 0; c < COLS; c++)
						if (cp.isMoveLegal(squares[r][c])) {
							squares[r][c].setHighlight(true);
							ENPASSANT = false;
							CASTLING = false;
						}
			}
		} else if (src != null && dest == null) {
			src.setPieceHighlight(false);
			for (int r = 0; r < ROWS; r++)
				for (int c = 0; c < COLS; c++)
					squares[r][c].setHighlight(false);

			dest = sq;
			ChessPiece cp = src.getPiece();
			if (cp.isMoveLegal(dest)) {
				passable = null;
				cp.move(dest);
				// is pawn promoted
				if (cp instanceof Pawn
						&& dest.getRow() == (turn ? GameBoard.ROWS - 1 : 0)) {
					this.promotePawn((Pawn) cp);
				}
				// is King captured
				if (dump instanceof King) {
					JOptionPane.showMessageDialog(this,
							((turn) ? "Black" : "White") + " wins!", "Victory",
							JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
				// is King checked
				if (bKing.underAttack())
					JOptionPane.showMessageDialog(this, "Black checked",
							"Check", JOptionPane.INFORMATION_MESSAGE);
				if (wKing.underAttack())
					JOptionPane.showMessageDialog(this, "White checked",
							"Check", JOptionPane.INFORMATION_MESSAGE);
				turn = !turn;

			}
			src = null;
			dest = null;
		}
	}
	private void promotePawn(Pawn p) {
		int newcp = JOptionPane.showOptionDialog(this, "Choose replacement",
				"Pawn Promotion", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, promoteOptions,
				promoteOptions[0]);
		Square sq = p.location;
		boolean tm = p.getTeam();
		switch (newcp) {
			case QUEEN :
				new Queen(tm, sq);
				break;
			case KNIGHT :
				new Knight(tm, sq);
				break;
			case ROOK :
				new Rook(tm, sq);
				break;
			case BISHOP :
				new Bishop(tm, sq);
				break;
			default :
				new Queen(tm, sq);
				JOptionPane.showMessageDialog(this, "Default to Queen",
						"Input Exited", JOptionPane.ERROR_MESSAGE);
		}
	}
	// lame main
	public static void main(String[] args) {
		new GameBoard();
	}

}
