package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Lesson;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.StudentBuilder;

public class AddLessonCommandTest {

    private static final Name VALID_NAME = new Name("John Doe");
    private static final Lesson VALID_LESSON = new Lesson("Tuesday");

    @Test
    public void execute_validLesson_success() throws Exception {
        Model model = new ModelManager();
        Person person = new PersonBuilder().build();
        model.addPerson(person);
        AddLessonCommand command = new AddLessonCommand(person.getName(), VALID_LESSON);
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
                        seedu.address.logic.Messages.format(updatedStudent)
                ),
                result.getFeedbackToUser()
        );
    }

    @Test
    public void execute_duplicateLesson_failure() throws Exception {
        Model model = new ModelManager();
        Student student = new StudentBuilder().build();
        model.addPerson(student);
        AddLessonCommand command = new AddLessonCommand(student.getName(), VALID_LESSON);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_studentNotFound_failure() {
        Model model = new ModelManager();
        AddLessonCommand command = new AddLessonCommand(new Name("Nonexistent"), VALID_LESSON);
        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals("Student does not exist", thrown.getMessage());
    }

}



