package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Lesson;
import seedu.address.model.RecurringLesson;

/**
 * Parses input arguments and creates a new AddLessonCommand object.
 */
public class AddLessonCommandParser implements Parser<AddLessonCommand> {

    public static final String MESSAGE_INVALID_DATE = "Date should be in the format "
            + "YYYY-MM-DD and within 365 days from now";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddLessonCommand and returns an AddLessonCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LESSON, PREFIX_INTERVAL);

        // Expect an index in the preamble and a lesson prefix
        if (argMultimap.getPreamble().isEmpty() || argMultimap.getValue(PREFIX_LESSON).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        // Ensure lesson and interval prefixes are not duplicated
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LESSON, PREFIX_INTERVAL);

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        String lessonDate = argMultimap.getValue(PREFIX_LESSON).get().trim();
        Lesson lesson;
        if (!Lesson.isValidLessonDate(lessonDate)) {
            throw new ParseException("Invalid date: must be in yyyy-MM-DD format within 364 days from now");
        }
        if (argMultimap.getValue(PREFIX_INTERVAL).isPresent()) {
            String intervalStr = argMultimap.getValue(PREFIX_INTERVAL).get().trim();
            if (!RecurringLesson.isValidInterval(intervalStr)) {
                throw new ParseException("Interval must be a positive integer less than 365");
            }
            try {
                int interval = Integer.parseInt(intervalStr);
                lesson = new RecurringLesson(lessonDate, interval);
            } catch (NumberFormatException e) {
                throw new ParseException("Interval must be a valid number");
            }
        } else {
            lesson = new Lesson(lessonDate);
        }

        return new AddLessonCommand(index, lesson);
    }

}
