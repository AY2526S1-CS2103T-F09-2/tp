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

    private static String normalize(String s) {
        return s == null ? null : s.toLowerCase().replaceAll("\\s+", "");
    }

    private static void putKey(String key, EducationLevel level) {
        STRING_TO_LEVEL.put(normalize(key), level);
    }

    static {
        // Primary
        putKey("primary 1", PRIMARY_1);
        putKey("primary one", PRIMARY_1);
        putKey("pri 1", PRIMARY_1);
        putKey("pri one", PRIMARY_1);

        putKey("primary 2", PRIMARY_2);
        putKey("primary two", PRIMARY_2);
        putKey("pri 2", PRIMARY_2);
        putKey("pri two", PRIMARY_2);

        putKey("primary 3", PRIMARY_3);
        putKey("primary three", PRIMARY_3);
        putKey("pri 3", PRIMARY_3);
        putKey("pri three", PRIMARY_3);

        putKey("primary 4", PRIMARY_4);
        putKey("primary four", PRIMARY_4);
        putKey("pri 4", PRIMARY_4);
        putKey("pri four", PRIMARY_4);

        putKey("primary 5", PRIMARY_5);
        putKey("primary five", PRIMARY_5);
        putKey("pri 5", PRIMARY_5);
        putKey("pri five", PRIMARY_5);

        putKey("primary 6", PRIMARY_6);
        putKey("primary six", PRIMARY_6);
        putKey("pri 6", PRIMARY_6);
        putKey("pri six", PRIMARY_6);

        // Secondary
        putKey("secondary 1", SEC_1);
        putKey("secondary one", SEC_1);
        putKey("sec 1", SEC_1);
        putKey("sec one", SEC_1);

        putKey("secondary 2", SEC_2);
        putKey("secondary two", SEC_2);
        putKey("sec 2", SEC_2);
        putKey("sec two", SEC_2);

        putKey("secondary 3", SEC_3);
        putKey("secondary three", SEC_3);
        putKey("sec 3", SEC_3);
        putKey("sec three", SEC_3);

        putKey("secondary 4", SEC_4);
        putKey("secondary four", SEC_4);
        putKey("sec 4", SEC_4);
        putKey("sec four", SEC_4);

        putKey("secondary 5", SEC_5);
        putKey("secondary five", SEC_5);
        putKey("sec 5", SEC_5);
        putKey("sec five", SEC_5);

        // Junior College
        putKey("j1", J1);
        putKey("junior 1", J1);
        putKey("jc 1", J1);

        putKey("j2", J2);
        putKey("junior 2", J2);
        putKey("jc 2", J2);

        // Other
        putKey("other", OTHER);
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
        String key = normalize(s);
        return STRING_TO_LEVEL.getOrDefault(key, OTHER);
    }
}
