package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Lesson;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * Adds a lesson for a student to the address book.
 */
public class AddLessonCommand extends Command {

    public static final String COMMAND_WORD = "addLesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson for a student to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_LESSON + "LESSON_DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_LESSON + "Tuesday";

    public static final String MESSAGE_DUPLICATE_LESSON = "This student already has an existing lesson in the "
            + "address book";
    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";

    private Name studentName;
    private Lesson toAdd;

    /**
     * Creates an AddLessonCommand to add the specified {@Lesson Lesson} for a student identified by {@Name name}
     */
    public AddLessonCommand(Name name, Lesson lesson) {
        requireNonNull(name);
        this.studentName = name;
        requireNonNull(lesson);
        this.toAdd = lesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // Search the full person list for matching name
        Person foundPerson = null;
        for (Person p : model.getAddressBook().getPersonList()) {
            if (p.getName().fullName.equalsIgnoreCase(studentName.fullName)) {
                foundPerson = p;
                break;
            }
        }
        if (foundPerson == null) {
            throw new CommandException("Student does not exist");
        }
        // If already a Student, check for lesson
        if (foundPerson instanceof Student) {
            Student targetStudent = (Student) foundPerson;
            if (targetStudent.getNextLesson() != null && !targetStudent.getNextLesson().isEmpty()) {
                throw new CommandException(MESSAGE_DUPLICATE_LESSON);
            }
            model.addLesson(targetStudent, toAdd);
        } else {
            // Convert Person to Student and replace in address book
            Student newStudent = new Student(
                    foundPerson.getName(),
                    foundPerson.getPhone(),
                    foundPerson.getEmail(),
                    foundPerson.getAddress(),
                    foundPerson.getTags(),
                    toAdd
            );
            model.setPerson(foundPerson, newStudent);
        }
        // Find the updated student from the full person list
        Student updatedStudent = null;
        for (Person p : model.getAddressBook().getPersonList()) {
            if (p instanceof Student && p.getName().fullName.equalsIgnoreCase(studentName.fullName)) {
                updatedStudent = (Student) p;
                break;
            }
        }
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

        // instanceof handles nulls
        if (!(other instanceof AddLessonCommand)) {
            return false;
        }

        AddLessonCommand otherAddLessonCommand = (AddLessonCommand) other;
        return toAdd.equals(otherAddLessonCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                add("student", studentName).
                add("toAdd", toAdd).
                toString();
    }
}
