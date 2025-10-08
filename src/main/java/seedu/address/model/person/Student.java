package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.Lesson;
import seedu.address.model.tag.Tag;
/**
 * Represents a Student in the address book.
 */
public class Student extends Person {

    private boolean hasPaid;
    private Lesson nextLesson;

    /**
     * Creates a new Student object
     */
    public Student(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Lesson nextLesson) {
        super(name, phone, email, address, tags);
        this.hasPaid = false;
        this.nextLesson = nextLesson;
    }   
}
