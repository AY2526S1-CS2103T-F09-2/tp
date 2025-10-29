package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Lesson;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * Adds a lesson for a student in the address book.
 */
public class AddLessonCommand extends Command {

    public static final String COMMAND_WORD = "addLesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson for a student to the address book. "
            + "Parameters: INDEX "
            + PREFIX_LESSON + "LESSON_DATE "
            + "[" + PREFIX_INTERVAL + "INTERVAL_DAYS_FOR_RECURRING]\n"
            + "Example:\n"
            + "Normal Lesson: " + COMMAND_WORD + " 2 "
            + PREFIX_LESSON + "2025-01-01\n"
            + "Recurring Lesson: " + COMMAND_WORD + " 2 "
            + PREFIX_LESSON + "2025-01-01 "
            + PREFIX_INTERVAL + "7";

    public static final String MESSAGE_DUPLICATE_LESSON = "This student already has an existing lesson in the "
            + "address book";
    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";

    private final Index targetIndex;
    private final Lesson toAdd;

    /**
     * Creates an AddLessonCommand to add the specified {@code Lesson} for the
     * student at the given {@code Index}.
     */
    public AddLessonCommand(Index index, Lesson lesson) {
        requireNonNull(index);
        requireNonNull(lesson);
        this.targetIndex = index;
        this.toAdd = lesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Use filtered list and the provided index to locate the student
        var lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person foundPerson = lastShownList.get(targetIndex.getZeroBased());

        Student newStudent;
        // If already a Student, check if they already have a lesson
        if (foundPerson instanceof Student) {
            Student targetStudent = (Student) foundPerson;
            Lesson existingLesson = targetStudent.getNextLesson();

            // Check if student already has a lesson scheduled
            if (existingLesson != null && !existingLesson.equals(Lesson.getEmpty())) {
                throw new CommandException(MESSAGE_DUPLICATE_LESSON);
            }

            // Create new student with the lesson
            newStudent = new Student(
                    targetStudent.getName(),
                    targetStudent.getPhone(),
                    targetStudent.getEmail(),
                    targetStudent.getAddress(),
                    targetStudent.getTags(),
                    toAdd,
                    targetStudent.getPaymentStatus(),
                    targetStudent.getEducationLevel());
            // increment outstanding payment for the newly added lesson
            newStudent = newStudent.unpaid();
        } else {
            // Convert Person to Student and add lesson
            newStudent = new Student(
                    foundPerson.getName(),
                    foundPerson.getPhone(),
                    foundPerson.getEmail(),
                    foundPerson.getAddress(),
                    foundPerson.getTags(),
                    toAdd);
            // mark as unpaid for the new lesson
            newStudent = newStudent.unpaid();
        }

        model.setPerson(foundPerson, newStudent);

        // Retrieve the updated person at the same index for display
        Person updated = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        Student updatedStudent = (updated instanceof Student) ? (Student) updated : null;

        if (updatedStudent == null) {
            throw new CommandException("Failed to update student");
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(updatedStudent)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddLessonCommand)) {
            return false;
        }

        AddLessonCommand otherAddLessonCommand = (AddLessonCommand) other;
        return targetIndex.equals(otherAddLessonCommand.targetIndex)
                && toAdd.equals(otherAddLessonCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", targetIndex)
                .add("toAdd", toAdd)
                .toString();
    }
}
