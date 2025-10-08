package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Lesson;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * Cancels the scheduled lesson of an existing student in the address book
 */
public class CancelLessonCommand extends Command {

    public static final String COMMAND_WORD = "cancelLesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels the next lesson of the student "
            + "at the given index number in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_NOT_A_STUDENT = "The person is not a student!";
    public static final String MESSAGE_NO_LESSON = "There is no scheduled lesson to cancel";
    public static final String MESSAGE_CANCEL_SUCCESS = "Cancelled lesson '%s' for student '%s'";

    private final Index index;

    /**
     * @param index of the student in the filtered person list to cancel lesson
     */
    public CancelLessonCommand(Index index) {
        this.index = requireNonNull(index);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person studentToEdit = lastShownList.get(index.getZeroBased());
        if (!(studentToEdit instanceof Student)) {
            throw new CommandException(MESSAGE_NOT_A_STUDENT);
        }
        Student student = (Student) studentToEdit;

        Lesson next = student.getNextLesson();

        if (next == null || next == Lesson.EMPTY) {
            throw new CommandException(MESSAGE_NO_LESSON);
        }

        Student edited = new Student(
                student.getName(),
                student.getPhone(),
                student.getEmail(),
                student.getAddress(),
                student.getTags(),
                Lesson.EMPTY);

        model.setPerson(student, edited);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_CANCEL_SUCCESS, next, Messages.format(edited)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CancelLessonCommand)) {
            return false;
        }

        CancelLessonCommand otherCancelCommand = (CancelLessonCommand) other;
        return index.equals(otherCancelCommand.index);
    }
}
