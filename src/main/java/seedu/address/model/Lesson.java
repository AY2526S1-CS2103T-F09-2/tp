package seedu.address.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Lesson in the address book.
 */
public class Lesson {

    public static final String EMPTY_MESSAGE = "No lessons yet";
    
    private static Lesson empty;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final int MAX_DATE_RANGE = 365;
    private final LocalDate lessonDate;

    /**
     * This method creates a new Lesson object
     * @param lessonDateString
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
     * Returns the interval day for a normal lesson
     * It returns 0 just for the convenience of storage
     */
    public int getIntervalDays() {
        return 0;
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
     * Returns the most recent upcoming lesson with reference to today's date
     * @param today local date of today
     * @return the most recent upcoming lesson
     */
    public Lesson getUpcomingLesson(LocalDate today) {
        Lesson current = this;
        while (!current.isEmpty() && current.isOutdated()) {
            current = current.getNextLesson();
        }
        return current;
    }

    /**
     * A util function that checks whether the input date is a valid input
     * @param input
     * @return whether the input can be accepted by the format
     */
    public static boolean isValidLessonDate(String input) {
        try {
            LocalDate toConvert = LocalDate.parse(input);
            boolean isAfterNow = toConvert.isAfter(LocalDate.now()) || toConvert.equals(LocalDate.now());
            boolean isWithinrange = toConvert.isBefore(LocalDate.now().plusDays(MAX_DATE_RANGE));
            return isAfterNow && isWithinrange;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Lesson)) {
            return false;
        }

        Lesson other = (Lesson) o;

        if (this.isEmpty() && other.isEmpty()) {
            return true;
        }

        return this.getLessonDateTime().equals(other.getLessonDateTime())
                && this.getClass().equals(other.getClass()); //prevent the other class being recurring lesson
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

        /**
         * This method is purely for the sake of convenience in storage
         */
        @Override
        public int getIntervalDays() {
            return -1;
        }
    }
}
