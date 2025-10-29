package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Lesson;
import seedu.address.model.RecurringLesson;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AddLessonCommand object
 */
public class AddLessonCommandParser implements Parser<AddLessonCommand> {

    public static final String INTERVAL_ERROR = "Invalid interval days: it must be an integer between 1 and 364.";
    public static final String DATE_ERROR = "Invalid date: it must be of format:"
            + "YYYY-MM-DD and within 365 days from now";

    /**
     * Parses the given {@code String} of arguments in the context of the AddLessonCommand
     * and returns an AddLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LESSON, PREFIX_INTERVAL);

        // Only require name and lesson
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_LESSON)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_LESSON);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        String lessonDate = argMultimap.getValue(PREFIX_LESSON).get().trim();
        Lesson lesson;
        if (!Lesson.isValidLessonDate(lessonDate)) {
            throw new ParseException("Invalid date: must be in yyyy-MM-DD format within 364 days from now");
        }
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

        // Only pass name and lesson, not a constructed Student
        return new AddLessonCommand(name, lesson);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

