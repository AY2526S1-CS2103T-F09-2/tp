package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.SearchTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new {@link SearchTagCommand} object.
 *
 * <p>The parser expects one or more keywords separated by spaces, which will be used
 * to filter persons based on their tags. The search is case-insensitive and matches
 * substrings within tag names.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * searchtag friend colleague
 * </pre>
 */
public class SearchTagCommandParser implements Parser<SearchTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@link SearchTagCommand}
     * and returns a {@link SearchTagCommand} object for execution.
     *
     * @param args The input arguments provided by the user (keywords separated by spaces).
     * @return A {@link SearchTagCommand} containing a predicate to filter persons by tags.
     * @throws ParseException if the input is empty or only contains whitespace.
     */
    public SearchTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchTagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new SearchTagCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}
