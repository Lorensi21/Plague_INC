package controller;
import model.*;
import view.*;
import view.GameView;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class GameController {
    public final GameModel model;
    public final GameView view;
    public long seconds = 0;
    public long coins = 10_000;
    public boolean running = true;


    public GameController(Difficulty difficulty) {
        model = new GameModel(difficulty);
        view = new GameView(model.getCountries(), model.getRoutes(), difficulty);
        view.setVisible(true);
        view.upgradesButton.addActionListener(e -> showUpgrades());
        view.updateCoins(coins);

        model.startSimulation();
        startTimerThread();
        startUiRefreshThread();
        registerExitShortcut();
        registerWindowCloseHandler();
    }


    public void startTimerThread() {
        new Thread(() -> {
            while (running) {
                try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
                seconds++;
                coins += 10;
                long currentCoins = coins;
                long currentSeconds = seconds;
                SwingUtilities.invokeLater(() -> {
                    view.updateTime(currentSeconds);
                    view.updateCoins(currentCoins);
                });
            }
        }).start();
    }


    public void startUiRefreshThread() {
        new Thread(() -> {
            while (running) {
                try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
                SwingUtilities.invokeLater(view::updateCountries);
                checkGameEnd();
            }
        }).start();
    }


    public void checkGameEnd() {
        boolean allInfected = model.getCountries().stream().allMatch(Country::isFullyInfected);
        if (allInfected) {
            running = false;
            model.stopSimulation();
            JOptionPane.showMessageDialog(view, "Game Over – Everyone is infected");
            askToSaveAndExit();
        }
    }


    private void askToSaveAndExit() {
        int choice = JOptionPane.showConfirmDialog(
                view,
                "Do you want to save your result?",
                "Save Result",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            saveHighScore();
        }

        view.dispose();
        SwingUtilities.invokeLater(MenuController::new);
    }


    private void saveHighScore() {
        long totalInfected = 0;
        long totalPopulation = 0;
        for (Country c : model.getCountries()) {
            totalInfected += c.getInfected();
            totalPopulation += c.getPopulation();
        }

        double infectedRatio = totalPopulation == 0 ? 0.0 : (double) totalInfected / totalPopulation;

        double difficultyMultiplier;
        switch (model.difficulty) {
            case EASY -> difficultyMultiplier = 1.0;
            case NORMAL -> difficultyMultiplier = 1.5;
            case HARD -> difficultyMultiplier = 2.0;
            default -> difficultyMultiplier = 1.0;
        }

        double timeFactor = Math.max(1.0, seconds / 10.0);

        int score = (int) (infectedRatio * 10000 * difficultyMultiplier / timeFactor);

        JOptionPane.showMessageDialog(
                view,
                "Your score: " + score,
                "Game Score",
                JOptionPane.INFORMATION_MESSAGE
        );

        String name = JOptionPane.showInputDialog(
                view,
                "Enter your name:",
                "Save Score",
                JOptionPane.PLAIN_MESSAGE
        );

        if (name == null || name.isBlank()) {
            return;
        }

        java.util.List<HighScore> scores = HighScore.loadAll();
        scores.add(new HighScore(name, score, seconds, model.difficulty.name()));
        HighScore.saveAll(scores);
    }

    private void showUpgrades() {
        UpgradeDialog dialog = new UpgradeDialog(view, coins);
        dialog.setVisible(true);
        coins = dialog.getCoins();
        view.updateCoins(coins);
    }

    public void registerExitShortcut() {
        KeyStroke ks = KeyStroke.getKeyStroke("control shift Q");
        view.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ks, "EXIT");
        view.getRootPane().getActionMap().put("EXIT", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                running = false;
                model.stopSimulation();
                askToSaveAndExit();
            }
        });
    }


    private void registerWindowCloseHandler() {
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                running = false;
                model.stopSimulation();
                askToSaveAndExit();
            }
        });
    }
}

