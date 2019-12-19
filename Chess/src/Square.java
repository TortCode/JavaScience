
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Square extends JPanel implements MouseListener {
	// ~~~~ Private Member Variables ~~~~
	private static GameBoard board;
	private ChessPiece piece;
	private int row, col;
	private static final Color BorderBLUE = new Color(0, 255, 255, 180);
	private static final Color BorderYELLOW = new Color(255, 255, 0, 180);
	// ~~~~ Constructors ~~~~
	public Square(int r, int c, GameBoard gb, boolean b) {
		board = gb;
		row = r;
		col = c;
		if (b)
			this.setBackground(Color.DARK_GRAY.darker());
		else
			this.setBackground(Color.WHITE);
		this.addMouseListener(this);
	}
	public Square(int r, int c, GameBoard gb) {
		board = gb;
		row = r;
		col = c;
		this.setBackground(Color.DARK_GRAY.darker());
		this.addMouseListener(this);
	}
	// setters and getters
	public GameBoard getBoard() {
		return board;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public ChessPiece getPiece() {
		return piece;
	}
	public void setPiece(ChessPiece cp) {
		piece = cp;
		this.repaint();
	}
	// ~~~~ some functions to specify the color of this square ~~~~
	public void setHighlight(boolean h) {// for possible moves
		if (h)
			this.setBorder(BorderFactory.createLineBorder(BorderYELLOW, 5));
		else
			this.setBorder(null);
	}
	public void setPieceHighlight(boolean h) {// selected piece
		if (h)
			this.setBorder(BorderFactory.createLineBorder(BorderBLUE, 5));
		else
			this.setBorder(null);
	}
	public void setColor(int c) {
		if (c == 1)
			this.setBackground(Color.DARK_GRAY.darker());
		else
			this.setBackground(Color.WHITE);
	}
	public void setColor(boolean black) {
		if (black)
			this.setBackground(Color.DARK_GRAY.darker());
		else
			this.setBackground(Color.WHITE);
	}
	public void setColor(Color c) {
		this.setBackground(c);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (piece != null)
			piece.draw(g);
	}
	// ~~~~ MouseListener stuff ~~~~
	public void mousePressed(MouseEvent arg0) {
		board.clicked(this);
	}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
}
