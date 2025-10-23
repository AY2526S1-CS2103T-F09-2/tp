package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Student;
import seedu.address.testutil.StudentBuilder;

public class JSonAdapatedStudentTest {

    private static final Student TYPICAL_STUDENT = new StudentBuilder().build();

    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        JsonAdaptedPerson student = new JsonAdaptedStudent(TYPICAL_STUDENT);
        assertEquals(TYPICAL_STUDENT, student.toModelType());
    }
}
