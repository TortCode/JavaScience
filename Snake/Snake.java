import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Snake extends DoubleLinkedQueue<BodySegment> implements KeyListener {
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	// give change in coordinate based on direction
	private static final int[] deltaX = { 0, BodySegment.SIZE, 0, -BodySegment.SIZE };
	private static final int[] deltaY = { -BodySegment.SIZE, 0, BodySegment.SIZE, 0 };
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

	public void addFirst() {
		headx += deltaX[direction];
		heady += deltaY[direction];
		this.add(new BodySegment(headx, heady));
		turningAllowed = true;
	}

	public void removeLast() {
		this.remove();
	}

	public void draw(Graphics g) {
		g.drawString("" + size(), headx, heady);
		for (BodySegment seg : this)
			seg.draw(g);
	}
	
	public boolean isTouchingWall() {
		BodySegment head = this.last();
		int x = head.getXPos();
		int y = head.getYPos();
		return x < 0 || y < 0 || y >= SnakeGame.HEIGHT || x >= SnakeGame.WIDTH;
	}

	public boolean isOverlapping() {
		BodySegment head = this.last();
		for (BodySegment seg : this) {
			if (seg == head)
				break;
			if (head.isTouching(seg))
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
			dir = UP;
			break;
		case KeyEvent.VK_DOWN:
			dir = DOWN;
			break;
		case KeyEvent.VK_RIGHT:
			dir = RIGHT;
			break;
		case KeyEvent.VK_LEFT:
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


