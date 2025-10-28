package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class RecurringLessonTest {

    @Test
    public void nextLessonTest() {
        LocalDate now = LocalDate.now();
        RecurringLesson dummyLesson = new RecurringLesson(now, 3);
        String expected = new RecurringLesson(now.plusDays(3), 3).toString();
        String output = dummyLesson.getNextLesson().toString();
        assertEquals(expected, output);
    }

    @Test
    public void nextLessonTest2() {
        LocalDate now = LocalDate.now();
        RecurringLesson dummyLesson = new RecurringLesson(now, 3);
        String expected = new RecurringLesson(now.plusDays(6), 3).toString();
        String output = dummyLesson.getNextLesson().getNextLesson().toString();
        assertEquals(expected, output);
    }

    @Test
    public void nextLessonTest3() {
        LocalDate now = LocalDate.now();
        RecurringLesson dummyLesson = new RecurringLesson(now, 4);
        String expected = new RecurringLesson(now.plusDays(4), 3).toString();
        String output = dummyLesson.getNextLesson().getNextLesson().toString();
        assertNotEquals(expected, output);
    }

    @Test 
    public void isOutdatedTest() {
        LocalDate now = LocalDate.now();
        RecurringLesson dummyLesson = new RecurringLesson(now, 4); // Now
        assertFalse(dummyLesson.isOutdated());
        dummyLesson = new RecurringLesson(now.minusDays(1), 4); // before
        assertTrue(dummyLesson.isOutdated());
        dummyLesson = new RecurringLesson(now.plusDays(1), 4); // after
        assertFalse(dummyLesson.isOutdated());
    }

    @Test
    public void intervalCheckTest() {
        assertFalse(RecurringLesson.isValidInterval("1000"));
        assertFalse(RecurringLesson.isValidInterval("NotANumber"));
        assertFalse(RecurringLesson.isValidInterval(""));
        assertFalse(RecurringLesson.isValidInterval(" "));
        assertFalse(RecurringLesson.isValidInterval("-1"));
        assertTrue(RecurringLesson.isValidInterval("364"));
        assertFalse(RecurringLesson.isValidInterval("366"));
        assertFalse(RecurringLesson.isValidInterval("0.5"));
        assertFalse(RecurringLesson.isValidInterval("0"));
        assertTrue(RecurringLesson.isValidInterval("1"));
        assertTrue(RecurringLesson.isValidInterval("3"));
        assertFalse(RecurringLesson.isValidInterval("<div />"));
    }

}
