package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Lesson;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RecurringLesson;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.StudentBuilder;

public class AddLessonCommandTest {

    private static final Lesson VALID_LESSON = new Lesson("2025-01-01");
    private static final Lesson ANOTHER_VALID_LESSON = new Lesson("2025-02-01");
    private static final RecurringLesson VALID_RECURRING_LESSON = new RecurringLesson("2025-01-01", 7);

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddLessonCommand(null, VALID_LESSON));
    }

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddLessonCommand(Index.fromOneBased(1), null));
    }

    @Test
    public void execute_validLesson_success() throws Exception {
        Model model = new ModelManager();
        Person person = new PersonBuilder().build();
        model.addPerson(person);
        Index index = Index.fromOneBased(1);
        AddLessonCommand command = new AddLessonCommand(index, VALID_LESSON);
        CommandResult result = command.execute(model);
        // Check feedback contains success message
        // Find the updated student
        Student updatedStudent = null;
        for (Person p : model.getAddressBook().getPersonList()) {
            if (p instanceof Student && p.getName().fullName.equalsIgnoreCase(person.getName().fullName)) {
                updatedStudent = (Student) p;
                break;
            }
        }
        assertEquals(
                String.format(
                        AddLessonCommand.MESSAGE_SUCCESS,
                        seedu.address.logic.Messages.format(updatedStudent)),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_validRecurringLesson_success() throws Exception {
        Model model = new ModelManager();
        Person person = new PersonBuilder().build();
        model.addPerson(person);
        Index index = Index.fromOneBased(1);
        AddLessonCommand command = new AddLessonCommand(index, VALID_RECURRING_LESSON);
        CommandResult result = command.execute(model);

        // Find the updated student
        Student updatedStudent = null;
        for (Person p : model.getAddressBook().getPersonList()) {
            if (p instanceof Student && p.getName().fullName.equalsIgnoreCase(person.getName().fullName)) {
                updatedStudent = (Student) p;
                break;
            }
        }

        // Verify the lesson was added and is a recurring lesson
        assertEquals(
                String.format(
                        AddLessonCommand.MESSAGE_SUCCESS,
                        seedu.address.logic.Messages.format(updatedStudent)),
                result.getFeedbackToUser());
        assertTrue(updatedStudent.getNextLesson() instanceof RecurringLesson);
    }

    @Test
    public void execute_studentWithExistingLesson_addsLesson() throws Exception {
        Model model = new ModelManager();
        // Create a student with no lesson (empty lesson)
        Student studentWithNoLesson = new StudentBuilder().withNewLesson(Lesson.getEmpty()).build();
        model.addPerson(studentWithNoLesson);
        Index index = Index.fromOneBased(1);
        AddLessonCommand command = new AddLessonCommand(index, VALID_LESSON);
        CommandResult result = command.execute(model);

        // Find the updated student
        Student updatedStudent = null;
        for (Person p : model.getAddressBook().getPersonList()) {
            if (p instanceof Student && p.getName().fullName.equalsIgnoreCase(studentWithNoLesson.getName().fullName)) {
                updatedStudent = (Student) p;
                break;
            }
        }

        assertEquals(
                String.format(
                        AddLessonCommand.MESSAGE_SUCCESS,
                        seedu.address.logic.Messages.format(updatedStudent)),
                result.getFeedbackToUser());
        assertNotEquals(Lesson.getEmpty(), updatedStudent.getNextLesson());
    }

    @Test
    public void execute_duplicateLesson_failure() throws Exception {
        Model model = new ModelManager();
        Student student = new StudentBuilder().build();
        model.addPerson(student);
        Index index = Index.fromOneBased(1);
        AddLessonCommand command = new AddLessonCommand(index, VALID_LESSON);
        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(AddLessonCommand.MESSAGE_DUPLICATE_LESSON, thrown.getMessage());
    }

    @Test
    public void execute_studentNotFound_failure() {
        Model model = new ModelManager();
        Index invalidIndex = Index.fromOneBased(1);
        AddLessonCommand command = new AddLessonCommand(invalidIndex, VALID_LESSON);
        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, thrown.getMessage());
    }

    @Test
    public void execute_invalidIndexTooLarge_failure() {
        Model model = new ModelManager();
        Person person = new PersonBuilder().build();
        model.addPerson(person);
        Index invalidIndex = Index.fromOneBased(10); // Out of bounds
        AddLessonCommand command = new AddLessonCommand(invalidIndex, VALID_LESSON);
        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, thrown.getMessage());
    }

    @Test
    public void equals() {
        AddLessonCommand addFirstLesson = new AddLessonCommand(Index.fromOneBased(1), VALID_LESSON);
        AddLessonCommand addSecondLesson = new AddLessonCommand(Index.fromOneBased(2), VALID_LESSON);
        AddLessonCommand addDifferentLesson = new AddLessonCommand(Index.fromOneBased(1), ANOTHER_VALID_LESSON);

        // same object -> returns true
        assertTrue(addFirstLesson.equals(addFirstLesson));

        // same values -> returns true
        AddLessonCommand addFirstLessonCopy = new AddLessonCommand(Index.fromOneBased(1), VALID_LESSON);
        assertTrue(addFirstLesson.equals(addFirstLessonCopy));

        // different types -> returns false
        assertFalse(addFirstLesson.equals(1));

        // null -> returns false
        assertFalse(addFirstLesson.equals(null));

        // different index -> returns false
        assertFalse(addFirstLesson.equals(addSecondLesson));

        // different lesson -> returns false
        assertFalse(addFirstLesson.equals(addDifferentLesson));
    }

    @Test
    public void toString_validCommand_correctFormat() {
        AddLessonCommand command = new AddLessonCommand(Index.fromOneBased(1), VALID_LESSON);
        String expected = AddLessonCommand.class.getCanonicalName()
                + "{index=" + Index.fromOneBased(1)
                + ", toAdd=" + VALID_LESSON + "}";
        assertEquals(expected, command.toString());
    }
}
