package model;
import java.util.concurrent.ThreadLocalRandom;

public class TransportRoute {
    private final Country from;
    private final Country to;
    private final TransportType type;
    private boolean open = true;


    public TransportRoute(Country from, Country to, TransportType type) {
        this.from = from;
        this.to = to;
        this.type = type;
    }


    public boolean canTransmit() {
        return open && from.getInfected() > 0;
    }

    /** Spread gradually: threshold + per-route chance. Low infected = small chance per route; high infected = high chance. */
    public void transmit(Difficulty difficulty) {
        if (!canTransmit()) return;
        int sourceInfected = from.getInfected();
        int threshold = switch (difficulty != null ? difficulty : Difficulty.NORMAL) {
            case EASY -> 4000;
            case NORMAL -> 2500;
            case HARD -> 1200;
        };
        if (sourceInfected < threshold) return;
        // chance per route: ~10% at 10k, ~50% at 100k, ~100% at 500k+ (so not all 4 at once when low)
        double chance = 0.05 + Math.min(0.95, sourceInfected / 500_000.0);
        double modifier = 1.0;
        switch (type) {
            case AIRPORT -> modifier = UpgradeState.getAirportChanceModifier();
            case SEAPORT -> modifier = UpgradeState.getSeaportChanceModifier();
            case LAND -> modifier = UpgradeState.getLandChanceModifier();
        }
        chance *= modifier;
        if (ThreadLocalRandom.current().nextDouble() >= chance) return;
        int amount = 1 + (sourceInfected / 12000);
        if (amount > 8) amount = 8;
        int newTotal = to.getInfected() + amount;
        to.seedInfection(Math.min(newTotal, to.getPopulation()));
    }


    public TransportType getType() { return type; }
    public Country getFrom() { return from; }
    public Country getTo() { return to; }
}
