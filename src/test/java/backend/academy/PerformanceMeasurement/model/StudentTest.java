package backend.academy.PerformanceMeasurement.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class StudentTest {

    @Test
    void testStudentRecordFields() {
        // Arrange: создаём экземпляр Student
        String expectedName = "John";
        String expectedSurname = "Doe";
        Student student = new Student(expectedName, expectedSurname);

        // Act & Assert: проверяем поля name и surname
        assertEquals(expectedName, student.name(), "The name should match the expected value.");
        assertEquals(expectedSurname, student.surname(), "The surname should match the expected value.");
    }

    @Test
    void testToString() {
        // Arrange: создаём экземпляр Student
        Student student = new Student("Jane", "Smith");

        // Act & Assert: проверяем метод toString()
        String expectedString = "Student[name=Jane, surname=Smith]";
        assertEquals(expectedString, student.toString(), "The toString output should match the expected format.");
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange: создаём два одинаковых экземпляра Student
        Student student1 = new Student("Alice", "Brown");
        Student student2 = new Student("Alice", "Brown");

        // Act & Assert: проверяем equals и hashCode
        assertEquals(student1, student2, "The two Student instances should be equal.");
        assertEquals(student1.hashCode(), student2.hashCode(), "The hashCodes should be equal for identical records.");
    }
}
