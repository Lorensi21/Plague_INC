package model;

public class UpgradeState {

    public static int airportLevel = 0;
    public static int seaportLevel = 0;
    public static int landLevel = 0;

    public static double getAirportChanceModifier() {
        return Math.max(0.2, 1.0 - 0.2 * airportLevel);
    }

    public static double getSeaportChanceModifier() {
        return Math.max(0.2, 1.0 - 0.2 * seaportLevel);
    }

    public static double getLandChanceModifier() {
        return Math.max(0.2, 1.0 - 0.2 * landLevel);
    }
}

