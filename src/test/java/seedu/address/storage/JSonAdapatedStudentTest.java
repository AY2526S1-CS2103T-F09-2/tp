package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Lesson;
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
    public void toModelType_nullLesson_throwsIllegalValueException() {
        JsonAdaptedPerson student = new JsonAdaptedStudent(TYPICAL_STUDENT.getName().toString(),
                TYPICAL_STUDENT.getPhone().toString(), TYPICAL_STUDENT.getEmail().toString(),
                TYPICAL_STUDENT.getAddress().toString(), TYPICAL_STUDENT.getTags().stream()
                        .map(JsonAdaptedTag::new).toList(),
                null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Lesson.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);

    }

}
