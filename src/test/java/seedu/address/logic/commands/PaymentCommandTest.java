package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PaymentStatus;
import seedu.address.model.person.Person;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.PaymentCommand.MESSAGE_PAYMENT_STATUS_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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

        PaymentStatus newPaymentStatus = null;
        // invert payment status twice to test both PAID and UNPAID status
        for (int i = 0; i < 2; i++) {
            newPaymentStatus = invertPaymentStatus(personToEdit.getPaymentStatus());
            PaymentCommand paymentCommand = new PaymentCommand(INDEX_FIRST_PERSON, Optional.of(newPaymentStatus));
            Person newPerson = Person.withPaymentStatus(personToEdit, newPaymentStatus);

            String expectedMessage = String.format(MESSAGE_PAYMENT_STATUS_SUCCESS,
                    newPerson.getName(), newPerson.getPaymentStatus());

            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.setPerson(personToEdit, newPerson);

            assertCommandSuccess(paymentCommand, model, expectedMessage, expectedModel);

            personToEdit = newPerson;
        }

        // Check payment status back to original
        assertEquals(originalPaymentStatus, newPaymentStatus);
    }


    @Test
    public void execute_nullTargetIndex_throwsNullPointerException(){
        PaymentCommand paymentCommand = new PaymentCommand(null);
        assertThrows(NullPointerException.class, () -> paymentCommand.execute(model));
    }

    @Test
    public void execute_executeNullModel_throwsNullPointerException(){
        PaymentCommand paymentCommand = new PaymentCommand(INDEX_FIRST_PERSON);
        assertThrows(NullPointerException.class, () -> paymentCommand.execute(null));
    }

    @Test
    public void execute_invalidTargetIndex_throwsCommandException(){
        int size = model.getAddressBook().getPersonList().size();
        Index index = Index.fromZeroBased(size + 1);
        PaymentCommand paymentCommand = new PaymentCommand(index);
        assertCommandFailure(paymentCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Method inverts payment status from paid to unpaid, vice versa.
     *
     * @param paymentStatus paymentStatus to invert
     * @return Inverted payment stastus
     */
    private PaymentStatus invertPaymentStatus(PaymentStatus paymentStatus) {
        return paymentStatus == PaymentStatus.PAID ? PaymentStatus.UNPAID : PaymentStatus.PAID;
    }
}
