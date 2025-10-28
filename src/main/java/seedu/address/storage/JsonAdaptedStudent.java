package seedu.address.storage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Lesson;
import seedu.address.model.RecurringLesson;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.PaymentStatus;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;

/**
 * Jason friendly version of student
 */
public class JsonAdaptedStudent extends JsonAdaptedPerson {

    private String lesson;
    private String paymentStatus;
    private int interval;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("lessonDate") String lesson,
            @JsonProperty("paymentStatus") String paymentStatus,
            @JsonProperty("educationLevel") String educationLevel, @JsonProperty("interval") int interval) {
        super(name, phone, email, address, tags, educationLevel);
        this.lesson = lesson;
        this.interval = interval;
        this.paymentStatus = paymentStatus;
    }
    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        super(source); // call JsonAdaptedA constructor
        this.lesson = source.getNextLesson().getLessonDate();
        this.interval = source.getNextLesson().getIntervalDays();
        this.paymentStatus = String.valueOf(source.getPaymentStatus().getOutstandingLessonPayments());
    }
    /**
     * Converts this Jackson-friendly adapted student object into the model's
     * {@code Student} object.
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted person.
     */
    @Override
    public Student toModelType() throws IllegalValueException {
        Person model = super.toModelType();
        Name modelName = model.getName();
        Phone modelPhone = model.getPhone();
        Email modelEmail = model.getEmail();
        Address modelAddress = model.getAddress();
        Set<Tag> modelTags = new HashSet<>(model.getTags());
        // Handle case where null fields in JSON
        Lesson tmpLesson;
        int outstandingPayments;
        tmpLesson = initilaiseLesson(this.lesson, this.interval);

        if (!PaymentStatus.isValidPaymentStatus(paymentStatus)) {
            throw new IllegalValueException("paymentStatus error in json");
        }

        if (paymentStatus == null) {
            outstandingPayments = PaymentStatus.ZERO_OUTSTANDING_PAYMENTS;
        } else {
            outstandingPayments = Integer.parseInt(paymentStatus);
        }
        return new Student(modelName, modelPhone, modelEmail, modelAddress, modelTags,
                tmpLesson, outstandingPayments, model.getEducationLevel());
    }
    /**
     * A helper method that helps to build a lesson from the JSON format
     * @param lessonString the date String stored in JSON
     * @param interval the interval between each recurring lesson(normal lesson = 0)
     * @return the lesson represented in JSON parameters
     */
    private Lesson initilaiseLesson(String lessonString, int interval) {
        if (this.lesson == null) {
            return Lesson.getEmpty();
        } else if (interval == 0) {
            return new Lesson(this.lesson);
        } else if (interval <= -1) {
            return Lesson.getEmpty();
        } else {
            assert interval > 0;
            return new RecurringLesson(lessonString, interval);
        }
    }

}

