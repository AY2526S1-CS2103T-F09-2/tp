package seedu.address.model;

import java.time.LocalDateTime;

/**
 * Represents a ReuccringLesson in the address book.
 */
public class ReuccringLesson extends Lesson {

    private static final int MAX_INTERVAL = 365;
    int intervalDays;

    /**
     * This method creates a new ReuccringLesson object
     * @param lessonDate
     */
    public ReuccringLesson(String lessonDateString, int intervalDays) {
        super(lessonDateString);
        this.intervalDays = intervalDays;
    }

    public ReuccringLesson(LocalDateTime lessonDate, int intervalDays) {
        super(lessonDate);
        this.intervalDays = intervalDays;
    }

    @Override
    public Lesson getNextLesson() {
        LocalDateTime nextLessonDate = getLessonDateTime().plusDays(intervalDays);
        return new ReuccringLesson(nextLessonDate.toLocalDate().toString(), intervalDays);
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
