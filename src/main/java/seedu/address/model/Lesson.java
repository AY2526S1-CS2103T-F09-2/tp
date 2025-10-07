package seedu.address.model;

public class Lesson {

   private String lessonDate;
   private boolean isCompleted;

    /** 
    * This method creates a new Lesson object
    * @param lessonDate
    */
    public Lesson(String lessonDate) {
     this.lessonDate = lessonDate;
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

