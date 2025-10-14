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
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;

public class JsonAdaptedStudent extends JsonAdaptedPerson {
    
    private String lesson;   
    private String paymentStatus; // reserved until payment feature is implemented
    
    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("lessonDate") String lesson) {
        super(name, phone, email, address, tags);
        this.lesson = lesson;
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        super(source); // call JsonAdaptedA constructor
        this.lesson = source.getNextLesson().getLessonDate();
        this.paymentStatus = "unpaid";
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    @Override
    public Student toModelType() throws IllegalValueException{
        Person model = super.toModelType();
        Name modelName = model.getName();
        Phone modelPhone = model.getPhone();
        Email modelEmail = model.getEmail();
        Address modelAddress = model.getAddress();
        Set<Tag> modelTags = new HashSet<>(model.getTags());
        if (lesson != null) {
            return new Student(modelName, modelPhone, modelEmail, modelAddress, modelTags, new Lesson(lesson));
        } else {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Lesson.class.getSimpleName()));
        }
    }


}
