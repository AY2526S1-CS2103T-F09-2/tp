package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PaymentCommand;
import seedu.address.model.person.PaymentStatus.PaymentStatusValue;

public class PaymentCommandParserTest {

    private final PaymentCommandParser parser = new PaymentCommandParser();

    @Test
    public void parse_validIndexWithoutStatus_returnsPaymentCommand() {
        // valid index
        PaymentCommand expectedPaymentCommand = new PaymentCommand(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "1", expectedPaymentCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 1 \n \t  \t", expectedPaymentCommand);
    }

    @Test
    public void parse_invalidArgsWithoutStatus_throwsParseException() {
        String failureMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaymentCommand.MESSAGE_USAGE);

        // whitespace between numbers
        assertParseFailure(parser, "1 2", failureMessage);

        // not a number
        assertParseFailure(parser, "test1", failureMessage);

        // number + invalid characters
        assertParseFailure(parser, "1 test", failureMessage);
    }

    @Test
    public void parse_validIndexWithStatus_returnsPaymentCommand() {
        // paid and unpaid statuses
        PaymentCommand paidStatus = new PaymentCommand(INDEX_FIRST_PERSON, Optional.of(PaymentStatusValue.PAID));
        assertParseSuccess(parser, "1 s/paid", paidStatus);

        PaymentCommand unpaidStatus = new PaymentCommand(INDEX_FIRST_PERSON, Optional.of(PaymentStatusValue.UNPAID));
        assertParseSuccess(parser, "1 s/unpaid", unpaidStatus);

        // multiple whitespace between keywords
        assertParseSuccess(parser, " \n 1 \n \t  \t s/   \n \t \t paid", paidStatus);
    }

    @Test
    public void parse_invalidArgsWithStatus_throwsParseException() {
        String failureMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaymentCommand.MESSAGE_USAGE);

        // invalid status string
        assertParseFailure(parser, "1 s/foo", failureMessage);

        // empty status string
        assertParseFailure(parser, "1 s/", failureMessage);

        // status string with whitespaces and empty status
        assertParseFailure(parser, "     1    s/  ", failureMessage);

        assertParseFailure(parser, "1 s/paid s/paid", failureMessage);

        assertParseFailure(parser, "1 s/paid asdsadsadsad", failureMessage);

        assertParseFailure(parser, " s/paid asdsadsadsad", failureMessage);
    }
}
