package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PaymentStatus;
import seedu.address.model.person.Person;

/**
 * Displays a person's payment status using it's displayed index from the address book.
 */
public class PaymentCommand extends Command {

    public static final String COMMAND_WORD = "payment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display payment status of person. "
            + "Parameters: INDEX (must be a positive integer) [s/STATUS] (STATUS must be 'paid' or 'unpaid')\n"
            + "Example: " + COMMAND_WORD + " 1 s/ paid";

    public static final String MESSAGE_PAYMENT_STATUS_SUCCESS = """
            Name: %1$s
            Payment status: %2$s
            """;

    private final Index targetIndex;
    private final Optional<PaymentStatus> toSetPaymentStatus;

    /**
     * Creates a PaymentCommand for a student given a specified {@code targetIndex}.
     *
     * {@code PaymentStatus} is unchanged here.
     * @param targetIndex index of student in list.
     */
    public PaymentCommand(Index targetIndex) {
        this.toSetPaymentStatus = Optional.empty();
        this.targetIndex = targetIndex;
    }

    /**
     * Creates a PaymentCommand for a student given a specified {@code targetIndex} and {@code toSetPaymentStatus}.
     * Sets PaymentStatus of student according to {@code PaymentStatus}.
     *
     * @param targetIndex index of student in list.
     * @param toSetPaymentStatus PaymentStatus to set.
     */
    public PaymentCommand(Index targetIndex, Optional<PaymentStatus> toSetPaymentStatus) {
        this.targetIndex = targetIndex;
        this.toSetPaymentStatus = toSetPaymentStatus;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = lastShownList.get(targetIndex.getZeroBased());

        if (toSetPaymentStatus.isPresent()) {
            PaymentStatus updatedPaymentStatus = toSetPaymentStatus.get();

            // Create edited person with updated payment status
            Person editedPerson = Person.withPaymentStatus(person, updatedPaymentStatus);

            model.setPerson(person, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_PAYMENT_STATUS_SUCCESS,
                    editedPerson.getName(), editedPerson.getPaymentStatus()));
        }

        return new CommandResult(String.format(MESSAGE_PAYMENT_STATUS_SUCCESS,
                person.getName(), person.getPaymentStatus()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PaymentCommand)) {
            return false;
        }

        PaymentCommand otherPaymentCommand = (PaymentCommand) other;
        return targetIndex.equals(otherPaymentCommand.targetIndex);
    }
}
