import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Snake extends DoubleLinkedList<BodySegment> implements KeyListener {
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	// give change in coordinate based on direction
	private static final int[] deltaX = { 0, BodySegment.SIZE, 0, -BodySegment.SIZE };
	private static final int[] deltaY = { -BodySegment.SIZE, 0, BodySegment.SIZE, 0 };
	// properties of head of snake
	private int direction;
	private int headx = SnakeGame.WIDTH / 2, heady = SnakeGame.HEIGHT / 2;
	// FLAG to prevent U-turn by rapidly pressing two keys
	private boolean turningAllowed;

	public Snake() {
		// go ahead and give him 1 BodySegment
		this.add(new BodySegment(headx, heady));
		// tell him what direction he is moving
		this.direction = RIGHT;
		turningAllowed = true;
	}

	// public int size() already implemented by parent

	// addFirst adds to end of internal container
	public void addFirst() {
		headx += deltaX[direction];
		heady += deltaY[direction];
		this.add(new BodySegment(headx, heady));
		turningAllowed = true;
	}

	// removeLast removes from beginning of internal container
	public void removeLast() {
		this.remove();
	}

	public void draw(Graphics g) {
		for (BodySegment seg : this)
			seg.draw(g);
	}
	
	// NOTE : last node is the head of the snake
	public boolean isTouchingWall() {
		BodySegment last = this.last();
		int x = last.getXPos();
		int y = last.getYPos();
		return x < 0 || y < 0 || y >= SnakeGame.HEIGHT || x >= SnakeGame.WIDTH;
	}

	public boolean isOverlapping() {
		BodySegment last = this.last();
		for (BodySegment seg : this) {
			if (seg == last)
				break;
			if (last.isTouching(seg))
				return true;
		}
		return false;
	}
	public boolean isTouching(BodySegment sp) {
		// if (sp == null) throw new NullPointerException();
		for (BodySegment seg : this)
			if(seg.isTouching(sp))
				return true;
		return false;
	}
	// make the snake respond to key presses
	public void keyPressed(KeyEvent e) {
		if (!turningAllowed)
			return;
		turningAllowed = false;

		int dir = direction;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			dir = UP;
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			dir = DOWN;
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			dir = RIGHT;
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			dir = LEFT;
			break;
		}
		// snake cannot u-turn
		if ((direction + 2) % 4 != dir)
			direction = dir;
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}