package view;

import model.HighScore;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighScoreView extends JFrame {

    public HighScoreView(List<HighScore> scores) {
        setTitle("High Scores");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("High Scores", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // sort scores by highest score first
        List<HighScore> sorted = new ArrayList<>(scores);
        sorted.sort(Comparator.comparingInt(h -> -h.score));

        DefaultListModel<HighScore> listModel = new DefaultListModel<HighScore>();
        for (HighScore hs : sorted) {
            listModel.addElement(hs);
        }

        JList<HighScore> list = new JList<HighScore>(listModel);
        list.setBackground(Color.BLACK);
        list.setForeground(Color.WHITE);
        list.setSelectionBackground(new Color(170, 0, 0));
        list.setSelectionForeground(Color.WHITE);
        list.setFont(new Font("Arial", Font.PLAIN, 14));
        list.setVisibleRowCount(8);
        list.setCellRenderer(new HighScoreRenderer());

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.getViewport().setBackground(Color.BLACK);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // simple header row so player knows what each value means
        JPanel headerPanel = new JPanel(new GridLayout(1, 4));
        headerPanel.setBackground(new Color(170, 0, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));

        JLabel nameHeader = new JLabel("Name");
        JLabel scoreHeader = new JLabel("Score");
        JLabel timeHeader = new JLabel("Time");
        JLabel difficultyHeader = new JLabel("Difficulty");

        nameHeader.setForeground(Color.WHITE);
        scoreHeader.setForeground(Color.WHITE);
        timeHeader.setForeground(Color.WHITE);
        difficultyHeader.setForeground(Color.WHITE);

        headerPanel.add(nameHeader);
        headerPanel.add(scoreHeader);
        headerPanel.add(timeHeader);
        headerPanel.add(difficultyHeader);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.BLACK);
        centerPanel.add(headerPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(170, 0, 0));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setOpaque(true);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(true);
        closeButton.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        bottomPanel.add(closeButton);

        add(titleLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }


    private static class HighScoreRenderer extends JPanel implements ListCellRenderer<HighScore> {

        private final JLabel nameLabel = new JLabel();
        private final JLabel scoreLabel = new JLabel();
        private final JLabel timeLabel = new JLabel();
        private final JLabel difficultyLabel = new JLabel();

        public HighScoreRenderer() {
            setLayout(new GridLayout(1, 4));
            setOpaque(true);

            nameLabel.setForeground(Color.WHITE);
            scoreLabel.setForeground(Color.WHITE);
            timeLabel.setForeground(Color.WHITE);
            difficultyLabel.setForeground(Color.WHITE);

            nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            timeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            add(nameLabel);
            add(scoreLabel);
            add(timeLabel);
            add(difficultyLabel);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends HighScore> list,
                                                      HighScore value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {
            if (value != null) {
                nameLabel.setText(value.playerName);
                scoreLabel.setText(String.valueOf(value.score));
                timeLabel.setText(value.time + "s");
                difficultyLabel.setText(value.difficulty);
            }

            if (isSelected) {
                setBackground(new Color(170, 0, 0));
            } else {
                setBackground(Color.BLACK);
            }

            return this;
        }
    }
}