package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Displays a person's payment status using it's displayed index from the address book.
 */
public class PaymentCommand extends Command {

    public static final String COMMAND_WORD = "payment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display payment status of person. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PAYMENT_STATUS_SUCCESS = """
            Name: %1$s
            Payment status: %2$b
            """;

    private final Index targetIndex;

    public PaymentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToCheck = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_PAYMENT_STATUS_SUCCESS,
                personToCheck.getName(), personToCheck.getPaymentStatus()));
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
