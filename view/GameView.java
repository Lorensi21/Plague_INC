package view;
import model.Country;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Difficulty;
import model.TransportRoute;
public class GameView extends JFrame {
    public final JLabel timeLabel = new JLabel("Time: 0 s");
    public final JLabel infectedLabel = new JLabel("Infected: 0");
    public final JLabel percentLabel = new JLabel("Infected: 0%");
    public final JLabel coinsLabel;
    public final JButton upgradesButton = new JButton("Upgrades");
    public final MapPanel mapPanel;
    private final Map<Country, JButton> countryButtons = new HashMap<>();

    private static class MapPos {
        int x, y;
        MapPos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }



    private MapPos getMapPosition(String country) {
        return switch (country) {

            // EUROPE (spaced so 100px buttons do not overlap)
            case "France" -> new MapPos(750, 390);
            case "Poland" -> new MapPos(850, 360);
            case "Italy" -> new MapPos(850, 435);
            case "Sweden" -> new MapPos(830, 300);

            // NORTH AMERICA
            case "USA" -> new MapPos(350, 410);
            case "Canada" -> new MapPos(330, 330);

            // SOUTH AMERICA
            case "Brazil" -> new MapPos(570, 630);

            // ASIA
            case "China" -> new MapPos(1150, 450);
            case "Japan" -> new MapPos(1320, 410);
            case "Thailand" -> new MapPos(1140, 550);

            // AFRICA
            case "Egypt" -> new MapPos(890, 480);
            case "S.Africa" -> new MapPos(860, 720);
            case "Madagaskar" -> new MapPos(960, 670);

            // OCEANIA
            case "Australia" -> new MapPos(1300, 690);

            default -> new MapPos(800, 500);
        };
    }




    public GameView(List<Country> countries, List<TransportRoute> routes, Difficulty difficulty) {
        setTitle("AntiPlague – Game");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        Map<String, int[]> countryPositions = new HashMap<>();
        for (Country c : countries) {
            MapPos p = getMapPosition(c.getName());
            countryPositions.put(c.getName(), new int[]{p.x, p.y});
        }
        mapPanel = new MapPanel(routes, countryPositions, difficulty);

        ImageIcon coinIcon = new ImageIcon("resources/coin.png");
        Image coinImg = coinIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        coinsLabel = new JLabel("0", new ImageIcon(coinImg), SwingConstants.LEFT);
        coinsLabel.setFont(new Font("Arial", Font.BOLD, 12));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        topPanel.add(timeLabel);
        topPanel.add(infectedLabel);
        topPanel.add(percentLabel);
        topPanel.add(coinsLabel);
        topPanel.add(upgradesButton);

        add(topPanel, BorderLayout.NORTH);
        add(mapPanel, BorderLayout.CENTER);

        for (Country c : countries) {
            addCountryButton(c);
        }
    }


    private void addCountryButton(Country country) {
        JButton button = new JButton(country.getName());
        Font font = new Font("Arial", Font.PLAIN, 9);
        button.setFont(font);
        final int buttonW = 98;
        final int buttonH = 20;
        button.setMargin(new Insets(2, 4, 2, 4));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setBackground(Color.LIGHT_GRAY);
        button.setHorizontalAlignment(SwingConstants.CENTER);

        MapPos pos = getMapPosition(country.getName());

        mapPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int mapW = mapPanel.drawW;
                int mapH = mapPanel.drawH;
                double scaleX = mapW / 1633.0;
                double scaleY = mapH / 980.0;
                int x = mapPanel.drawX + (int)(pos.x * scaleX);
                int y = mapPanel.drawY + (int)(pos.y * scaleY);
                button.setBounds(x - buttonW / 2, y - buttonH / 2, buttonW, buttonH);
            }
        });

        button.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        country.getName() +
                                "\nInfected: " + country.getInfected() +
                                "/" + country.getPopulation()
                )
        );

        countryButtons.put(country, button);
        mapPanel.add(button);
    }





    public void updateCountries() {
        long totalInfected = 0;
        long totalPopulation = 0;

        for (Map.Entry<Country, JButton> entry : countryButtons.entrySet()) {
            Country c = entry.getKey();
            JButton b = entry.getValue();

            if (c.getInfected() > 0) {
                b.setBackground(Color.RED);
            } else {
                b.setBackground(Color.LIGHT_GRAY);
            }

            totalInfected += c.getInfected();
            totalPopulation += c.getPopulation();
        }

        infectedLabel.setText("Infected: " + totalInfected);
        double percent = totalPopulation == 0 ? 0.0 : 100.0 * totalInfected / totalPopulation;
        percentLabel.setText(String.format("Infected: %.2f%%", percent));
    }

    public void updateCoins(long coins) {
        coinsLabel.setText(String.valueOf(coins));
    }
    public void updateTime(long seconds) {
        timeLabel.setText("Time: " + seconds + " s");
    }

}