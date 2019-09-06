import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Square extends JPanel implements MouseListener{
	//~~~~ Private Member Variables ~~~~
	private static GameBoard board;
	private ChessPiece piece;
	private int row, col;
	private boolean highlighted = false;
	private boolean pieceHighlighted = false;
	private static final Color blue = new Color(0,255,255,180);
	private static final Color yellow = new Color(255,255,0,180);
	//~~~~ Constructors ~~~~
	public Square(int r, int c, GameBoard gb, boolean b){
		board = gb;
		row = r;
		col = c;
		if(b)
			this.setBackground(Color.DARK_GRAY);
		else
			this.setBackground(Color.WHITE);
		this.addMouseListener(this);
	}
	public Square(int r, int c, GameBoard gb){
		board = gb;
		row = r;
		col = c;
		this.setBackground(Color.BLACK);		
		this.addMouseListener(this);
	}
	//setters and getters
	public int getRow() {return row;}
	public int getCol() {return col;}
	public ChessPiece getPiece() { return piece;}
	public void setPiece(ChessPiece cp) {
		piece=cp;
		this.repaint();
	}
	//~~~~ some functions to specify the color of this square ~~~~
	public void setHighlight(boolean h) {
		if (h)
			this.setBorder(BorderFactory.createLineBorder(yellow,5));		
		else 
			this.setBorder(BorderFactory.createEmptyBorder());
	}
	
	public void setPieceHighlight(boolean h) {
		if (h)
			this.setBorder(BorderFactory.createLineBorder(blue,5));		
		else 
			this.setBorder(BorderFactory.createEmptyBorder());
	}

	public void setColor( int c ){//1 means black, 0 means white
		if(c==1)
			this.setBackground(Color.DARK_GRAY);
		else
			this.setBackground(Color.WHITE);
	}

	public void setColor( boolean black ){//true means black, false means white
		if(black)
			this.setBackground(Color.DARK_GRAY);
		else
			this.setBackground(Color.WHITE);
	}
	public void setColor( Color c ){
		this.setBackground(c);
	}


	public void paintComponent(Graphics g){
		super.paintComponent(g);
		/*
		if(pieceHighlighted) {
			g.setColor(new Color(0,255,255,100));
			g.fillRect(0,0,this.getWidth(),this.getHeight());
		}
		if(highlighted) {
			g.setColor(new Color(255,255,0,100));
			g.fillRect(0,0,this.getWidth(),this.getHeight());
		}
		*/
		if(piece!=null)
			piece.draw(g);
	}
	
	public String toString() {
		return "("+(row+1)+","+(col+1)+")";
	}
	
	//~~~~ MouseListener stuff ~~~~
	public void mousePressed(MouseEvent arg0) {
		//when I am clicked, I will tell the board that I was clicked!
		board.clicked(this);		
	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}
	

}
