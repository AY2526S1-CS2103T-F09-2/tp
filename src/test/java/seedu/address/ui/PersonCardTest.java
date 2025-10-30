package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.Lesson;
import seedu.address.model.RecurringLesson;
import seedu.address.model.person.Student;
import seedu.address.testutil.StudentBuilder;

public class PersonCardTest {

    @BeforeAll
    public static void initJfx() {
        try {
            Platform.startup(() -> {
            });
        } catch (IllegalStateException e) {
            // JavaFX platform already started; ignore
        }
    }

    private Label getPrivateLabel(PersonCard card, String fieldName) throws Exception {
        Field f = PersonCard.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        return (Label) f.get(card);
    }

    @Test
    public void lessonLabel_displaysNormalLessonDate() throws Exception {
        Student s = new StudentBuilder().withNewLesson(new Lesson("2025-11-02")).build();
        PersonCard card = new PersonCard(s, 1);
        Node root = card.getRoot(); // ensure FXML loaded
        assertNotNull(root);
        Label lessonLabel = getPrivateLabel(card, "lessonLabel");
        assertEquals("Lesson: 2025-11-02", lessonLabel.getText());
    }

    @Test
    public void lessonLabel_displaysRecurringLessonWithInterval() throws Exception {
        Student s = new StudentBuilder().withNewLesson(new RecurringLesson("2025-11-02", 1)).build();
        PersonCard card = new PersonCard(s, 1);
        Node root = card.getRoot();
        assertNotNull(root);
        Label lessonLabel = getPrivateLabel(card, "lessonLabel");
        assertEquals("Lesson: 2025-11-02 (every 1 day)", lessonLabel.getText());
    }

    @Test
    public void lessonLabel_displaysNoneWhenEmpty() throws Exception {
        Student s = new StudentBuilder().withNewLesson(Lesson.getEmpty()).build();
        PersonCard card = new PersonCard(s, 1);
        Node root = card.getRoot();
        assertNotNull(root);
        Label lessonLabel = getPrivateLabel(card, "lessonLabel");
        assertEquals("Lesson: None", lessonLabel.getText());
    }
}
