package model;
import java.io.*;
import java.util.*;

public class HighScore implements Serializable {

    public static final String FILE_NAME = "highscores.dat";

    public final String playerName;
    public final int score;
    public final long time;
    public final String difficulty;

    public HighScore(String playerName, int score, long time, String difficulty) {
        this.playerName = playerName;
        this.score = score;
        this.time = time;
        this.difficulty = difficulty;
    }

    public static List<HighScore> loadAll() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<HighScore>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void saveAll(List<HighScore> scores) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(scores);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return playerName + " | " + score + " | " + time + "s | " + difficulty;
    }
}
