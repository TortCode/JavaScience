import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MyGame extends SnakeGame{
	//things you inherited from SnakeGame
	//protected Snake player;
	//protected BodySegment food;
	//protected double waitSeconds;
	private long startTime;
	
	public MyGame(){
		super();
		setBG(new Color(100,100,0));
		setGridColor( new Color(0,255,255,100));
		startTime = System.currentTimeMillis();
		playGame();
	}
	
	public void gameFrame() {
		// lose by touching self or wall
		if (player.isOverlapping() || player.isTouchingWall()) {
			JOptionPane.showMessageDialog(null, "Ha! Loser! Go back to Minesweeper!");
			System.exit(0);
		}
		// grow by NOT removing last
		if(player.isTouching(food)) placeFood();
		else player.removeLast();
		player.addFirst();
		
		score.setText("Score: " + player.size());
		timer.setText("Time: " + (System.currentTimeMillis()-startTime)/1000.0);
		drawGame();// do this at some point

	}
	
	public static void main(String[] args){new MyGame();}	
}
