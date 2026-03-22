package controller;
import model.Difficulty;
import view.*;
import model.HighScore;

import javax.swing.*;

public class MenuController {
    public final MainMenuView view;

    public MenuController() {
        view = new MainMenuView();
        view.newGameButton.addActionListener(e -> startNewGame());
        view.highScoreButton.addActionListener(e -> showHighScores());
        view.exitButton.addActionListener(e -> System.exit(0));
        view.setVisible(true);
    }

    public void startNewGame() {

            Difficulty difficulty = showDifficultyDialog();
            if (difficulty != null) {
                view.dispose();
                new GameController(difficulty);
            }


    }

    public void showHighScores() {
        new HighScoreView(HighScore.loadAll()).setVisible(true);
    }


    private Difficulty showDifficultyDialog() {
        return DifficultyDialog.showDialog(view);
    }
}

