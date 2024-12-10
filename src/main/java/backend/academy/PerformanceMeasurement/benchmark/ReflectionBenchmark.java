package backend.academy.PerformanceMeasurement.benchmark;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

/**
 * A benchmark class to compare various methods of accessing class fields.
 */
@BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.NANOSECONDS) @State(Scope.Thread)
public class ReflectionBenchmark {

    private static final String STUDENT_FIRST_NAME = "Alexander";
    private static final String STUDENT_LAST_NAME = "Biryukov";
    private static final String METHOD_NAME = "name";

    private static final int WARMUP_ITERATIONS = 1;
    private static final int MEASUREMENT_ITERATIONS = 1;
    private static final int FORKS = 1;
    private static final int WARMUP_FORKS = 1;
    private static final int WARMUP_TIME_SECONDS = 5;
    private static final int MEASUREMENT_TIME_SECONDS = 5;

    private Student student;
    private Method method;
    private MethodHandle methodHandle;
    private Function<Student, String> lambda;

    /**
     * Setup method for initializing the student object and reflection tools.
     *
     * @throws NoSuchMethodException if the method cannot be found
     * @throws Throwable             for method handle or lambda factory errors
     */
    @Setup public void setup() throws NoSuchMethodException, Throwable {
        student = new Student(STUDENT_FIRST_NAME, STUDENT_LAST_NAME);

        method = Student.class.getDeclaredMethod(METHOD_NAME);
        method.setAccessible(true);

        methodHandle =
            MethodHandles.lookup().findVirtual(Student.class, METHOD_NAME, MethodType.methodType(String.class));

        CallSite callSite =
            LambdaMetafactory.metafactory(MethodHandles.lookup(), "apply",
                MethodType.methodType(Function.class),
                MethodType.methodType(Object.class, Object.class), methodHandle,
                MethodType.methodType(String.class, Student.class));
        lambda = (Function<Student, String>) callSite.getTarget().invokeExact();
    }

    /**
     * Direct access benchmark.
     *
     * @param bh the Blackhole to consume results
     */
    @Benchmark public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    /**
     * Reflection access benchmark.
     *
     * @param bh the Blackhole to consume results
     * @throws Exception if method invocation fails
     */
    @Benchmark public void reflectionAccess(Blackhole bh) throws Exception {
        bh.consume(method.invoke(student));
    }

    /**
     * MethodHandle access benchmark.
     *
     * @param bh the Blackhole to consume results
     * @throws Throwable for method handle errors
     */
    @Benchmark public void methodHandleAccess(Blackhole bh) throws Throwable {
        bh.consume(methodHandle.invoke(student));
    }

    /**
     * LambdaMetafactory access benchmark.
     *
     * @param bh the Blackhole to consume results
     */
    @Benchmark public void lambdaMetafactoryAccess(Blackhole bh) {
        bh.consume(lambda.apply(student));
    }

    /**
     * Provides the configuration for JMH benchmarks.
     *
     * @return the Options for running benchmarks
     */
    public static Options getBenchmarkOptions() {
        return new OptionsBuilder().include(ReflectionBenchmark.class.getSimpleName()).shouldFailOnError(true)
            .shouldDoGC(true).mode(Mode.AverageTime).timeUnit(TimeUnit.NANOSECONDS).forks(FORKS)
            .warmupForks(WARMUP_FORKS).warmupIterations(WARMUP_ITERATIONS)
            .warmupTime(TimeValue.seconds(WARMUP_TIME_SECONDS)).measurementIterations(MEASUREMENT_ITERATIONS)
            .measurementTime(TimeValue.seconds(MEASUREMENT_TIME_SECONDS)).build();
    }

    public record Student(String name, String surname) {
    }
}
