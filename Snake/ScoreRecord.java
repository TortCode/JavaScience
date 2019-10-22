
public class ScoreRecord implements Comparable<ScoreRecord>{
	String username;
	double time;
	int score;

	public ScoreRecord(String u, double t, int s) {
		username = u;
		time = t;
		score = s;
	}

	public ScoreRecord(String line) {
		String[] elems = line.split(":");
		username = elems[0];
		time = Double.parseDouble(elems[1]);
		score = Integer.parseInt(elems[2]);
	}

	public String fileFormat() {
		return username+":"+time+":"+score;
	}

	public String toString() {
		return "Username: " + username + "\nTime: " + time + "\nScore: " + score;
	}

	public int compareTo(ScoreRecord sr) {
		return sr.score - this.score;
	}
}
