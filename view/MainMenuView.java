package view;
import javax.swing.*;
import util.Constants;
import java.awt.*;
public class MainMenuView extends JFrame {
    public JButton newGameButton = new JButton("New Game");
    public JButton highScoreButton = new JButton("High Scores");
    public JButton exitButton = new JButton("Exit");


    public MainMenuView() {
        setTitle(Constants.APP_TITLE);
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 15));


        buttonPanel.add(newGameButton);
        buttonPanel.add(highScoreButton);
        buttonPanel.add(exitButton);


        add(buttonPanel, BorderLayout.CENTER);
    }
}

