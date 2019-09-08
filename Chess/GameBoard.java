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
		// Automatic double link provided by constructor
		for (int j = 0; j < COLS; j++)
			new Pawn(true, squares[1][j]);
		for (int j = 0; j < COLS; j++)
			new Pawn(false, squares[6][j]);
		new Rook(true, squares[0][0]);
		new Rook(true, squares[0][7]);
		new Rook(false, squares[7][0]);
		new Rook(false, squares[7][7]);
		new Knight(true, squares[0][1]);
		new Knight(true, squares[0][6]);
		new Knight(false, squares[7][1]);
		new Knight(false, squares[7][6]);
		new Bishop(true, squares[0][2]);
		new Bishop(true, squares[0][5]);
		new Bishop(false, squares[7][2]);
		new Bishop(false, squares[7][5]);
		new Queen(true, squares[0][3]);
		new Queen(false, squares[7][3]);
		bKing = new King(true, squares[0][4]);
		wKing = new King(false, squares[7][4]);
		// some finishing touches
		this.setSize(1000, 1000);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	// click handler stores legal origin and destination
	// executes legal moves after destination selection
	public void clicked(Square sq) {
		if (src == null && sq.getPiece() != null) {
			ChessPiece cp = sq.getPiece();
			if (cp.getTeam() == turn) {
				src = sq;
				src.setPieceHighlight(true);
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
			// reset highlight
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
							(turn ? "Black" : "White") + " wins!", "Victory",
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
		p.location = null;
		boolean tm = p.getTeam();
		ChessPiece cp;
		switch (newcp) {
			case QUEEN :
				cp = new Queen(tm, sq);
				break;
			case KNIGHT :
				cp = new Knight(tm, sq);
				break;
			case ROOK :
				cp = new Rook(tm, sq);
				break;
			case BISHOP :
				cp = new Bishop(tm, sq);
				break;
			default :
				cp = new Queen(tm, sq);
				JOptionPane.showMessageDialog(this, "Default to Queen",
						"Improper Input Exit", JOptionPane.ERROR_MESSAGE);
		}
		cp.firstMove = false;
	}
	// lame main
	public static void main(String[] args) {
		new GameBoard();
	}

}
