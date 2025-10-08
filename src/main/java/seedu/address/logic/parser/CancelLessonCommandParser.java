package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CancelLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CancelLessonCommand object
 */
public class CancelLessonCommandParser implements Parser<CancelLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CancelLessonCommand
     * and returns an CancelLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CancelLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;

        try {
            index = ParserUtil.parseIndex(args.trim());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelLessonCommand.MESSAGE_USAGE), pe
            );
        }

        return new CancelLessonCommand(index);
    }
}
