import java.io.*;

public class ScoreFrame extends OrderedLinkedList<ScoreRecord> {
	public static String FILEDIR = "C:/PatelSnakeScores";
	private BufferedReader br;
	private PrintWriter pw;
	public ScoreFrame(ScoreRecord sr) throws IOException {
		this.insert(sr);
		this.readFile();
		this.writeFile();
	}
	
	private void readFile() throws IOException {
		br = new BufferedReader(new FileReader(FILEDIR+"/topScores.txt"));
		String input = br.readLine();
		while(input != null) {
			this.insert(new ScoreRecord(input));
			input = br.readLine();
		}
	}
	
	private void writeFile() throws IOException {
		pw = new PrintWriter(new FileWriter(FILEDIR+"/topScores.txt"));
		for (ScoreRecord sr : this) {
			System.out.println(sr.fileFormat());
			pw.println(sr.fileFormat());
		}
		pw.flush();
	}
}
