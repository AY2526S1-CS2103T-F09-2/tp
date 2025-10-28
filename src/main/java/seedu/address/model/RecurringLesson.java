package seedu.address.model;

import java.time.LocalDate;

/**
 * Represents a RecurringLesson in the address book.
 */
public class RecurringLesson extends Lesson {

    private static final int MAX_INTERVAL = 365;
    private final int intervalDays;

    /**
     * This method creates a new ReuccringLesson object
     * @param lessonDateString
     * @param intervalDays
     */
    public RecurringLesson(String lessonDateString, int intervalDays) {
        super(lessonDateString);
        this.intervalDays = intervalDays;
    }

    /**
     * Initialise a new recurring lesson with lesson date
     * @param lessonDate
     * @param intervalDays
     */
    public RecurringLesson(LocalDate lessonDate, int intervalDays) {
        super(lessonDate);
        this.intervalDays = intervalDays;
    }

    /**
     * Returns the interval day for the recurring lesson
     */
    @Override
    public int getIntervalDays() {
        return intervalDays;
    }

    /**
     * Output the next lesson after the previous becomes obsolete
     */
    @Override
    public Lesson getNextLesson() {
        LocalDate nextLessonDate = getLessonDateTime().plusDays(intervalDays);
        return new RecurringLesson(nextLessonDate, intervalDays);
    }

    @Override
    public String toString() {
        return super.toString() + " (every " + intervalDays + " days)";
    }

    /**
     * Checks whether a lesson interval is valid
     * @param input the String input of the interval
     * @return whether the lesson is valid
     */
    public static boolean isValidInterval(String input) {
        try {
            int interval = Integer.parseInt(input);
            return interval > 0 && interval < MAX_INTERVAL;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof RecurringLesson)) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        RecurringLesson other = (RecurringLesson) o;
        return this.getIntervalDays() == other.getIntervalDays() && this.getLessonDateTime().equals(other.getLessonDateTime());
    }
}
