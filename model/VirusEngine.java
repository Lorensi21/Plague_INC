package model;
import java.util.List;
public class VirusEngine implements Runnable {
    public final List<Country> countries;
    public final List<TransportRoute> routes;
    public final Difficulty difficulty;

    public boolean running = true;
    private int tickCount = 0;

    public VirusEngine(List<Country> countries, List<TransportRoute> routes, Difficulty difficulty) {
        this.countries = countries;
        this.routes = routes;
        this.difficulty = difficulty;
    }
    public void stop() {
        running = false;
    }
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
            countries.forEach(c -> c.updateInfection(difficulty.infectionMultiplier));
            // only start spreading between countries after first tick, so only start country is red at game start
            if (tickCount > 0) {
                routes.forEach(r -> r.transmit(difficulty));
            }
            tickCount++;
        }
    }
}
