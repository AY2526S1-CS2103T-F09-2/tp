package seedu.address.storage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Lesson;
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

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("lessonDate") String lesson,
            @JsonProperty("paymentStatus") String paymentStatus,
            @JsonProperty("educationLevel") String educationLevel) {
        super(name, phone, email, address, tags, educationLevel);
        this.lesson = lesson;
        this.paymentStatus = paymentStatus;
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        super(source); // call JsonAdaptedA constructor
        this.lesson = source.getNextLesson().getLessonDate();
        this.paymentStatus = String.valueOf(source.getPaymentStatus().getOutstandingLessonPayments());
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's
     * {@code Student} object.
     *
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

        if (lesson == null) {
            tmpLesson = Lesson.getEmpty();
        } else {
            tmpLesson = new Lesson(lesson);
        }

        if (!PaymentStatus.isValidString(paymentStatus)) {
            throw new IllegalValueException("paymentStatus error in json");
        }

        if (paymentStatus == null) {
            outstandingPayments = 0;
        } else {
            outstandingPayments = Integer.parseInt(paymentStatus);
        }

        return new Student(modelName, modelPhone, modelEmail, modelAddress, modelTags,
                tmpLesson, outstandingPayments, model.getEducationLevel());
    }

}
