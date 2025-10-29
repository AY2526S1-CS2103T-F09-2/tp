package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Student;
import seedu.address.testutil.StudentBuilder;

public class AddStudentCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_studentAcceptedByModel_addSuccessful() throws Exception {

        Student validStudent = new StudentBuilder().build();
        Model model = new ModelManager();
        CommandResult commandResult = new AddStudentCommand(validStudent).execute(model);

        assertEquals(String.format(AddStudentCommand.MESSAGE_SUCCESS, Messages.format(validStudent)),
                commandResult.getFeedbackToUser());

    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() throws Exception {

        Student validStudent = new StudentBuilder().build();
        Model model = new ModelManager();
        CommandResult commandResult = new AddStudentCommand(validStudent).execute(model);
        AddStudentCommand secondTime = new AddStudentCommand(validStudent);
        assertThrows(CommandException.class, () -> secondTime.execute(model),
                AddStudentCommand.MESSAGE_DUPLICATE_STUDENT);
    }
}
