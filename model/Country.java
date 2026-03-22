package model;
public class Country {
    public final String name;
    public final int population;
    public int infected;
    public double infectionRate;
    public Country(String name, int population, double infectionRate) {
        this.name = name;
        this.population = population;
        this.infectionRate = infectionRate;
        this.infected = 0;
    }

    public void updateInfection(double difficultyMultiplier) {
        if (infected <= 0) return;

        int newInfections = (int) (infected * infectionRate * difficultyMultiplier);
        infected = Math.min(population, infected + Math.max(newInfections, 1));
    }
    public boolean isFullyInfected() {
        return infected >= population;
    }
    public void seedInfection(int initial) {
        infected = Math.min(initial, population);
    }
    public String getName() { return name; }
    public int getPopulation() { return population; }
    public int getInfected() { return infected; }
}
