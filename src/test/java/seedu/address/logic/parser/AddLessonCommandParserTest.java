package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.model.Lesson;
import seedu.address.model.RecurringLesson;
import seedu.address.model.person.Name;

public class AddLessonCommandParserTest {
    private AddLessonCommandParser parser = new AddLessonCommandParser();

    @Test
    public void parse_normal_success() throws Exception {
        String input = " n/John Doe l/2025-11-02";
        assertEquals(new AddLessonCommand(new Name("John Doe"), new Lesson("2025-11-02")),
                parser.parse(input));
    }
    @Test
    public void parse_recurring_success() throws Exception {
        String input = " n/John Doe l/2025-11-02 every/7";
        assertEquals(new AddLessonCommand(new Name("John Doe"),
                new RecurringLesson("2025-11-02", 7)), parser.parse(input));
    }
}
