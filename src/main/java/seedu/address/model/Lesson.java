package seedu.address.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Lesson in the address book.
 */
public class Lesson {

    private static Lesson EMPTY;
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private final LocalDateTime lessonDate;

    /**
     * This method creates a new Lesson object
     * @param lessonDate
     */
    public Lesson(String lessonDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        this.lessonDate = LocalDateTime.parse(lessonDateString, formatter);
    }

    public Lesson(LocalDateTime lessonDate) {
        this.lessonDate = lessonDate;
    }
    /**
     * Returns an empty lesson
     * @return
     */
    public static Lesson getEmpty() {
        if (Lesson.EMPTY == null) {
            Lesson.EMPTY = new EmptyLesson();
        }
        return EMPTY;
    } 

    /**
     * Returns true if this lesson is empty (i.e., Lesson.EMPTY)
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * This method returns the string representation of the date of the specific
     * lesson of the student
     * @return String
     */
    public String getLessonDate() {
        return lessonDate.toString();
    }

    /**
     * This method returns the date of the specific lesson of the student
     * @return String
     */
    public LocalDateTime getLessonDateTime() {
        return lessonDate;
    }

    @Override
    public String toString() {
        return lessonDate.toString();
    }

    public boolean isOutdated() {
        return lessonDate.isBefore(LocalDateTime.now());
    }

    public Lesson getNextLesson() {
        return EMPTY;
    }

    /**
     * A util function that checks whether the input date is a valid input
     * @param input
     * @return whether the input can be accepted by the format
     */
    public static boolean isValidLessonDate(String input) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            LocalDateTime.parse(input, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Represents an empty Lesson.
     * This is used when a Student has no upcoming lessons.
     * This allows us to avoid using null to represent the absence of a Lesson.
     */
    private static final class EmptyLesson extends Lesson {
        private EmptyLesson() {
            super("No upcoming lessons");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean isOutdated() {
            return false;
        }
        
    }
}
