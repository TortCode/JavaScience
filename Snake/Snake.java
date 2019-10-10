
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Snake implements KeyListener{
	public static final int UP=0, RIGHT=1, DOWN=2, LEFT=3;
	private static final int[] deltaX = {0,BodySegment.SIZE,0,-BodySegment.SIZE};
	private static final int[] deltaY = {-BodySegment.SIZE,0,BodySegment.SIZE,0};
	private int direction;
	private int x = SnakeGame.WIDTH/2, y = SnakeGame.HEIGHT/2;
	
	private static class SegNode{
		BodySegment bs;
		SegNode next;
		SegNode prev;
		SegNode(BodySegment bs, SegNode prev, SegNode next) {
			this.bs = bs;
			this.next = next;
			this.prev = prev;
		}
	}
	
	//variables you will need
	// 1) pointer to the first and/or last piece of the snake
	// 2) a variable to keep track of what direction this snake is facing
	
	public Snake(){
		//go ahead and give him 1 BodySegment
		segs = new LinkedList<>();
		segs.add(new BodySegment(x,y));
		//tell him what direction he is moving
		this.direction = RIGHT;
	}
	
	public int size(){
		return segs.size();
	}
	
	public void addFirst(){
		x += deltaX[direction];
		y += deltaY[direction];
		segs.add(new BodySegment(x,y));
	}
	
	public void removeLast(){
		segs.remove();
	}
	
	
	public void draw(Graphics g){
		g.drawString("" + size(),x,y);
		for (BodySegment seg : segs)
			seg.draw(g);
	}
	
	
	@Override
	//make the snake respond to key presses
	public void keyPressed(KeyEvent e) {	
		if(e.getKeyCode() == KeyEvent.VK_UP)
			System.out.println("User pressed up");
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	
}
