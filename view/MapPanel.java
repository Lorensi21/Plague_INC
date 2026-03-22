package view;
import model.Difficulty;
import model.TransportRoute;
import model.TransportType;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class MapPanel extends JPanel {

    private static final int MAP_W = 1633;
    private static final int MAP_H = 980;

    private final Image mapImage;
    private final List<TransportRoute> routes;
    private final Map<String, int[]> countryPositions;
    private volatile float routePhase = 0f;
    private volatile boolean animationRunning = true;

    public int drawX, drawY, drawW, drawH;

    public MapPanel(List<TransportRoute> routes, Map<String, int[]> countryPositions, Difficulty difficulty) {
        this.routes = routes != null ? routes : List.of();
        this.countryPositions = countryPositions != null ? countryPositions : Map.of();
        mapImage = new ImageIcon("resources/world_map.png").getImage();
        setLayout(null);

        double speed = difficulty != null ? difficulty.animationSpeed : 2.0;
        final int sleepMs = (int) (220 / speed);
        final float phaseStep = (float) Math.min(0.04, 0.012 * speed);

        Thread animThread = new Thread(() -> {
            while (animationRunning) {
                try {
                    Thread.sleep(sleepMs);
                } catch (InterruptedException ignored) {}
                routePhase += phaseStep;
                if (routePhase > 1f) routePhase = 0f;
                SwingUtilities.invokeLater(this::repaint);
            }
        });
        animThread.setDaemon(true);
        animThread.start();
    }

    private int toScreenX(int mapX) {
        return drawX + (int) ((double) mapX / MAP_W * drawW);
    }

    private int toScreenY(int mapY) {
        return drawY + (int) ((double) mapY / MAP_H * drawH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelW = getWidth();
        int panelH = getHeight();

        double imgRatio = 1920.0 / 1080;
        double panelRatio = (double) panelW / panelH;

        if (panelRatio > imgRatio) {
            drawH = panelH;
            drawW = (int) (panelH * imgRatio);
        } else {
            drawW = panelW;
            drawH = (int) (panelW / imgRatio);
        }

        drawX = (panelW - drawW) / 2;
        drawY = (panelH - drawH) / 2;

        g.drawImage(mapImage, drawX, drawY, drawW, drawH, this);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (TransportRoute route : routes) {
            int[] fromPos = countryPositions.get(route.getFrom().getName());
            int[] toPos = countryPositions.get(route.getTo().getName());
            if (fromPos == null || toPos == null) continue;

            int x1 = toScreenX(fromPos[0]);
            int y1 = toScreenY(fromPos[1]);
            int x2 = toScreenX(toPos[0]);
            int y2 = toScreenY(toPos[1]);

            Color lineColor = switch (route.getType()) {
                case LAND -> new Color(220, 100, 100, 180);
                case SEAPORT -> new Color(80, 120, 200, 180);
                case AIRPORT -> new Color(100, 100, 100, 120);
            };
            g2.setColor(lineColor);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(x1, y1, x2, y2);

            int midX = (x1 + x2) / 2;
            int midY = (y1 + y2) / 2;
            String icon = route.getType().icon;
            Font f = g2.getFont();
            g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
            g2.setColor(Color.DARK_GRAY);
            g2.drawString(icon, midX - 6, midY + 4);
            g2.setFont(f);

            int moveX = (int) (x1 + (x2 - x1) * routePhase);
            int moveY = (int) (y1 + (y2 - y1) * routePhase);
            g2.setColor(Color.BLACK);
            g2.drawString(icon, moveX - 6, moveY + 4);
        }
    }
}

