package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Student;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.StudentBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void refreshLessonDates_recurringLessonPastDate_advancesToNewDate() {
        LocalDate today = LocalDate.now();
        LocalDate fifteenDaysAgo = today.minusDays(15);
        RecurringLesson r = new RecurringLesson(fifteenDaysAgo, 7);
        Student s = new StudentBuilder().withNewLesson(r).build();

        AddressBook ab = new AddressBook();
        ab.addPerson(s);

        ModelManager mn = new ModelManager(ab, new UserPrefs());
        mn.refreshLessonDates();

        Student cur = (Student) mn.getAddressBook().getPersonList().get(0);

        Lesson next = cur.getNextLesson();
        assertTrue(next instanceof RecurringLesson, "Should remain recurring");

        LocalDate d = next.getLessonDateTime();
        assertFalse(d.isBefore(today), "Current lesson should not be before today");

        long days = ChronoUnit.DAYS.between(fifteenDaysAgo, d);
        assertEquals(0, days % 7, "Lesson updated in 7-day step");
    }

    @Test
    public void refreshLessonDates_futureLesson_noChange() {
        LocalDate today = LocalDate.now();
        LocalDate future = today.plusDays(5);

        Lesson l = new Lesson(future);
        Student s = new StudentBuilder().withNewLesson(l).build();

        AddressBook ab = new AddressBook();
        ab.addPerson(s);

        ModelManager mn = new ModelManager(ab, new UserPrefs());
        mn.refreshLessonDates();

        Student cur = (Student) mn.getAddressBook().getPersonList().get(0);
        assertEquals(future, cur.getNextLesson().getLessonDateTime(), "Future lesson stay unchanged");
    }

    @Test
    public void refreshLessonDates_passedDate_becomesEmpty() {
        LocalDate today = LocalDate.now();
        LocalDate past = today.minusDays(3);

        Lesson l = new Lesson(past);
        Student s = new StudentBuilder().withNewLesson(l).build();

        AddressBook ab = new AddressBook();
        ab.addPerson(s);

        ModelManager mn = new ModelManager(ab, new UserPrefs());
        mn.refreshLessonDates();

        Student cur = (Student) mn.getAddressBook().getPersonList().get(0);
        assertTrue(cur.getNextLesson().isEmpty(), "Lesson with a past date should become empty");
    }

    @Test
    public void refreshLessonDates_emptyLesson_remainEmpty() {
        Student s = new StudentBuilder().withNewLesson(Lesson.getEmpty()).build();

        AddressBook ab = new AddressBook();
        ab.addPerson(s);

        ModelManager mn = new ModelManager(ab, new UserPrefs());
        mn.refreshLessonDates();

        Student cur = (Student) mn.getAddressBook().getPersonList().get(0);
        assertTrue(cur.getNextLesson().isEmpty(), "Empty lesson remains empty after refresh");
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
