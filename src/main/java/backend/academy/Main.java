package backend.academy;

import backend.academy.PerformanceMeasurement.benchmark.ReflectionBenchmark;
import lombok.experimental.UtilityClass;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;

@UtilityClass
public class Main {

    public static void main(String[] args) throws RunnerException {
        new Runner(ReflectionBenchmark.getBenchmarkOptions()).run();
    }
}
