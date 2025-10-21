package seedu.address.model;

import java.time.LocalDate;

/**
 * Represents a RecurringLesson in the address book.
 */
public class RecurringLesson extends Lesson {

    private static final int MAX_INTERVAL = 365;
    int intervalDays;

    /**
     * This method creates a new ReuccringLesson object
     * @param lessonDate
     */
    public RecurringLesson(String lessonDateString, int intervalDays) {
        super(lessonDateString);
        this.intervalDays = intervalDays;
    }

    public RecurringLesson(LocalDate lessonDate, int intervalDays) {
        super(lessonDate);
        this.intervalDays = intervalDays;
    }

    @Override
    public Lesson getNextLesson() {
        LocalDate nextLessonDate = getLessonDateTime().plusDays(intervalDays);
        return new RecurringLesson(nextLessonDate, intervalDays);
    }

    @Override
    public String toString() {
        return super.toString() + " (every " + intervalDays + " days)";
    }

    public static boolean isValidInterval(String input) {
        try {
            int interval = Integer.parseInt(input);
            return interval > 0 && interval < MAX_INTERVAL;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
