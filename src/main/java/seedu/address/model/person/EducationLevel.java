package seedu.address.model.person;

import java.util.HashMap;
import java.util.Map;


/**
 * Represents the education level of a person.
 *
 * <p>The levels include primary school (PRIMARY_1 to PRIMARY_6), secondary school (SEC_1 to SEC_5),
 * junior college (J1, J2), as well as UNKNOWN and OTHER for unspecified or unrecognized levels.
 *
 * <p>This enum also provides a {@link #fromString(String)} method to map free-form input strings
 * to the corresponding {@code EducationLevel}, handling common synonyms and variations.
 */
public enum EducationLevel {
    UNKNOWN,
    PRIMARY_1,
    PRIMARY_2,
    PRIMARY_3,
    PRIMARY_4,
    PRIMARY_5,
    PRIMARY_6,
    SEC_1,
    SEC_2,
    SEC_3,
    SEC_4,
    SEC_5,
    J1,
    J2,
    OTHER;
    private static final Map<String, EducationLevel> STRING_TO_LEVEL = new HashMap<>();

    static {
        // Primary
        STRING_TO_LEVEL.put("primary 1", PRIMARY_1);
        STRING_TO_LEVEL.put("primary one", PRIMARY_1);
        STRING_TO_LEVEL.put("pri 1", PRIMARY_1);
        STRING_TO_LEVEL.put("pri one", PRIMARY_1);

        STRING_TO_LEVEL.put("primary 2", PRIMARY_2);
        STRING_TO_LEVEL.put("primary two", PRIMARY_2);
        STRING_TO_LEVEL.put("pri 2", PRIMARY_2);
        STRING_TO_LEVEL.put("pri two", PRIMARY_2);

        STRING_TO_LEVEL.put("primary 3", PRIMARY_3);
        STRING_TO_LEVEL.put("primary three", PRIMARY_3);
        STRING_TO_LEVEL.put("pri 3", PRIMARY_3);
        STRING_TO_LEVEL.put("pri three", PRIMARY_3);

        STRING_TO_LEVEL.put("primary 4", PRIMARY_4);
        STRING_TO_LEVEL.put("primary four", PRIMARY_4);
        STRING_TO_LEVEL.put("pri 4", PRIMARY_4);
        STRING_TO_LEVEL.put("pri four", PRIMARY_4);

        STRING_TO_LEVEL.put("primary 5", PRIMARY_5);
        STRING_TO_LEVEL.put("primary five", PRIMARY_5);
        STRING_TO_LEVEL.put("pri 5", PRIMARY_5);
        STRING_TO_LEVEL.put("pri five", PRIMARY_5);

        STRING_TO_LEVEL.put("primary 6", PRIMARY_6);
        STRING_TO_LEVEL.put("primary six", PRIMARY_6);
        STRING_TO_LEVEL.put("pri 6", PRIMARY_6);
        STRING_TO_LEVEL.put("pri six", PRIMARY_6);

        // Secondary
        STRING_TO_LEVEL.put("secondary 1", SEC_1);
        STRING_TO_LEVEL.put("secondary one", SEC_1);
        STRING_TO_LEVEL.put("sec 1", SEC_1);
        STRING_TO_LEVEL.put("sec one", SEC_1);

        STRING_TO_LEVEL.put("secondary 2", SEC_2);
        STRING_TO_LEVEL.put("secondary two", SEC_2);
        STRING_TO_LEVEL.put("sec 2", SEC_2);
        STRING_TO_LEVEL.put("sec two", SEC_2);

        STRING_TO_LEVEL.put("secondary 3", SEC_3);
        STRING_TO_LEVEL.put("secondary three", SEC_3);
        STRING_TO_LEVEL.put("sec 3", SEC_3);
        STRING_TO_LEVEL.put("sec three", SEC_3);

        STRING_TO_LEVEL.put("secondary 4", SEC_4);
        STRING_TO_LEVEL.put("secondary four", SEC_4);
        STRING_TO_LEVEL.put("sec 4", SEC_4);
        STRING_TO_LEVEL.put("sec four", SEC_4);

        STRING_TO_LEVEL.put("secondary 5", SEC_5);
        STRING_TO_LEVEL.put("secondary five", SEC_5);
        STRING_TO_LEVEL.put("sec 5", SEC_5);
        STRING_TO_LEVEL.put("sec five", SEC_5);

        // Junior College
        STRING_TO_LEVEL.put("j1", J1);
        STRING_TO_LEVEL.put("junior 1", J1);
        STRING_TO_LEVEL.put("jc 1", J1);

        STRING_TO_LEVEL.put("j2", J2);
        STRING_TO_LEVEL.put("junior 2", J2);
        STRING_TO_LEVEL.put("jc 2", J2);

        // Other
        STRING_TO_LEVEL.put("other", OTHER);
    }

    /**
     * This method attempts to handle common variations in user input, such as:
     * Numeric vs. word forms (e.g., "primary 1", "primary one")
     * Abbreviations (e.g., "pri 1", "sec 3")
     * Junior College variations (e.g., "J1", "junior 1", "JC 1")
     * If the input is {@code null} or does not match any known level, it will return
     * {@link EducationLevel#UNKNOWN} or {@link EducationLevel#OTHER}, depending on context.
     *
     * @param s the free-form string representing the user's education level input
     *
     * @return the corresponding {@code EducationLevel} enum, or {@code OTHER} if unrecognized,
     *      {@code UNKNOWN} if the input is {@code null}
     */

    public static EducationLevel fromString(String s) {
        if (s == null) {
            return UNKNOWN;
        }
        return STRING_TO_LEVEL.getOrDefault(s.trim().toLowerCase(), OTHER);
    }
}
