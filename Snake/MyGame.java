import java.awt.Color;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MyGame extends SnakeGame {
	// things you inherited from SnakeGame
	// protected Snake player;
	// protected BodySegment food;
	// protected double waitSeconds;

	public MyGame() {
		super();
		setBG(new Color(100, 100, 0));
		setGridColor(new Color(0, 255, 255, 100));

		playGame();
	}

	public void gameFrame() {
		// grow by NOT removing last
		if(player.isTouching(food)) placeFood();
		else player.removeLast();
		player.addFirst();
		
		drawGame();// do this at some point

	}
	

	public static void main(String[] args) {
		new MyGame();
	}
}
