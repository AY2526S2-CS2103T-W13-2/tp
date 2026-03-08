package seedu.address.model.opportunity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the contact person's name for an opportunity.
 * Guarantees: immutable; is valid as declared in {@link #isValidContactName(String)}
 */
public class ContactName {

    public static final String MESSAGE_CONSTRAINTS =
        "Contact names must be 1-60 characters and can only contain letters, spaces, and "
            + "common symbols like ., ' and - and must not be blank";

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 60;

    /*
     * The first character of the contact name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alpha}][\\p{Alpha} .'-]*";

    public final String contactName;

    /**
     * Constructs a {@code ContactName}.
     *
     * @param contactName A valid contact name.
     */
    public ContactName(String contactName) {
        requireNonNull(contactName);
        String trimmedContactName = contactName.trim();
        checkArgument(isValidContactName(trimmedContactName), MESSAGE_CONSTRAINTS);
        this.contactName = trimmedContactName;
    }

    /**
     * Returns true if a given string is a valid contact name.
     * @param test The string to be tested.
     * @return true if the string is a valid contact name, false otherwise.
     */
    public static boolean isValidContactName(String test) {
        requireNonNull(test);
        String trimmedTest = test.trim();
        return trimmedTest.length() >= MIN_LENGTH
            && trimmedTest.length() <= MAX_LENGTH
            && trimmedTest.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return contactName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactName)) {
            return false;
        }

        ContactName otherContactName = (ContactName) other;
        return contactName.equals(otherContactName.contactName);
    }

    @Override
    public int hashCode() {
        return contactName.hashCode();
    }

}
