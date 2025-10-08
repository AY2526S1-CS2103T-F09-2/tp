package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CancelLessonCommand;

public class CancelLessonCommandParserTest {
    private CancelLessonCommandParser parser = new CancelLessonCommandParser();

    @Test
    public void parse_validIndex_success() throws Exception {
        assertEquals(new CancelLessonCommand(Index.fromOneBased(2)), parser.parse("2"));
    }

    @Test
    public void parse_withWhiteSpace_success() throws Exception {
        assertEquals(new CancelLessonCommand(Index.fromOneBased(3)), parser.parse("  3"));
    }

    @Test
    public void parse_emptyArgs_failure() {
        assertParseFailure(parser, "", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                                                              CancelLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonNumeric_failure() {
        assertParseFailure(parser, "a", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                CancelLessonCommand.MESSAGE_USAGE));
    }

}
