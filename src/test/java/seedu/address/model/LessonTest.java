package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class LessonTest {

    @Test
    public void isValidLessonInput() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        assertTrue(Lesson.isValidLessonDate(LocalDate.now().format(formatter)));
        assertFalse(Lesson.isValidLessonDate(LocalDate.now().minusDays(1).format(formatter)));
        assertFalse(Lesson.isValidLessonDate("2027-13-01"));
        assertFalse(Lesson.isValidLessonDate("2027-11-1"));
        assertFalse(Lesson.isValidLessonDate("1900-11-15"));
        assertFalse(Lesson.isValidLessonDate("Hello"));
    }

    @Test
    public void initialiseTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate test = LocalDate.now();
        String expected = test.format(formatter);
        Lesson dummyLesson = new Lesson(test);
        String output = dummyLesson.toString();
        assertEquals(expected, output);
    }

    @Test
    public void nextLessonText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate test = LocalDate.now();
        Lesson dummyLesson = new Lesson(test);
        String expected = Lesson.EMPTY_MESSAGE;
        String output = dummyLesson.getNextLesson().toString();
        assertEquals(expected, output);
    }

}
