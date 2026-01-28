package model;
import java.util.*;
public class GameModel {
    private final List<Country> countries = new ArrayList<>();
    private final Difficulty difficulty;
    private VirusEngine engine;
    private Thread engineThread;
    public GameModel(Difficulty difficulty) {
        this.difficulty = difficulty;
        initCountries();
    }
    private void initCountries() {
        countries.add(new Country("Ukraine", 41_000_000, 0.018));
        countries.add(new Country("Poland", 38_000_000, 0.017));
        countries.add(new Country("Germany", 83_000_000, 0.016));
        countries.add(new Country("France", 67_000_000, 0.016));
        countries.add(new Country("Italy", 60_000_000, 0.015));
        countries.add(new Country("Spain", 47_000_000, 0.015));
        countries.add(new Country("Netherlands", 17_000_000, 0.014));
        countries.add(new Country("Czech Republic", 10_700_000, 0.014));


        countries.add(new Country("USA", 330_000_000, 0.020));
        countries.add(new Country("Canada", 38_000_000, 0.013));
        countries.add(new Country("Brazil", 212_000_000, 0.021));
        countries.add(new Country("Japan", 126_000_000, 0.012));
        countries.add(new Country("China", 1_412_000_000, 0.010));
        countries.add(new Country("Thailand", 70_000_000, 0.014));
        countries.add(new Country("New Zealand", 5_100_000, 0.011));
        countries.add(new Country("Australia", 26_000_000, 0.012));


        countries.add(new Country("Nigeria", 223_000_000, 0.019));
        countries.add(new Country("Egypt", 110_000_000, 0.018));
        countries.add(new Country("South Africa", 60_000_000, 0.017));
        countries.add(new Country("Kenya", 55_000_000, 0.016));
        countries.add(new Country("Ethiopia", 126_000_000, 0.018));


        Country start = countries.get(new java.util.Random().nextInt(countries.size()));
        start.seedInfection(1_000);
    }
    public void startSimulation() {
        engine = new VirusEngine(countries, difficulty);
        engineThread = new Thread(engine);
        engineThread.start();
    }
    public void stopSimulation() {
        engine.stop();
    }
    public List<Country> getCountries() {
        return Collections.unmodifiableList(countries);
    }
}
