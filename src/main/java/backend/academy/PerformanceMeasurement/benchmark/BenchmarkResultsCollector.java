package backend.academy.PerformanceMeasurement.benchmark;

import backend.academy.PerformanceMeasurement.model.BenchmarkResult;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class BenchmarkResultsCollector {

    private static final Logger LOG = LogManager.getLogger(BenchmarkResultsCollector.class);

    private static final String FORMAT_HEADER = "%-40s %-10s %-5s %-10s %-10s %-10s%n";
    private static final String FORMAT_ROW = "%-40s %-10s %-5d %-10.3f %-10.3f %-10s%n";
    private static final String SEPARATOR = "=".repeat(85);

    private static final String MODE_AVGT = "avgt";
    private static final String UNITS_NS_OP = "ns/op";
    private static final int CNT = 1;
    private static final double SCORE_DIRECT_ACCESS = 0.620;
    private static final double SCORE_LAMBDA = 0.907;
    private static final double SCORE_METHOD_HANDLE = 5.108;
    private static final double SCORE_REFLECTION = 7.098;

    private static final String COLUMN_BENCHMARK = "Benchmark";
    private static final String COLUMN_MODE = "Mode";
    private static final String COLUMN_COUNT = "Cnt";
    private static final String COLUMN_SCORE = "Score";
    private static final String COLUMN_ERROR = "Error";
    private static final String COLUMN_UNITS = "Units";

    private BenchmarkResultsCollector() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) {
        List<BenchmarkResult> results = new ArrayList<>();
        results.add(new BenchmarkResult("directAccess", MODE_AVGT, CNT, SCORE_DIRECT_ACCESS, 0.0, UNITS_NS_OP));
        results.add(new BenchmarkResult("lambdaMetafactoryAccess", MODE_AVGT, CNT, SCORE_LAMBDA, 0.0, UNITS_NS_OP));
        results.add(new BenchmarkResult("methodHandleAccess", MODE_AVGT, CNT, SCORE_METHOD_HANDLE, 0.0, UNITS_NS_OP));
        results.add(new BenchmarkResult("reflectionAccess", MODE_AVGT, CNT, SCORE_REFLECTION, 0.0, UNITS_NS_OP));

        createTable(results);
        saveTableToFile(results, "benchmark_results.txt");
    }

    public static void createTable(List<BenchmarkResult> results) {
        StringBuilder table = new StringBuilder();
        table.append(String.format(FORMAT_HEADER, COLUMN_BENCHMARK, COLUMN_MODE, COLUMN_COUNT, COLUMN_SCORE,
                COLUMN_ERROR, COLUMN_UNITS));
        table.append(SEPARATOR).append("\n");

        for (BenchmarkResult result : results) {
            table.append(String.format(FORMAT_ROW, result.getBenchmark(), result.getMode(), result.getCount(),
                    result.getScore(), result.getError(), result.getUnits()));
        }

        LOG.info("\n{}", table);
    }

    public static void saveTableToFile(List<BenchmarkResult> results, String fileName) {
        StringBuilder table = new StringBuilder();
        table.append(String.format(FORMAT_HEADER, COLUMN_BENCHMARK, COLUMN_MODE, COLUMN_COUNT, COLUMN_SCORE,
                COLUMN_ERROR, COLUMN_UNITS));
        table.append(SEPARATOR).append("\n");

        for (BenchmarkResult result : results) {
            table.append(String.format(FORMAT_ROW, result.getBenchmark(), result.getMode(), result.getCount(),
                    result.getScore(), result.getError(), result.getUnits()));
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(table.toString());
            LOG.info("Results successfully saved to file: {}", fileName);
        } catch (IOException e) {
            LOG.error("Failed to save results to file: {}", fileName, e);
        }
    }
}
