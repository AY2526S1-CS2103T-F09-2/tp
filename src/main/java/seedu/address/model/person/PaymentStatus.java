package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Optional;

/**
 * Represents a Person's payment status.
 * Guarantees: immutable; value is either {@code PAID} or {@code UNPAID},
 * and must be valid as checked by {@link #isValidPaymentStatus(Optional)}.
 */
public class PaymentStatus {

    /**
     * Enumerates the possible values of a student's payment status.
     * A student is either marked as {@code PAID} or {@code UNPAID}.
     */
    public enum PaymentStatusValue {
        PAID, UNPAID;

        @Override
        public String toString() {
            return this == PAID ? "paid" : "unpaid";
        }
    }

    public static final String MESSAGE_CONSTRAINTS =
            "If PaymentStatus s/ is included, it can only take 'paid' or 'unpaid' values";

    private final int outstandingLessonPayments;

    public PaymentStatus(int outstandingLessonPayments) {
        this.outstandingLessonPayments = outstandingLessonPayments;
    }

    /**
     * Converts an Optional&lt;String&gt; into an PaymentStatus.
     */
    public static Optional<PaymentStatusValue> fromOptionalString(Optional<String> optionalString) {
        requireNonNull(optionalString);
        checkArgument(isValidPaymentStatus(optionalString), MESSAGE_CONSTRAINTS);

        String status = optionalString.get().trim().toLowerCase();
        switch (status) {
        case "paid":
            return Optional.of(PaymentStatusValue.PAID);
        case "unpaid":
            return Optional.of(PaymentStatusValue.UNPAID);
        default:
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if statusString is valid.
     */
    public static boolean isValidPaymentStatus(Optional<String> statusString) {
        requireNonNull(statusString);
        if (statusString.isEmpty()) {
            return false;
        }
        String str = statusString.get().trim().toLowerCase();
        return str.equals("paid") || str.equals("unpaid");
    }

    /**
     * Returns a new {@code PaymentStatus} updated with the given {@code newStatus} value.
     * Guarantees: immutable;
     * @param newStatus {@code newStatus} value determines PaymentStatus action to perform.
     * @return A new {@code PaymentStatus} if {@code PaymentStatusValue} provided,
     *     else returns same {@code PaymentStatusValue}
     */
    public PaymentStatus update(Optional<PaymentStatusValue> newStatus) {
        requireNonNull(newStatus);

        if (newStatus.isEmpty()) {
            return this;
        }

        return switch (newStatus.get()) {
        case PAID -> this.paid();
        case UNPAID -> this.unpaid();
        };
    }

    private PaymentStatus paid() {
        return new PaymentStatus(outstandingLessonPayments - 1);
    }

    private PaymentStatus unpaid() {
        return new PaymentStatus(outstandingLessonPayments + 1);
    }

    public int getOutstandingLessonPayments() {
        return this.outstandingLessonPayments;
    }

    /**
     * Check if string value can be parsed as an integer so that {@code PaymentStatus} can be instantiated.
     */
    public static boolean isValidString(String paymentStatus) {
        paymentStatus = paymentStatus.trim();
        try {
            Integer.parseInt(paymentStatus);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PaymentStatus)) {
            return false;
        }

        PaymentStatus otherPaymentStatus = (PaymentStatus) other;
        return this.getOutstandingLessonPayments() == otherPaymentStatus.getOutstandingLessonPayments();
    }

    @Override
    public String toString() {
        if (outstandingLessonPayments == 0) {
            return "All lessons have been paid";
        } else if (outstandingLessonPayments < 0) {
            return outstandingLessonPayments + " overpaid lessons";
        } else {
            return outstandingLessonPayments + " unpaid lessons";
        }
    }
}
