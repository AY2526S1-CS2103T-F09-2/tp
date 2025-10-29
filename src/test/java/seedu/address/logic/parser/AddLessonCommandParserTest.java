package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddLessonCommand;
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
}
