package seedu.address.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Lesson in the address book.
 */
public class Lesson {

    public static final Lesson EMPTY = new EmptyLesson();

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final LocalDateTime lessonDate;

    /**
     * This method creates a new Lesson object
     * 
     * @param lessonDate
     */
    public Lesson(String lessonDateString) {
        this.lessonDate = LocalDateTime.parse(lessonDateString, DATE_FORMATTER);
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
     * 
     * @return String
     */
    public String getLessonDate() {
        return lessonDate.toString();
    }

    /**
     * This method returns the date of the specific lesson of the student
     * 
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
    }
}
