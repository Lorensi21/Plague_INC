package model;
public enum Difficulty {
    EASY(0.5, 2.0), NORMAL(1.0, 2.5), HARD(1.5, 3.0);
    public final double infectionMultiplier;
    /** Route animation speed: easy = normal pace, normal = in between, hard = fastest */
    public final double animationSpeed;
    Difficulty(double infectionMultiplier, double animationSpeed) {
        this.infectionMultiplier = infectionMultiplier;
        this.animationSpeed = animationSpeed;
    }
}
