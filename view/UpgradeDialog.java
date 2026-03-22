package view;

import model.UpgradeState;

import javax.swing.*;
import java.awt.*;

public class UpgradeDialog extends JDialog {

    private long coins;
    private final JLabel coinsLabel = new JLabel();

    private final JLabel airportLabel = new JLabel();
    private final JLabel seaportLabel = new JLabel();
    private final JLabel landLabel = new JLabel();

    public UpgradeDialog(JFrame parent, long coins) {
        super(parent, "Upgrades", true);
        this.coins = coins;

        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(3, 3, 5, 5));

        coinsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateCoinsLabel();

        updateLevelLabels();

        JButton airportButton = new JButton("Buy");
        airportButton.addActionListener(e -> buyAirportUpgrade());

        JButton seaportButton = new JButton("Buy");
        seaportButton.addActionListener(e -> buySeaportUpgrade());

        JButton landButton = new JButton("Buy");
        landButton.addActionListener(e -> buyLandUpgrade());

        mainPanel.add(new JLabel("Airport control:"));
        mainPanel.add(airportLabel);
        mainPanel.add(airportButton);

        mainPanel.add(new JLabel("Seaport control:"));
        mainPanel.add(seaportLabel);
        mainPanel.add(seaportButton);

        mainPanel.add(new JLabel("Land border control:"));
        mainPanel.add(landLabel);
        mainPanel.add(landButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(closeButton);

        add(coinsLabel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateCoinsLabel() {
        coinsLabel.setText("Coins: " + coins);
    }

    private void updateLevelLabels() {
        airportLabel.setText("Level " + UpgradeState.airportLevel + "/3");
        seaportLabel.setText("Level " + UpgradeState.seaportLevel + "/3");
        landLabel.setText("Level " + UpgradeState.landLevel + "/3");
    }

    private void buyAirportUpgrade() {
        int cost = 2000 * (UpgradeState.airportLevel + 1);
        if (!canBuy(UpgradeState.airportLevel, cost)) return;
        UpgradeState.airportLevel++;
        coins -= cost;
        updateCoinsLabel();
        updateLevelLabels();
    }

    private void buySeaportUpgrade() {
        int cost = 1500 * (UpgradeState.seaportLevel + 1);
        if (!canBuy(UpgradeState.seaportLevel, cost)) return;
        UpgradeState.seaportLevel++;
        coins -= cost;
        updateCoinsLabel();
        updateLevelLabels();
    }

    private void buyLandUpgrade() {
        int cost = 1000 * (UpgradeState.landLevel + 1);
        if (!canBuy(UpgradeState.landLevel, cost)) return;
        UpgradeState.landLevel++;
        coins -= cost;
        updateCoinsLabel();
        updateLevelLabels();
    }

    private boolean canBuy(int level, int cost) {
        if (level >= 3) {
            JOptionPane.showMessageDialog(this, "Maximum level reached for this upgrade.");
            return false;
        }
        if (coins < cost) {
            JOptionPane.showMessageDialog(this, "Not enough coins. Need " + cost + ".");
            return false;
        }
        return true;
    }

    public long getCoins() {
        return coins;
    }
}

