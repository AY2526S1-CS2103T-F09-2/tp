package seedu.address.model.person;

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

    /**
     * Maps a free-form string to an EducationLevel enum, using common synonyms.
     * Returns OTHER for unrecognized inputs and UNKNOWN for null.
     */
    public static EducationLevel fromString(String s) {
        if (s == null) {
            return UNKNOWN;
        }
        String norm = s.trim().toLowerCase();
        switch (norm) {
        case "primary 1":
        case "primary one":
        case "pri one":
        case "pri 1":
            return PRIMARY_1;
        case "primary 2":
        case "primary two":
        case "pri two":
        case "pri 2":
            return PRIMARY_2;
        case "primary 3":
        case "primary three":
        case "pri three":
        case "pri 3":
            return PRIMARY_3;
        case "primary 4":
        case "primary four":
        case "pri four":
        case "pri 4":
            return PRIMARY_4;
        case "primary 5":
        case "primary five":
        case "pri five":
        case "pri 5":
            return PRIMARY_5;
        case "primary 6":
        case "primary six":
        case "pri six":
        case "pri 6":
            return PRIMARY_6;

        // Secondary 1–5
        case "secondary 1":
        case "secondary one":
        case "sec 1":
        case "sec one":
            return SEC_1;
        case "secondary 2":
        case "secondary two":
        case "sec 2":
        case "sec two":
            return SEC_2;
        case "secondary 3":
        case "secondary three":
        case "sec 3":
        case "sec three":
            return SEC_3;
        case "secondary 4":
        case "secondary four":
        case "sec 4":
        case "sec four":
            return SEC_4;
        case "secondary 5":
        case "secondary five":
        case "sec 5":
        case "sec five":
            return SEC_5;

        // Junior College J1–J2
        case "j1":
        case "junior 1":
        case "jc 1":
            return J1;
        case "j2":
        case "junior 2":
        case "jc 2":
            return J2;
        case "other":
            return OTHER;
        default:
            return OTHER;
        }
    }
}
