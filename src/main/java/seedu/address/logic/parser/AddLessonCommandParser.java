package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Lesson;
import seedu.address.model.RecurringLesson;

/**
 * Parses input arguments and creates a new AddLessonCommand object.
 */
public class AddLessonCommandParser implements Parser<AddLessonCommand> {

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
        String nowStr = LocalDate.now().toString();
        Lesson lesson;

        try {
            LocalDate.parse(lessonDate);
            if (!Lesson.isValidLessonDate(lessonDate)) {
                throw new ParseException(
                        "Invalid date: must be in yyyy-MM-DD format within 364 days from now (" + nowStr + ")");
            }
        } catch (DateTimeParseException e) {
            if (lessonDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                try {
                    String[] parts = lessonDate.split("-");
                    int y = Integer.parseInt(parts[0]);
                    int m = Integer.parseInt(parts[1]);
                    int d = Integer.parseInt(parts[2]);
                    YearMonth ym = YearMonth.of(y, m);
                    // day is invalid for this month
                    boolean dayInvalid = d < 1 || d > ym.lengthOfMonth();
                    if (dayInvalid) {
                        LocalDate firstOfMonth = ym.atDay(1);
                        LocalDate lastOfMonth = ym.atEndOfMonth();
                        LocalDate now = LocalDate.now();
                        LocalDate upper = now.plusDays(364);
                        boolean monthIntersects = (!firstOfMonth.isAfter(upper)) && (!lastOfMonth.isBefore(now));
                        if (monthIntersects) {
                            throw new ParseException(
                                    "Invalid date: must be a real calendar date in yyyy-MM-DD format within 364 "
                                            + "days from now (" + nowStr + ")");
                        }
                    }
                } catch (Exception ex) {
                    if (ex instanceof ParseException) {
                        throw (ParseException) ex;
                    }
                }
            }

            throw new ParseException(
                    "Invalid date: must be in yyyy-MM-DD format within 364 days from now (" + nowStr + ")");
        }
        if (argMultimap.getValue(PREFIX_INTERVAL).isPresent()) {
            String intervalStr = argMultimap.getValue(PREFIX_INTERVAL).get().trim();
            if (!intervalStr.matches("\\d+")) {
                throw new ParseException("Interval must be a plain positive integer less than 365");
            }

            int interval = Integer.parseInt(intervalStr);

            if (interval <= 0 || interval >= 365) {
                throw new ParseException("Interval must be a positive integer less than 365");
            }

            lesson = new RecurringLesson(lessonDate, interval);
        } else {
            lesson = new Lesson(lessonDate);
        }

        return new AddLessonCommand(index, lesson);
    }

}
