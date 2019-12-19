import java.awt.Color;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MyGame extends SnakeGame {
	private boolean gameOver = false;
	private long startTime;
	private double time;

	public MyGame() {
		super();
		setBG(new Color(0x222222));
		setGridColor(new Color(200,200,200, 25));
		startTime = System.currentTimeMillis();
		playGame();
	}

	public void gameFrame() {
		if (gameOver) {
			return;
		}
		// grow by NOT removing last
		if (player.isTouching(food))
			placeFood();
		else
			player.removeLast();
		player.addFirst();

		time = (System.currentTimeMillis() - startTime) / 1000.0;
		// lose by touching self or wall
		if (player.isOverlapping() || player.isTouchingWall()) {
			gameOver = true;;
			String user = JOptionPane.showInputDialog("Enter your username");
			if (user == null || user.equals("")) user = "Anonymous";
			
			new ScoreFrame(new ScoreRecord(user, time, player.size()));
			JOptionPane.showMessageDialog(null, "You scored " + player.size() + " points.");
		}
		
		// curr game info
		score.setText("Score: " + player.size());
		timer.setText("Time: " + time);
		drawGame();// do this at some point

	}

	public static void main(String[] args) {
		new MyGame();
	}
}
