package seedu.address.model.person;

import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Lesson;
import seedu.address.model.tag.Tag;

/**
 * Represents a Student in the address book.
 */
public class Student extends Person {

    private boolean hasPaid;
    private Lesson nextLesson;

    /**
     * Constructs a new Student object
     */
    public Student(Name name, Phone phone, Email email,
            Address address, Set<Tag> tags, Lesson nextLesson) {
        super(name, phone, email, address, tags);
        this.hasPaid = false;
        this.nextLesson = nextLesson;
    }

    /**
     * Constructs a new Student object with PaymentStatus
     */
    public Student(Name name, Phone phone, Email email,
            Address address, Set<Tag> tags, Lesson nextLesson, PaymentStatus paymentStatus) {
        super(name, phone, email, address, tags, paymentStatus);
        this.hasPaid = false;
        this.nextLesson = nextLesson;
    }

    /**
     * This method shows the string representation of the Student object
     */
    @Override
    public String toString() {
        String personString = super.toString();
        return new ToStringBuilder(personString)
                .add("Has Paid", hasPaid)
                .add("Next Lesson", nextLesson)
                .toString();
    }

    public Lesson getNextLesson() {
        return nextLesson;
    }

    /**
     * Creates a new {@code Student} with same fields but containing new
     * {@code PaymentStatus}
     *
     * @param student              Student whose fields to copy over.
     * @param updatedPaymentStatus Payment Status to set for new student.
     * @return New Student with updated {@code PaymentStatus}.
     */
    public static Student withPaymentStatus(Student student, PaymentStatus updatedPaymentStatus) {
        return new Student(
                student.getName(),
                student.getPhone(),
                student.getEmail(),
                student.getAddress(),
                student.getTags(),
                student.getNextLesson(),
                updatedPaymentStatus);
    }
}
