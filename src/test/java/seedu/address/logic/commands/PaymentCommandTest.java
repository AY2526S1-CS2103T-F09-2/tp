package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.PaymentCommand.MESSAGE_PAYMENT_STATUS_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PaymentStatus;
import seedu.address.model.person.PaymentStatus.PaymentStatusValue;
import seedu.address.model.person.Person;

public class PaymentCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_returnsCorrectPaymentStatus() throws Exception {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PaymentCommand paymentCommand = new PaymentCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(MESSAGE_PAYMENT_STATUS_SUCCESS,
                person.getName(), person.getPaymentStatus());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), person);
        assertCommandSuccess(paymentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexAndStatus_returnsCorrectPaymentStatus() throws Exception {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PaymentStatus originalPaymentStatus = personToEdit.getPaymentStatus();

        final List<PaymentStatusValue> paymentStatusValues = List.of(PaymentStatusValue.PAID,
                PaymentStatusValue.UNPAID);

        for (PaymentStatusValue statusValue : paymentStatusValues) {
            Optional<PaymentStatusValue> newPaymentStatus = Optional.of(statusValue);
            PaymentCommand paymentCommand = new PaymentCommand(INDEX_FIRST_PERSON, newPaymentStatus);

            Person newPerson = Person.withPaymentStatus(personToEdit,
                    personToEdit.getPaymentStatus().update(newPaymentStatus));
            String expectedMessage = String.format(MESSAGE_PAYMENT_STATUS_SUCCESS,
                    newPerson.getName(), newPerson.getPaymentStatus());
            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.setPerson(personToEdit, newPerson);

            assertCommandSuccess(paymentCommand, model, expectedMessage, expectedModel);

            personToEdit = newPerson;
        }

        // Check payment status back to original
        assertEquals(originalPaymentStatus, personToEdit.getPaymentStatus());
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
}
