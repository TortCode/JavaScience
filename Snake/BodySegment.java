import java.awt.Color;
import java.awt.Graphics;

public class BodySegment {
	// this BodySegment will act like a NODE
	// he will point to the next BodySegment in the Snake
	// You will need a variable for this

	public static final int SIZE = 15;
	private Color hue = new Color(0x22BB77);
	public static int nextID = 0;
	public int myID;
	private int xPos, yPos;

	public BodySegment(int x, int y) {
		xPos = x;
		yPos = y;

		myID = nextID++;
	}

	// ~~~~~~~~~~~~~~~~accessors and mutators~~~~~~~~~~~~~~~~~~~~~~

	public boolean isTouching(BodySegment sp) {
		return sp.xPos == this.xPos && sp.yPos == this.yPos;
	}

	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}
	
	public Color getHue() {
		return hue;
	}

	public void setHue(Color hue) {
		this.hue = hue;
	}

	// each BodySegment knows how to draw himself :)
	public void draw(Graphics g) {
		g.setColor(hue);
		g.fillOval(xPos, yPos, SIZE, SIZE);
		// just for testing purposes, you can take this out later
		//g.setColor(Color.BLACK);
		//g.drawString("" + myID, xPos + SIZE / 4, yPos + SIZE);
	}
}