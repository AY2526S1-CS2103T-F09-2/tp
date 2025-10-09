package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PaymentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
            Index index = ParserUtil.parseIndex(args);
            return new PaymentCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaymentCommand.MESSAGE_USAGE), pe);
        }
    }
}
