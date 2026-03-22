package model;
import java.util.*;
public class GameModel {
    public final List<Country> countries = new ArrayList<>();
    public final List<TransportRoute> routes = new ArrayList<>();
    public final Difficulty difficulty;
    public VirusEngine engine;
    public Thread engineThread;
    public GameModel(Difficulty difficulty) {
        this.difficulty = difficulty;
        initCountries();
        initRoutes();
        seedStartInfection();

    }

    private void seedStartInfection() {
        for (Country c : countries) {
            c.infected = 0;
        }
        Country france = find("France");
        if (france != null) {
            france.seedInfection(1000);
        }
    }

    public void initCountries() {
        countries.add(new Country("Poland", 38_000_000, 0.017));
        countries.add(new Country("France", 67_000_000, 0.016));
        countries.add(new Country("Italy", 60_000_000, 0.015));
        countries.add(new Country("Sweden", 10_000_000, 0.014));

        countries.add(new Country("USA", 330_000_000, 0.020));
        countries.add(new Country("Canada", 38_000_000, 0.013));
        countries.add(new Country("Brazil", 212_000_000, 0.021));
        countries.add(new Country("Japan", 126_000_000, 0.012));
        countries.add(new Country("China", 1_412_000_000, 0.010));
        countries.add(new Country("Thailand", 70_000_000, 0.014));
        countries.add(new Country("Australia", 26_000_000, 0.012));

        countries.add(new Country("Egypt", 110_000_000, 0.018));
        countries.add(new Country("S.Africa", 60_000_000, 0.017));
        countries.add(new Country("Madagaskar", 28_000_000, 0.016));

        countries.forEach(c -> c.seedInfection(0));
        getCountry("France").seedInfection(1000);
    }
    public void startSimulation() {
        engine = new VirusEngine(countries, routes, difficulty);
        engineThread = new Thread(engine);
        engineThread.start();
    }
    public Country getCountry(String name) {
        return countries.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void stopSimulation() {
        engine.stop();
    }
    public List<Country> getCountries() {
        return Collections.unmodifiableList(countries);
    }

    public List<TransportRoute> getRoutes() {
        return Collections.unmodifiableList(routes);
    }

    private void initRoutes() {

        // ===== EUROPE =====
        routes.add(new TransportRoute(find("Poland"), find("Italy"), TransportType.LAND));
        routes.add(new TransportRoute(find("France"), find("Italy"), TransportType.LAND));
        routes.add(new TransportRoute(find("USA"), find("France"), TransportType.AIRPORT));
        routes.add(new TransportRoute(find("France"), find("Brazil"), TransportType.SEAPORT));
        routes.add(new TransportRoute(find("Sweden"), find("Japan"), TransportType.AIRPORT));

        // Sweden: airplane to Canada, seaport to Poland
        routes.add(new TransportRoute(find("Canada"), find("Sweden"), TransportType.AIRPORT));
        routes.add(new TransportRoute(find("Sweden"), find("Poland"), TransportType.SEAPORT));

        // ===== NORTH AMERICA =====
        routes.add(new TransportRoute(find("USA"), find("Canada"), TransportType.LAND));
        routes.add(new TransportRoute(find("USA"), find("Japan"), TransportType.AIRPORT));

        // ===== SOUTH AMERICA =====
        routes.add(new TransportRoute(find("USA"), find("Brazil"), TransportType.AIRPORT));
        routes.add(new TransportRoute(find("Brazil"), find("S.Africa"), TransportType.SEAPORT));

        // ===== ASIA =====
        routes.add(new TransportRoute(find("China"), find("Japan"), TransportType.AIRPORT));
        routes.add(new TransportRoute(find("China"), find("Thailand"), TransportType.LAND));
        routes.add(new TransportRoute(find("China"), find("Australia"), TransportType.SEAPORT));

        // ===== AFRICA =====
        routes.add(new TransportRoute(find("Egypt"), find("S.Africa"), TransportType.LAND));
        routes.add(new TransportRoute(find("Egypt"), find("China"), TransportType.AIRPORT));
        routes.add(new TransportRoute(find("Madagaskar"), find("S.Africa"), TransportType.SEAPORT));
        routes.add(new TransportRoute(find("Madagaskar"), find("Australia"), TransportType.AIRPORT));
        routes.add(new TransportRoute(find("Madagaskar"), find("Thailand"), TransportType.SEAPORT));

        // ===== OCEANIA =====
        routes.add(new TransportRoute(find("Australia"), find("USA"), TransportType.SEAPORT));
    }



    public Country find(String name) {
        return countries.stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null);
    }

}
