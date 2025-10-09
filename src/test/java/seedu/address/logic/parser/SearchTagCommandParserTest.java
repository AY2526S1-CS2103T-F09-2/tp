package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SearchTagCommand;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class SearchTagCommandParserTest {

    private final SearchTagCommandParser parser = new SearchTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSearchTagCommand() {
        // no leading and trailing whitespaces
        SearchTagCommand expectedCommand =
                new SearchTagCommand(new TagContainsKeywordsPredicate(Arrays.asList("friend", "colleague")));
        assertParseSuccess(parser, "friend colleague", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friend \n \t colleague  \t", expectedCommand);

        // single keyword
        expectedCommand = new SearchTagCommand(new TagContainsKeywordsPredicate(Arrays.asList("friend")));
        assertParseSuccess(parser, "friend", expectedCommand);
    }
}
