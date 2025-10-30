package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Lesson;
import seedu.address.model.RecurringLesson;

public class AddLessonCommandParserTest {
    private AddLessonCommandParser parser = new AddLessonCommandParser();

    @Test
    public void parse_normal_success() throws Exception {
        String input = " 1 l/2025-11-02";
        assertEquals(new AddLessonCommand(Index.fromOneBased(1), new Lesson("2025-11-02")),
                parser.parse(input));
    }

    @Test
    public void parse_recurring_success() throws Exception {
        String input = " 1 l/2025-11-02 every/7";
        assertEquals(new AddLessonCommand(Index.fromOneBased(1),
                new RecurringLesson("2025-11-02", 7)), parser.parse(input));
    }

    @Test
    public void parse_recurringWithOneDay_success() throws Exception {
        String input = " 1 l/2025-11-02 every/1";
        assertEquals(new AddLessonCommand(Index.fromOneBased(1),
                new RecurringLesson("2025-11-02", 1)), parser.parse(input));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        String input = " l/2025-11-02";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_missingLessonPrefix_throwsParseException() {
        String input = " 1 2025-11-02";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        String input = " 1 l/2025-13-01"; // Invalid month
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        String input = " 1 l/01-11-2025"; // Wrong format
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_pastDate_throwsParseException() {
        String input = " 1 l/2020-11-02"; // Past date
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_invalidInterval_throwsParseException() {
        String input = " 1 l/2025-11-02 every/abc";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_negativeInterval_throwsParseException() {
        String input = " 1 l/2025-11-02 every/-1";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_zeroInterval_throwsParseException() {
        String input = " 1 l/2025-11-02 every/0";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String input = " 0 l/2025-11-02"; // Index must be positive
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_nonNumericIndex_throwsParseException() {
        String input = " abc l/2025-11-02";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }
}
