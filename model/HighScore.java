package model;
import java.io.Serializable;
public class HighScore implements Serializable {
    private final String playerName;
    private final int score;
    private final long time;
    private final String difficulty;
    public HighScore(String playerName, int score, long time, String difficulty) {
        this.playerName = playerName;
        this.score = score;
        this.time = time;
        this.difficulty = difficulty;
    }
    public String toString() {
        return playerName + " | " + score + " | " + time + "s | " + difficulty;
    }
}
