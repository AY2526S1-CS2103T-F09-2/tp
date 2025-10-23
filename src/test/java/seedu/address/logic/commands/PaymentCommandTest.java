package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.PaymentCommand.MESSAGE_NO_LESSON;
import static seedu.address.logic.commands.PaymentCommand.MESSAGE_PAYMENT_STATUS_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Lesson;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PaymentStatus;
import seedu.address.model.person.PaymentStatus.PaymentStatusValue;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

public class PaymentCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_personClassPaymentStatus_throwsCommandException() throws Exception {
        PaymentCommand paymentCommand = new PaymentCommand(INDEX_FIRST_PERSON);
        PaymentCommand paymentCommandWithFlag = new PaymentCommand(INDEX_FIRST_PERSON,
                Optional.of(PaymentStatusValue.PAID));

        assertCommandFailure(paymentCommand, model, MESSAGE_NO_LESSON);
        assertCommandFailure(paymentCommandWithFlag, model, MESSAGE_NO_LESSON);
    }

    @Test
    public void execute_validIndexAndIsStudentClass_returnsCorrectPaymentStatus() throws Exception {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PaymentCommand paymentCommand = new PaymentCommand(INDEX_FIRST_PERSON);

        Student student = generateStudent(person);

        String expectedMessage = String.format(MESSAGE_PAYMENT_STATUS_SUCCESS,
                student.getName(), student.getPaymentStatus());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), student);

        addLessonToModel(student);

        assertCommandSuccess(paymentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validStatusFlag_returnsCorrectPaymentStatus() throws Exception {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Student student = generateStudent(personToEdit);

        PaymentStatus originalPaymentStatus = student.getPaymentStatus();
        addLessonToModel(student);

        final List<PaymentStatusValue> paymentStatusValues = List.of(PaymentStatusValue.PAID,
                PaymentStatusValue.UNPAID);

        for (PaymentStatusValue statusValue : paymentStatusValues) {
            Optional<PaymentStatusValue> newPaymentStatus = Optional.of(statusValue);
            PaymentCommand paymentCommand = new PaymentCommand(INDEX_FIRST_PERSON, newPaymentStatus);

            Student newStudent = student.updatePaymentStatus(student,
                    student.getPaymentStatus().update(newPaymentStatus));
            String expectedMessage = String.format(MESSAGE_PAYMENT_STATUS_SUCCESS,
                    newStudent.getName(), newStudent.getPaymentStatus());
            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.setPerson(student, newStudent);

            assertCommandSuccess(paymentCommand, model, expectedMessage, expectedModel);

            student = newStudent;
        }

        // Check payment status back to original
        assertEquals(originalPaymentStatus, student.getPaymentStatus());
    }


    @Test
    public void execute_nullTargetIndex_throwsNullPointerException() {
        PaymentCommand paymentCommand = new PaymentCommand(null);
        assertThrows(NullPointerException.class, () -> paymentCommand.execute(model));
    }

    @Test
    public void execute_executeNullModel_throwsNullPointerException() {
        PaymentCommand paymentCommand = new PaymentCommand(INDEX_FIRST_PERSON);
        assertThrows(NullPointerException.class, () -> paymentCommand.execute(null));
    }

    @Test
    public void execute_invalidTargetIndex_throwsCommandException() {
        int size = model.getAddressBook().getPersonList().size();
        Index index = Index.fromZeroBased(size + 1);
        PaymentCommand paymentCommand = new PaymentCommand(index);
        assertCommandFailure(paymentCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);

        // flag s/ not included
        PaymentCommand paymentCommand = new PaymentCommand(index);
        String expected = PaymentCommand.class.getCanonicalName()
                + "{index=" + index + ", "
                + "flag=null" + "}";
        assertEquals(expected, paymentCommand.toString());

        // flag s/ included
        Optional<PaymentStatusValue> toSetPaymentStatus = Optional.of(PaymentStatusValue.UNPAID);
        PaymentCommand paymentCommandWithFlag = new PaymentCommand(index, toSetPaymentStatus);
        String expectedWithFlag = PaymentCommand.class.getCanonicalName()
                + "{index=" + index + ", "
                + "flag=" + toSetPaymentStatus.get() + "}";
        assertEquals(expectedWithFlag, paymentCommandWithFlag.toString());
    }

    @Test
    public void equals() {
        // same PaymentCommand
        PaymentCommand paymentCommand1 = new PaymentCommand(INDEX_FIRST_PERSON);
        assertEquals(paymentCommand1, paymentCommand1);

        // same index
        PaymentCommand paymentCommand2 = new PaymentCommand(Index.fromOneBased(1));
        assertEquals(paymentCommand1, paymentCommand2);

        // different index
        PaymentCommand paymentCommand3 = new PaymentCommand(INDEX_SECOND_PERSON);
        assertNotEquals(paymentCommand3, paymentCommand1);

        // not instanceof paymentCommand
        assertNotEquals(null, paymentCommand1);
    }

    private Student generateStudent(Person person) {
        if (person instanceof Student) {
            return (Student) person;
        }

        return new Student(person.getName(),
                person.getPhone(),
                person.getEmail(),
                person.getAddress(),
                person.getTags(),
                new Lesson("Tuesday")
        );
    }

    private void addLessonToModel(Student student) throws CommandException {
        AddLessonCommand addLessonCommand = new AddLessonCommand(student.getName(), new Lesson("Tuesday"));
        addLessonCommand.execute(model);
    }
}
