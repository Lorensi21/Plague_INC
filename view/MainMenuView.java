package view;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {

    public JButton newGameButton = new JButton("Start Game");
    public JButton highScoreButton = new JButton("High Score");
    public JButton exitButton = new JButton("Exit");

    public MainMenuView() {
        setTitle("Plague Inc Game");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        // logo, a bit to the right and scaled down
        ImageIcon originalIcon = new ImageIcon("resources/plague_logo.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(420, 220, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.BLACK);
        logoPanel.add(logoLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 120, 20, 120));

        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        newGameButton.setFont(buttonFont);
        highScoreButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);

        newGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        highScoreButton.setHorizontalAlignment(SwingConstants.CENTER);
        exitButton.setHorizontalAlignment(SwingConstants.CENTER);

        Color buttonColor = new Color(170, 0, 0);

        newGameButton.setBackground(buttonColor);
        highScoreButton.setBackground(buttonColor);
        exitButton.setBackground(buttonColor);

        newGameButton.setForeground(Color.WHITE);
        highScoreButton.setForeground(Color.WHITE);
        exitButton.setForeground(Color.WHITE);

        newGameButton.setOpaque(true);
        highScoreButton.setOpaque(true);
        exitButton.setOpaque(true);

        newGameButton.setBorderPainted(false);
        highScoreButton.setBorderPainted(false);
        exitButton.setBorderPainted(false);

        newGameButton.setContentAreaFilled(true);
        highScoreButton.setContentAreaFilled(true);
        exitButton.setContentAreaFilled(true);

        newGameButton.setFocusPainted(false);
        highScoreButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);

        buttonPanel.add(newGameButton);
        buttonPanel.add(highScoreButton);
        buttonPanel.add(exitButton);

        add(logoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}


