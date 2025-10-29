package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Lesson;
import seedu.address.model.RecurringLesson;

/**
 * Parses input arguments and creates a new AddLessonCommand object
 */
public class AddLessonCommandParser implements Parser<AddLessonCommand> {

    public static final String INTERVAL_ERROR = "Invalid interval days: it must be an integer between 1 and 364.";
    public static final String DATE_ERROR = "Invalid date: it must be of format:"
            + "YYYY-MM-DD and within 365 days from now";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddLessonCommand
     * and returns an AddLessonCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LESSON, PREFIX_INTERVAL);

        // Expect an index in the preamble and a lesson prefix
        if (argMultimap.getPreamble().isEmpty() || argMultimap.getValue(PREFIX_LESSON).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LESSON);

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        String lessonDate = argMultimap.getValue(PREFIX_LESSON).get().trim();
        Lesson lesson;
        if (argMultimap.getValue(PREFIX_INTERVAL).isPresent()) {
            String interval = argMultimap.getValue(PREFIX_INTERVAL).get().trim();
            if (!RecurringLesson.isValidInterval(interval)) {
                throw new ParseException(AddLessonCommandParser.INTERVAL_ERROR);
            }
            if (!Lesson.isValidLessonDate(lessonDate)) {
                throw new ParseException(AddLessonCommandParser.DATE_ERROR);
            }
            int intervalDays = Integer.parseInt(interval);
            lesson = new RecurringLesson(lessonDate, intervalDays);
        } else {
            lesson = new Lesson(lessonDate);
        }

        return new AddLessonCommand(index, lesson);
    }

}
