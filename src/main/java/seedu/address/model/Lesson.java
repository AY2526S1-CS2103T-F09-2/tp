package seedu.address.model;

public class Lesson {

   private String lessonDate;

   public Lesson(String lessonDate) {
     this.lessonDate = lessonDate;
    }   

    public String getLessonDate() {
        return lessonDate;
    }
    
    @Override
    public String toString() {
        return lessonDate;
    }

}

