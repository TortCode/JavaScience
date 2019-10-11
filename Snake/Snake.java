import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.*;

public class Snake extends DoubleLinkedQueue<BodySegment> implements KeyListener {
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	// give change in coordinate based on direction
	private static final int[] deltaX = {0, BodySegment.SIZE, 0, -BodySegment.SIZE};
	private static final int[] deltaY = {-BodySegment.SIZE, 0, BodySegment.SIZE, 0};
	private int direction;
	private int headx = SnakeGame.WIDTH / 2, heady = SnakeGame.HEIGHT / 2;

	public Snake() {
		// go ahead and give him 1 BodySegment
		this.add(new BodySegment(headx, heady));
		// tell him what direction he is moving
		this.direction = RIGHT;
	}

	// public int size(){} already implemented by parent class
	public void addFirst() {
		headx += deltaX[direction];
		heady += deltaY[direction];
		this.add(new BodySegment(headx, heady));
	}
	public void removeLast() {
		this.remove();
	}
	public void draw(Graphics g) {
		g.drawString(""+size(), headx, heady);
		for (BodySegment seg : this)
			seg.draw(g);
	}
	// make the snake respond to key presses
	public void keyPressed(KeyEvent e) {
		int dir = direction;
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP :
				dir = UP;
				break;
			case KeyEvent.VK_DOWN :
				dir = DOWN;
				break;
			case KeyEvent.VK_RIGHT :
				dir = RIGHT;
				break;
			case KeyEvent.VK_LEFT :
				dir = LEFT;
				break;
		}
		// snake cannot u-turn
		if ((direction + 2) % 4 != dir)
			direction = dir;
	}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}


