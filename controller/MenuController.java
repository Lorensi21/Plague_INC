package controller;
import view.*;
import util.FileManager;
public class MenuController {
    private final MainMenuView view;
    public MenuController() {
        view = new MainMenuView();
        view.newGameButton.addActionListener(e -> startNewGame());
        view.highScoreButton.addActionListener(e -> showHighScores());
        view.exitButton.addActionListener(e -> System.exit(0));
        view.setVisible(true);
    }
    private void startNewGame() {
        javax.swing.JOptionPane.showMessageDialog(view, "Next phase: Difficulty selection");
    }
    private void showHighScores() {
        new HighScoreView(FileManager.loadScores()).setVisible(true);
    }
}