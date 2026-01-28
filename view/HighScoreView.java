package view;
import javax.swing.*;
import java.util.List;
import model.HighScore;
public class HighScoreView extends JFrame {
    public HighScoreView(List<HighScore> scores) {
        setTitle("High Scores");
        setSize(400, 300);
        setLocationRelativeTo(null);
        JList<HighScore> list = new JList<>(scores.toArray(new HighScore[0]));
        add(new JScrollPane(list));
    }
}