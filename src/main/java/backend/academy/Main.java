package backend.academy;

import backend.academy.PerformanceMeasurement.benchmark.ReflectionBenchmark;
import lombok.experimental.UtilityClass;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;

/**
 * Main class for executing JMH benchmarks.
 */
@UtilityClass
public class Main {

    /**
     * The main method for executing benchmarks.
     *
     * @param args
     *            command-line arguments
     *
     * @throws RunnerException
     *             if the benchmark fails to execute
     */
    public static void main(String[] args) throws RunnerException {
        new Runner(ReflectionBenchmark.getBenchmarkOptions()).run();
    }
}
