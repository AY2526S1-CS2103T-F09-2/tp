package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_STATUS;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.PaymentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PaymentStatus;

/**
 * Parses input arguments and creates a new PaymentCommand object
 */
public class PaymentCommandParser implements Parser<PaymentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PaymentCommand
     * and returns an PaymentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PaymentCommand parse(String args) throws ParseException {
        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PAYMENT_STATUS);
            Optional<String> paymentStatusString = argMultimap.getValue(PREFIX_PAYMENT_STATUS);

            // if argument s/ flag used then check if it is a valid argument
            if ((paymentStatusString.isPresent() && !PaymentStatus.isValidPaymentStatus(paymentStatusString))
                    || argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }

            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

            // If PREFIX_PAYMENT_STATUS not used in command then paymentStatusString == Optional.empty
            if (paymentStatusString.isEmpty()) {
                return new PaymentCommand(index);
            }
            Optional<PaymentStatus.PaymentStatusValue> status = PaymentStatus.fromOptionalString(paymentStatusString);
            return new PaymentCommand(index, status);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaymentCommand.MESSAGE_USAGE), pe);
        }
    }
}
