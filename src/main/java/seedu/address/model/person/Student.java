package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
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
     * Constructs a new Student carrying over payment status and education level.
     */
    public Student(Person person) {
        super(person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), person.getTags(), person.getEducationLevel());
        this.paymentStatus = PaymentStatus.getZeroPaymentStatus();
        this.nextLesson = Lesson.getEmpty();
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
     * Converts the current Student object into a new Student object with new paymentStatus to ensure immutability.
     */
    public Student updatePaymentStatus(PaymentStatus paymentStatus) {
        requireNonNull(paymentStatus);
        return new Student(this, paymentStatus);
    }

    /**
     * Returns a new instance of Student who has paid for a Lesson.
     */
    public Student paid() throws CommandException {
        return new Student(this, this.paymentStatus.update(PaymentStatus.PaymentStatusValue.PAID));
    }

    /**
     * Returns a new instance of Student who has not paid for a Lesson.
     */
    public Student unpaid() throws CommandException {
        return new Student(this, this.paymentStatus.update(PaymentStatus.PaymentStatusValue.UNPAID));
    }

    /**
     * Returns a new instance of Student who has no Lesson.
     */
    public Student setEmptyLesson() {
        return new Student(
                this.getName(),
                this.getPhone(),
                this.getEmail(),
                this.getAddress(),
                this.getTags(),
                Lesson.getEmpty(),
                this.paymentStatus.getOutstandingLessonPayments(),
                this.getEducationLevel()
        );
    }
}
