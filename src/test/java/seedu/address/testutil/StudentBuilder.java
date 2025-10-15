package seedu.address.testutil;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.Lesson;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Ah Beng";
    public static final String DEFAULT_PHONE = "87651234";
    public static final String DEFAULT_EMAIL = "anBeng@gmail.com";
    public static final String DEFAULT_ADDRESS = "102, College Ave West 29, #04-123";
    private static final String DEFAULT_TAG = "sec3";
    private static final String DEFUALT_LESSON = "2025-10-07 14:00 Math";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Lesson lesson;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        lesson = new Lesson(DEFUALT_LESSON);
        tags.add(new Tag(DEFAULT_TAG));
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        address = studentToCopy.getAddress();
        tags = new HashSet<>(studentToCopy.getTags());
    }

    /**
     * builds a new student with default values for testing
     */
    public Student build() {
        return new Student(name, phone, email, address, tags, lesson);
    }

    /**
     * builds a new student and only change the lesson field.
     * @param newLesson the new lesson to be recorded.
     * @return the modified StudentBuilder instance.
     */
    public StudentBuilder withNewLesson(Lesson newLesson) {
        this.lesson = newLesson;
        return this;
    }

}
