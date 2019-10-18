//package forKids;

import java.awt.Color;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MyGame extends SnakeGame{
	//things you inherited from SnakeGame
	//protected Snake player;
	//protected BodySegment food;
	//protected double waitSeconds;
	
	public MyGame(){
		super();
		setBG(new Color(100,100,0));
		setGridColor( new Color(0,255,255,100));
		
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
		
		drawGame();// do this at some point

	}
	
	public static void main(String[] args){new MyGame();}	
}
