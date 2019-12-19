import java.awt.BorderLayout;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ScoreFrame extends JFrame {

	// private member variables
	public static final String FILEDIR = "C:/PatelSnakeScores";// <<<<<<<Change this to include your last name
	public static String FILENAME = "topScores.txt";
	public static String OUTPUTFILENAME = "topScores.txt";
	public static final int WIDTH = 500, HEIGHT = 700;

	private JTextArea display; // you will append the scores into here
	private JScrollPane scrolly;

	private ScoreRecord current; // the score record for the current game
	private OrderedLinkedList<ScoreRecord> theScores; // where you will store all of the score records

	// Pass in the scoreRecord for the current game!
	public ScoreFrame(ScoreRecord scoreFromThisGame) {
		super("Top Scores");
		current = scoreFromThisGame;

		theScores = new OrderedLinkedList<>();
		display = new JTextArea();
		display.setEditable(false);
		display.setText("High Scores:\n");
		scrolly = new JScrollPane(display);

		readFromFile();
		writeToFile();

		this.add(scrolly, BorderLayout.CENTER);
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	// this function will:
	// 1) Read in all of the OLD scores that are stored in the text file & store
	// them into the ORderedList
	// 2) place current into the OrderedList
	public void readFromFile() {
		try {
			BufferedReader br = openFileForReading();
			if (br != null) {// if the file exists, read from it
				String input = br.readLine();
				int i = 1;
				if (!"".equals(input))
					while (i < 40 && input != null) {
						theScores.insert(new ScoreRecord(input));
						input = br.readLine();
						i++;
					}
				br.close();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// put the current record in the correct spot in your data structure
		theScores.insert(current);
		// display the records <using display.append()>
		int i = 1;
		for (ScoreRecord sr : theScores) {
			display.append(i + ": " + sr + "\n");
			i++;
		}

	}

	// this function will:
	// 1) Write each of the ScoreRecords out into the file
	// Be sure that they are in a colon-delimited format
	public void writeToFile() {
		try {
			PrintWriter pw = openFileForWriting();
			for (ScoreRecord sr : theScores)
				pw.print(sr.fileFormat() + "\r\n");
			pw.close();
		} catch (Exception ex) {
			System.out.println("ERROR!!?");
			ex.printStackTrace();
		}
	}

	/**********************************************************************/
	/** Don't mess with these functions down here. But you can call them **/
	private BufferedReader openFileForReading() {
		FileReader reeder;
		BufferedReader br = null;
		try {
			reeder = new FileReader(new File(FILEDIR, FILENAME));
			br = new BufferedReader(reeder);
		} catch (FileNotFoundException fnf) {
			System.out.println("First time playing");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return br;
	}

	private PrintWriter openFileForWriting() {
		File scoreDir = new File(FILEDIR);
		File scoreFile = new File(FILEDIR, OUTPUTFILENAME);
		FileWriter file = null;
		try {
			if (!scoreDir.exists()) {
				boolean made = scoreDir.mkdirs();
				System.out.println(made);
				scoreFile.createNewFile();
			}
			file = new FileWriter(scoreFile.getAbsolutePath());

		} catch (Exception ex) {
			System.out.println("ERROR!!?");
			ex.printStackTrace();
		}
		return new PrintWriter(file);
	}

}