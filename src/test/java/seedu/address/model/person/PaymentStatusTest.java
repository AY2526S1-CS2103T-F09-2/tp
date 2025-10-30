package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class PaymentStatusTest {

    @Test
    public void fromOptionalString_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> PaymentStatus.fromOptionalString(null));
    }

    @Test
    public void fromOptionalString_optionalEmpty_throwsNullPointerException() {
        assertThrows(IllegalArgumentException.class, () -> PaymentStatus.fromOptionalString(Optional.empty()));
    }

    @Test
    public void fromOptionalString_emptyPaymentStatus_throwsIllegalArgumentException() {
        Optional<String> emptyPaymentStatus = Optional.of("");
        assertThrows(IllegalArgumentException.class, () -> PaymentStatus.fromOptionalString(emptyPaymentStatus));
    }

    @Test
    public void fromOptionalString_invalidPaymentStatus_throwsIllegalArgumentException() {
        Optional<String> invalidPaymentStatus = Optional.of("true");
        assertThrows(IllegalArgumentException.class, () -> PaymentStatus.fromOptionalString(invalidPaymentStatus));
    }

    @Test
    public void isValidPaymentStatus() {
        // null payment status
        assertThrows(NullPointerException.class, () -> PaymentStatus.fromOptionalString(null));

        // empty payment status
        assertFalse(PaymentStatus.isValidPaymentStatus(Optional.empty()));

        // invalid payment status
        assertFalse(PaymentStatus.isValidPaymentStatus(opt(""))); // empty string
        assertFalse(PaymentStatus.isValidPaymentStatus(opt(" "))); // spaces only
        assertFalse(PaymentStatus.isValidPaymentStatus(opt("^"))); // only non-alphanumeric characters
        assertFalse(PaymentStatus.isValidPaymentStatus(opt("peter*"))); // contains non-alphanumeric characters
        assertFalse(PaymentStatus.isValidPaymentStatus(opt("peter jack"))); // alphabets only
        assertFalse(PaymentStatus.isValidPaymentStatus(opt("12345"))); // numbers only
        assertFalse(PaymentStatus.isValidPaymentStatus(opt("peter the 2nd"))); // alphanumeric characters
        assertFalse(PaymentStatus.isValidPaymentStatus(opt("Capital Tan"))); // with capital letters

        // valid payment status
        assertTrue(PaymentStatus.isValidPaymentStatus(opt("paid")));
        assertTrue(PaymentStatus.isValidPaymentStatus(opt("unpaid")));

        // First letter capital
        assertTrue(PaymentStatus.isValidPaymentStatus(opt("Paid")));
        assertTrue(PaymentStatus.isValidPaymentStatus(opt("Unpaid")));

        // Random casing
        assertTrue(PaymentStatus.isValidPaymentStatus(opt("pAiD")));
        assertTrue(PaymentStatus.isValidPaymentStatus(opt("uNpAiD")));

        // All capitalise
        assertTrue(PaymentStatus.isValidPaymentStatus(opt("PAID")));
        assertTrue(PaymentStatus.isValidPaymentStatus(opt("UNPAID")));

        // Multiple spaces
        assertTrue(PaymentStatus.isValidPaymentStatus(opt("   \n paid \n \t")));
        assertTrue(PaymentStatus.isValidPaymentStatus(opt("\nunpaid\n")));
    }

    /**
     * Wraps the given string in an {@code Optional}.
     *
     * @param value String to wrap
     * @return an {@code Optional} containing the given value
     */
    private Optional<String> opt(String value) {
        return Optional.of(value);
    }

    @Test
    public void update_nullPaymentStatus_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PaymentStatus(1).update(
                (Optional<PaymentStatus.PaymentStatusValue>) null));
    }

    @Test
    public void update_paidStatus_paymentStatusDecrementedByOne() {
        PaymentStatus paymentStatus = new PaymentStatus(1);
        PaymentStatus expected = new PaymentStatus(PaymentStatus.ZERO_OUTSTANDING_PAYMENTS);

        assertEquals(expected, paymentStatus.update(Optional.of(PaymentStatus.PaymentStatusValue.PAID)));
    }

    @Test
    public void update_unpaidStatus_paymentStatusIncrementedByOne() {
        PaymentStatus paymentStatus = new PaymentStatus(1);
        PaymentStatus expected = new PaymentStatus(2);

        assertEquals(expected, paymentStatus.update(Optional.of(PaymentStatus.PaymentStatusValue.UNPAID)));
    }

    @Test
    public void equals() {
        PaymentStatus paymentStatus = new PaymentStatus(1);

        // same instance
        assertEquals(paymentStatus, paymentStatus);

        // same value
        assertEquals(new PaymentStatus(1), paymentStatus);

        // different value
        assertNotEquals(new PaymentStatus(2), paymentStatus);

        // not PaymentStatus instance
        assertNotEquals(null, paymentStatus);
    }

    @Test
    public void isValidPaymentStatus_validPaymentStatus_success() {
        assertTrue(PaymentStatus.isValidPaymentStatus("1"));
        assertTrue(PaymentStatus.isValidPaymentStatus(" 12 "));
    }

    @Test
    public void isValidPaymentStatus_invalidPaymentStatus_returnsFalse() {
        assertFalse(PaymentStatus.isValidPaymentStatus("abcd"));
    }

    @Test
    public void testGetZeroPaymentStatus() {
        PaymentStatus zeroStatus = PaymentStatus.getZeroPaymentStatus();
        assertEquals("All lessons have been paid", zeroStatus.toString());
    }

    @Test
    public void toString_formatsSingularPluralAndOverpaid() {
        // singular unpaid
        assertEquals("1 unpaid lesson", new PaymentStatus(1).toString());

        // plural unpaid
        assertEquals("3 unpaid lessons", new PaymentStatus(3).toString());

        // overpaid (negative outstanding)
        assertEquals("2 overpaid lessons", new PaymentStatus(-2).toString());
    }
}
