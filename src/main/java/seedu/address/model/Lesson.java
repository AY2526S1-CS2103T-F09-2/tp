package seedu.address.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Lesson in the address book.
 */
public class Lesson {

    private static Lesson empty;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String EMPTY_MESSAGE = "No lessons yet";

    private final LocalDate lessonDate;

    /**
     * This method creates a new Lesson object
     * @param lessonDate
     */
    public Lesson(String lessonDateString) {
        this.lessonDate = LocalDate.parse(lessonDateString);
    }

    public Lesson(LocalDate lessonDate) {
        this.lessonDate = lessonDate;
    }

    /**
     * Returns an empty lesson
     * @return
     */
    public static Lesson getEmpty() {
        if (Lesson.empty == null) {
            Lesson.empty = new EmptyLesson();
        }
        return empty;
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
     * @deprecated for removal
     */
    @Deprecated
    public String getLessonDate() {
        return lessonDate.toString();
    }

    /**
     * This method returns the date of the specific lesson of the student
     * @return LocalDate object of the lesson
     */
    public LocalDate getLessonDateTime() {
        return lessonDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return lessonDate.format(formatter);
    }

    public boolean isOutdated() {
        return lessonDate.isBefore(LocalDate.now());
    }

    public Lesson getNextLesson() {
        return getEmpty();
    }

    /**
     * A util function that checks whether the input date is a valid input
     * @param input
     * @return whether the input can be accepted by the format
     */
    public static boolean isValidLessonDate(String input) {
        try {
            LocalDate toConvert = LocalDate.parse(input);
            return toConvert.isAfter(LocalDate.now()) || toConvert.equals(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Represents an empty Lesson.
     * This is used when a Student has no upcoming lessons.
     * This allows us to avoid using null to represent the absence of a Lesson.
     */
    private static final class EmptyLesson extends Lesson {
        private EmptyLesson() {
            super(LocalDate.now());
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean isOutdated() {
            return false;
        }

        @Override
        public String toString() {
            return EMPTY_MESSAGE;
        }
    }
}
