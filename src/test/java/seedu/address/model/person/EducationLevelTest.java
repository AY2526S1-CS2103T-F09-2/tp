package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;



/**
 * Tests for {@link EducationLevel}
 *
 * Partitions identified for fromString(String s):
 * - P1: s is null -> returns UNKNOWN
 * - P2: s matches a PRIMARY synonym (e.g., "primary 1", "pri five") -> returns corresponding PRIMARY_x
 * - P3: s matches a SECONDARY synonym (e.g., "secondary three", "sec 4") -> returns SEC_x
 * - P4: s matches a JUNIOR COLLEGE synonym (e.g., "j1", "jc 2") -> returns J1/J2
 * - P5: s is exactly "other" (any case/whitespace) -> returns OTHER
 * - P6: s is an unrecognized non-null string -> returns OTHER
 *
 * Additional dimensions covered across partitions:
 * - Case-insensitivity (upper/mixed case inputs)
 * - Leading/trailing whitespace trimming
 */
public class EducationLevelTest {
    // P1
    @Test
    public void fromString_null_returnsUnknown() {
        assertEquals(EducationLevel.UNKNOWN, EducationLevel.fromString(null));
    }

    @Test
    // P2
    public void fromString_primarySynonyms_mapToPrimary() {
        // numeric form
        assertEquals(EducationLevel.PRIMARY_1, EducationLevel.fromString("primary 1"));

        // word form (mixed case)
        assertEquals(EducationLevel.PRIMARY_3, EducationLevel.fromString("PrImArY ThReE"));

        // abbreviation with trimming
        assertEquals(EducationLevel.PRIMARY_5, EducationLevel.fromString("  pri five  "));

        // boundary within primary range
        assertEquals(EducationLevel.PRIMARY_6, EducationLevel.fromString("PRI SIX"));
    }

    @Test
    // P3
    public void fromString_secondarySynonyms_mapToSecondary() {
        // numeric
        assertEquals(EducationLevel.SEC_1, EducationLevel.fromString("secondary 1"));

        // word form
        assertEquals(EducationLevel.SEC_3, EducationLevel.fromString("sec three"));

        // upper case numeric
        assertEquals(EducationLevel.SEC_4, EducationLevel.fromString("SEC 4"));

        // upper bound in secondary range
        assertEquals(EducationLevel.SEC_5, EducationLevel.fromString("Secondary Five"));
    }

    @Test
    // P4
    public void fromString_juniorCollegeSynonyms_mapToJc() {
        assertEquals(EducationLevel.J1, EducationLevel.fromString("j1"));
        assertEquals(EducationLevel.J1, EducationLevel.fromString("JC 1"));
        assertEquals(EducationLevel.J2, EducationLevel.fromString("junior 2"));
        assertEquals(EducationLevel.J2, EducationLevel.fromString("  jc 2  "));
    }

    @Test
    // P5
    public void fromString_otherKeyword_returnsOther() {
        assertEquals(EducationLevel.OTHER, EducationLevel.fromString("other"));
        assertEquals(EducationLevel.OTHER, EducationLevel.fromString("Other"));
        assertEquals(EducationLevel.OTHER, EducationLevel.fromString("  OTHER  "));
    }

    @Test
    // P6
    public void fromString_unrecognized_returnsOther() {
        assertEquals(EducationLevel.OTHER, EducationLevel.fromString("polytechnic"));
        assertEquals(EducationLevel.OTHER, EducationLevel.fromString("graduate"));
        assertEquals(EducationLevel.OTHER, EducationLevel.fromString(""));
        assertEquals(EducationLevel.OTHER, EducationLevel.fromString("  "));
    }

    @Test
    public void enumUtilityMethods_valuesAndValueOfCovered() {
        // Cover the synthetic enum methods
        EducationLevel[] all = EducationLevel.values();
        assertTrue(all.length > 0);
        // valueOf requires exact enum constant name
        assertEquals(EducationLevel.PRIMARY_1, EducationLevel.valueOf("PRIMARY_1"));
        assertEquals(EducationLevel.UNKNOWN, EducationLevel.valueOf("UNKNOWN"));
    }
}

