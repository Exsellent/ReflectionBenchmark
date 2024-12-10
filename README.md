# **Проект 5: Измерение производительности**

Проект направлен на изучение и сравнение производительности различных методов вызова
через **Java Reflection API** и альтернативных подходов.

---

## **Цель проекта**

Реализовать и провести бенчмарк-тесты для **четырёх способов вызова метода**:

1. **Прямой доступ** к методу.
2. Использование **`java.lang.reflect.Method`**.
3. Использование **`java.lang.invoke.MethodHandles`**.
4. Использование **`java.lang.invoke.LambdaMetafactory`**.

---

## **Функциональные требования**

1. Реализовать **бенчмарк-тест** для каждого варианта метода.
2. Использовать **профильный фреймворк** для замера производительности:
    - Java: **JMH** (Java Microbenchmark Harness).
3. Провести замеры, получить финальную таблицу результатов.

---

## **Нефункциональные требования**

1. **Опубликовать финальную таблицу результатов запуска тестов**.
2. Минимизировать фоновую нагрузку на компьютер при проведении замеров.
3. Время выполнения тестов должно быть увеличено для получения корректных данных (порядка
нескольких минут).

---

## **Структура проекта**

Проект организован с использованием **Apache Maven** и включает следующие
ключевые компоненты:

```
.
├── pom.xml                              # Конфигурация Maven и зависимости
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── backend/
│   │   │       ├── academy/
│   │   │       │   ├── Main.java        # Точка входа
│   │   │       │   └── PerformanceMeasurement/
│   │   │       │       ├── benchmark/
│   │   │       │       │   ├── ReflectionBenchmark.java   # Бенчмарк-тесты
│   │   │       │       │   └── BenchmarkResultsCollector.java # Сбор результатов
│   │   │       │       └── model/
│   │   │       │           └── Student.java   # Тестируемый класс
│   ├── test/                                # Тесты для проверки корректности
│
├── checkstyle.xml                           # Конфигурация Checkstyle
├── pmd.xml                                  # Конфигурация PMD
└── benchmark_results.txt                    # Финальные результаты замеров
```

---

## **Тестируемый класс**

Пример целевого класса:

```java
public record Student(String name, String surname) {}
```

---

## **Бенчмарк-тесты**

Тесты измеряют производительность четырёх методов вызова:

```java
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class ReflectionBenchmark {
    private Student student;
    private Method method;

    @Setup
    public void setup() throws NoSuchMethodException {
        student = new Student("Alexander", "Biryukov");
        method = Student.class.getDeclaredMethod("name");
        method.setAccessible(true);
    }

    @Benchmark
    public void directAccess(Blackhole bh) {
        bh.consume(student.name());
    }

    @Benchmark
    public void reflectionAccess(Blackhole bh) throws Exception {
        bh.consume(method.invoke(student));
    }
}
```

---

## **Запуск и сборка проекта**

Для запуска и тестирования проекта требуется **Java 22** и **Maven 3.8.8+**.

### **1. Сборка проекта**

Для сборки и проверки корректности выполнения:

```bash
mvn clean verify
```

### **2. Запуск бенчмарк-тестов**

Запуск тестов с помощью JMH:

```bash
mvn exec:java -Dexec.mainClass="backend.academy.PerformanceMeasurement.benchmark.ReflectionBenchmark"
```

### **3. Сбор результатов**

Для сборки и сохранения таблицы с результатами:

```bash
mvn exec:java -Dexec.mainClass="backend.academy.PerformanceMeasurement.benchmark.BenchmarkResultsCollector"
```

Результаты будут сохранены в файл **`benchmark_results.txt`**.

---

## **Пример финальной таблицы результатов**

```
Benchmark                                Mode       Cnt   Score      Error      Units     
=====================================================================================
directAccess                             avgt       1     0.620      0.000      ns/op     
lambdaMetafactoryAccess                  avgt       1     0.907      0.000      ns/op     
methodHandleAccess                       avgt       1     5.108      0.000      ns/op     
reflectionAccess                         avgt       1     7.098      0.000      ns/op     
```

---

## **Команды для анализа и тестирования**

1. **Компиляция проекта:**
   ```bash
   mvn compile
   ```

2. **Запуск тестов:**
   ```bash
   mvn test
   ```

3. **Запуск линтеров:**
   ```bash
   mvn checkstyle:check pmd:check spotbugs:check
   ```

4. **Вывод дерева зависимостей:**
   ```bash
   mvn dependency:tree
   ```

---

## **Полезные ресурсы**

- [JMH Samples](https://github.com/openjdk/jmh/tree/master/jmh-samples)
- [Java Reflection](https://blogs.oracle.com/javamagazine/post/java-reflection-introduction)
- [Документация по Maven](https://maven.apache.org/guides/index.html)
- [Java 22 API](https://docs.oracle.com/en/java/javase/22/docs/api/index.html)

---

## **Заключение**

В проекте реализованы и протестированы четыре различных способа вызова методов в Java
с использованием JMH. Результаты замеров производительности позволяют оценить разницу
в скорости и выбрать оптимальный подход для решения задач в реальных проектах.

---
