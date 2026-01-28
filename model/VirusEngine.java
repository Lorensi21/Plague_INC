package model;
import java.util.List;
public class VirusEngine implements Runnable {
    private final List<Country> countries;
    private final Difficulty difficulty;
    private boolean running = true;
    public VirusEngine(List<Country> countries, Difficulty difficulty) {
        this.countries = countries;
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
        }
    }
}
