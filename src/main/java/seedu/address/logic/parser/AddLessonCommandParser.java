package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Lesson;

/**
 * Parses input arguments and creates a new AddLessonCommand object
 */
public class AddLessonCommandParser implements Parser<AddLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddLessonCommand
     * and returns an AddLessonCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LESSON);

        // Expect an index in the preamble and a lesson prefix
        if (argMultimap.getPreamble().isEmpty() || argMultimap.getValue(PREFIX_LESSON).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LESSON);

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        String lessonDate = argMultimap.getValue(PREFIX_LESSON).get();
        Lesson lesson = new Lesson(lessonDate);

        return new AddLessonCommand(index, lesson);
    }

}
