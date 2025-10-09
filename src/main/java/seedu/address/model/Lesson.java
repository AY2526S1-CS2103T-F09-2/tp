package seedu.address.model;

/**
 * Represents a Lesson in the address book.
 */
public class Lesson {


    /**
     * Represents an empty Lesson.
     * This is used when a Student has no upcoming lessons.
     * This allows us to avoid using null to represent the absence of a Lesson.
     */
    private static final class EmptyLesson extends Lesson {
        private EmptyLesson() {
            super("No upcoming lessons");
        }
    } 

    public static final Lesson EMPTY = new EmptyLesson();
    private String lessonDate;
    private boolean isCompleted;

    /** 
    * This method creates a new Lesson object
    * @param lessonDate
    */
    public Lesson(String lessonDate) {
        this.lessonDate = lessonDate;
        this.isCompleted = false;
    }   
    /** 
     * This method returns the date of the specific lesson of the student
     * @return String
     */
    public String getLessonDate() {
        return lessonDate;
    }

    @Override
    public String toString() {
        return lessonDate;
    }

    /** 
     * This method returns true if the lesson is completed
     * @return boolean
     */
    public boolean isCompleted() {
        return isCompleted;
    }
}

