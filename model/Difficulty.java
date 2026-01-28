package model;
public enum Difficulty {
    EASY(0.5), NORMAL(1.0), HARD(1.5);
    public final double infectionMultiplier;
    Difficulty(double infectionMultiplier) {
        this.infectionMultiplier = infectionMultiplier;
    }
}
