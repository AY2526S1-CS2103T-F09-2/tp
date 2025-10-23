package seedu.address.model.person;

import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Lesson;
import seedu.address.model.tag.Tag;

/**
 * Represents a Student in the address book.
 */
public class Student extends Person {

    private final PaymentStatus paymentStatus;
    private Lesson nextLesson;

    /**
     * Constructs a new Student object
     */
    public Student(Name name, Phone phone, Email email,
                   Address address, Set<Tag> tags, Lesson nextLesson) {
        super(name, phone, email, address, tags);
        this.paymentStatus = new PaymentStatus(PaymentStatus.ZERO_OUTSTANDING_PAYMENTS);
        this.nextLesson = nextLesson;
    }

    /**
     * Constructs a new Student object
     */
    public Student(Name name, Phone phone, Email email,
                   Address address, Set<Tag> tags, Lesson nextLesson,
                   int outstandingLessons, EducationLevel educationLevel) {
        super(name, phone, email, address, tags, educationLevel);
        this.paymentStatus = new PaymentStatus(outstandingLessons);
        this.nextLesson = nextLesson;
    }


    /**
     * Constructs a new Student object.
     */
    private Student(Student student, PaymentStatus paymentStatus) {
        super(student.getName(), student.getPhone(), student.getEmail(), student.getAddress(), student.getTags());
        this.paymentStatus = paymentStatus;
        this.nextLesson = student.getNextLesson();
    }


    /**
     * Constructs a new Student carrying over payment status and education level.
     */
    public Student(Name name, Phone phone, Email email,
                   Address address, Set<Tag> tags, Lesson nextLesson,
                   PaymentStatus paymentStatus, EducationLevel educationLevel) {
        super(name, phone, email, address, tags, educationLevel);
        this.paymentStatus = paymentStatus;
        this.nextLesson = nextLesson;
    }

    /**
     *  This method shows the string representation of the Student object
     */
    @Override
    public String toString() {
        String personString = super.toString();
        return new ToStringBuilder(personString)
                .add("Outstanding lesson payment", paymentStatus.getOutstandingLessonPayments())
                .add("Next Lesson", nextLesson)
                .toString();
    }

    public Lesson getNextLesson() {
        return nextLesson;
    }

    /**
     * Returns payment status of student.
     */
    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    /**
     * Converts a Student into a new Student object with new paymentStatus to ensure immutability
     */
    public Student updatePaymentStatus(Student student, PaymentStatus paymentStatus) {
        return new Student(student, paymentStatus);
    }
}
