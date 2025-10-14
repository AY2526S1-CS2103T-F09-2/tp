package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PaymentCommand;

public class PaymentCommandParserTest {

    private PaymentCommandParser parser = new PaymentCommandParser();

    @Test
    public void parse_validArgs_returnsPaymentCommand() {
        // valid index
        PaymentCommand expectedPaymentCommand = new PaymentCommand(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "1", expectedPaymentCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 1 \n \t  \t", expectedPaymentCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String failureMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaymentCommand.MESSAGE_USAGE);

        // whitespace between numbers
        assertParseFailure(parser, "1 2", failureMessage);

        // not a number
        assertParseFailure(parser, "test1", failureMessage);

        // number + invalid characters
        assertParseFailure(parser, "1 test", failureMessage);
    }
}
