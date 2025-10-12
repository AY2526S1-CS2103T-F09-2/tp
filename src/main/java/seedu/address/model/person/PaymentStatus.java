package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Optional;

/**
 * Represents a Person's payment status.
 * Guarantees: immutable; is  valid as declared in {@link #isValidPaymentStatus(Optional)}
 */
public enum PaymentStatus {
    PAID, UNPAID;

    public static final String MESSAGE_CONSTRAINTS =
            "If PaymentStatus s/ is included, it can only take 'paid' or 'unpaid' values";

    /**
     * Converts an Optional&lt;String&gt; into an PaymentStatus.
     */
    public static Optional<PaymentStatus> fromOptionalString(Optional<String> optionalString) {
        requireNonNull(optionalString);
        checkArgument(isValidPaymentStatus(optionalString), MESSAGE_CONSTRAINTS);

        String status = optionalString.get().trim().toLowerCase();
        switch (status) {
        case "paid":
            return Optional.of(PaymentStatus.PAID);
        case "unpaid":
            return Optional.of(PaymentStatus.UNPAID);
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

    @Override
    public String toString() {
        return this == PAID ? "paid" : "unpaid";
    }
}
