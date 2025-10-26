package seedu.address.storage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.Lesson;
import seedu.address.model.RecurringLesson;
import seedu.address.model.person.Student;
import seedu.address.testutil.StudentBuilder;

public class JSonAdapatedStudentTest {

    private static final Student TYPICAL_STUDENT = new StudentBuilder().build();
    

    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        JsonAdaptedPerson student = new JsonAdaptedStudent(TYPICAL_STUDENT);
        assertEquals(TYPICAL_STUDENT, student.toModelType());
    }

    @Test
    public void toModelType_emptyLesson_returnStudent() throws Exception {
        Student emptyLesson = new StudentBuilder().withNewLesson(Lesson.getEmpty()).build();
        JsonAdaptedStudent student = new JsonAdaptedStudent(emptyLesson);
        assertEquals(student.toModelType().getNextLesson(), Lesson.getEmpty());
    }

    @Test 
    public void toModelType_reucrringLesson_returnStudent() throws Exception {
        RecurringLesson recur = new RecurringLesson(LocalDate.now(), 5);
        Student recurStudent = new StudentBuilder().withNewLesson(recur).build();
        JsonAdaptedStudent studentJson = new JsonAdaptedStudent(recurStudent);
        Student convertedBack = studentJson.toModelType();
        assertEquals(recur.getIntervalDays(), convertedBack.getNextLesson().getIntervalDays());
        assertEquals(recur.getNextLesson(), convertedBack.getNextLesson().getNextLesson());
    }
}
