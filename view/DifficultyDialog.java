package view;

import model.Difficulty;

import javax.swing.*;
import java.awt.*;

public class DifficultyDialog extends JDialog {

    private Difficulty selectedDifficulty;

    public DifficultyDialog(JFrame parent) {
        super(parent, "Choose Difficulty", true);

        setSize(350, 180);
        setLocationRelativeTo(parent);
        setResizable(false);

        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Select difficulty:", SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));

        buttonPanel.add(createButton("Easy", Difficulty.EASY));
        buttonPanel.add(createButton("Normal", Difficulty.NORMAL));
        buttonPanel.add(createButton("Hard", Difficulty.HARD));

        add(label, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, Difficulty difficulty) {
        JButton button = new JButton(text);

        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));

        Color buttonColor = new Color(170, 0, 0);
        button.setBackground(buttonColor);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);

        button.addActionListener(e -> {
            selectedDifficulty = difficulty;
            dispose();
        });

        return button;
    }

    public static Difficulty showDialog(JFrame parent) {
        DifficultyDialog dialog = new DifficultyDialog(parent);
        dialog.setVisible(true);
        return dialog.selectedDifficulty;
    }
}
