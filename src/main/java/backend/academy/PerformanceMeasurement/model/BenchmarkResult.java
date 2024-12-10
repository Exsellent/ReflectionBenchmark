package backend.academy.PerformanceMeasurement.model;

/**
 * Class to store benchmark results.
 */
public class BenchmarkResult {
    private String benchmark;
    private String mode;
    private int count;
    private double score;
    private double error;
    private String units;

    public BenchmarkResult(String benchmark, String mode, int count, double score, double error, String units) {
        this.benchmark = benchmark;
        this.mode = mode;
        this.count = count;
        this.score = score;
        this.error = error;
        this.units = units;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public String getMode() {
        return mode;
    }

    public int getCount() {
        return count;
    }

    public double getScore() {
        return score;
    }

    public double getError() {
        return error;
    }

    public String getUnits() {
        return units;
    }
}
