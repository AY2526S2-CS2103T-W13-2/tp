package seedu.address.model.opportunity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the contact detail for an opportunity.
 * Guarantees: immutable; is valid as declared in {@link #isValidContactDetail(String)}
 */
public class ContactDetail {

    public static final String MESSAGE_CONSTRAINTS =
        "Contact detail must be 1-120 characters.";

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 120;

    public final String contactDetail;

    /**
     * Constructs a {@code ContactDetail}.
     *
     * @param contactDetail A valid contact detail.
     */
    public ContactDetail(String contactDetail) {
        requireNonNull(contactDetail);
        String trimmedContactDetail = contactDetail.trim();
        checkArgument(isValidContactDetail(trimmedContactDetail), MESSAGE_CONSTRAINTS);
        this.contactDetail = trimmedContactDetail;
    }

    /**
     * Returns true if a given string is a valid contact detail.
     * @param test The string to be tested.
     * @return true if the string is a valid contact detail, false otherwise.
     */
    public static boolean isValidContactDetail(String test) {
        requireNonNull(test);
        String trimmedTest = test.trim();
        return trimmedTest.length() >= MIN_LENGTH
            && trimmedTest.length() <= MAX_LENGTH;
    }

    @Override
    public String toString() {
        return contactDetail;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactDetail)) {
            return false;
        }

        ContactDetail otherContactDetail = (ContactDetail) other;
        return contactDetail.equals(otherContactDetail.contactDetail);
    }

    @Override
    public int hashCode() {
        return contactDetail.hashCode();
    }
}
