package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Lesson;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.StudentBuilder;

public class CancelLessonCommandTest {
    private Model model;
    private final Student withLesson = new StudentBuilder().build();
    private final Student noLesson = new StudentBuilder().withNewLesson(Lesson.EMPTY).build();
    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertEquals(1, model.getFilteredPersonList().size());
        Person shown = model.getFilteredPersonList().get(0);

        Student withLessonShown = new Student(
                shown.getName(),
                shown.getPhone(),
                shown.getEmail(),
                shown.getAddress(),
                shown.getTags(),
                new Lesson("Tue 3pm-5pm")
        );

        model.setPerson(shown, withLessonShown);
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        CancelLessonCommand cancelLessonCommand = new CancelLessonCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(
                CancelLessonCommand.MESSAGE_CANCEL_SUCCESS,
                "Tue 3pm-5pm",
                Messages.format(new StudentBuilder(withLessonShown).withNewLesson(Lesson.EMPTY).build())
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(withLessonShown,
                new StudentBuilder(withLessonShown).withNewLesson(Lesson.EMPTY).build());
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertEquals(1, model.getFilteredPersonList().size());

        assertCommandSuccess(cancelLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_studentHasLessonUnfilteredList_success() {
        Person shown = model.getFilteredPersonList().get(0);
        model.setPerson(shown, withLesson);

        CancelLessonCommand cancelLessonCommand = new CancelLessonCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(
                CancelLessonCommand.MESSAGE_CANCEL_SUCCESS,
                "2025-10-07 14:00 Math",
                Messages.format(noLesson)
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(withLesson, noLesson);

        assertCommandSuccess(cancelLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBound = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        CancelLessonCommand cancelLessonCommand = new CancelLessonCommand(outOfBound);
        assertCommandFailure(cancelLessonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        CancelLessonCommand cancelLessonCommand = new CancelLessonCommand(outOfBoundIndex);
        assertCommandFailure(cancelLessonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personNotStudentUnfilteredList_failure() {
        Person first = model.getFilteredPersonList().get(0);
        Person notStudent = new PersonBuilder().build();
        model.setPerson(first, notStudent);
        CancelLessonCommand cancelLessonCommand = new CancelLessonCommand(INDEX_FIRST_PERSON);
        assertCommandFailure(cancelLessonCommand, model, CancelLessonCommand.MESSAGE_NOT_A_STUDENT);
    }

    @Test
    public void execute_studentHasNoLessonUnfilteredList_failure() {
        Index index = INDEX_FIRST_PERSON;
        Person first = model.getFilteredPersonList().get(index.getZeroBased());
        model.setPerson(first, noLesson);

        CancelLessonCommand cancelLessonCommand = new CancelLessonCommand(index);
        assertCommandFailure(cancelLessonCommand, model, CancelLessonCommand.MESSAGE_NO_LESSON);
    }

    @Test
    public void equals() {
        CancelLessonCommand c1 = new CancelLessonCommand(INDEX_FIRST_PERSON);
        CancelLessonCommand c2 = new CancelLessonCommand(INDEX_FIRST_PERSON);
        CancelLessonCommand c3 = new CancelLessonCommand(INDEX_SECOND_PERSON);
        HelpCommand c4 = new HelpCommand();

        assertTrue(c1.equals(c2));
        assertTrue(c1.equals(c1));
        assertFalse(c1.equals(null));
        assertFalse(c1.equals(c3));
        assertFalse(c1.equals(c4));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        CancelLessonCommand cancelLessonCommand = new CancelLessonCommand(index);
        String expected = CancelLessonCommand.class.getCanonicalName() + "{index=" + index + "}";
        assertEquals(expected, cancelLessonCommand.toString());
    }
}
