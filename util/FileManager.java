package util;
import model.HighScore;
import java.io.*;
import java.util.*;

public class FileManager {
    public static List<HighScore> loadScores() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Constants.HIGH_SCORE_FILE))) {
            return (List<HighScore>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    public static void saveScores(List<HighScore> scores) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Constants.HIGH_SCORE_FILE))) {
            oos.writeObject(scores);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
