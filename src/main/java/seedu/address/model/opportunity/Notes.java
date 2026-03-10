package seedu.address.model.opportunity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the notes for an opportunity.
 * Guarantees: immutable; is valid as declared in {@link #isValidNotes(String)}
 */
public class Notes {

    public static final String MESSAGE_CONSTRAINTS =
        "Notes must be 1-200 characters.";

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 200;

    public final String notes;

    /**
     * Constructs a {@code Notes}.
     *
     * @param notes A valid set of notes.
     */
    public Notes(String notes) {
        requireNonNull(notes);
        String trimmedNotes = notes.trim();
        checkArgument(isValidNotes(trimmedNotes), MESSAGE_CONSTRAINTS);
        this.notes = trimmedNotes;
    }

    /**
     * Returns true if a given string is a valid set of notes.
     * @param test The string to be tested.
     * @return true if the string is a valid set of notes, false otherwise.
     */
    public static boolean isValidNotes(String test) {
        requireNonNull(test);
        String trimmedTest = test.trim();
        return trimmedTest.length() >= MIN_LENGTH
            && trimmedTest.length() <= MAX_LENGTH;
    }

    @Override
    public String toString() {
        return notes;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Notes)) {
            return false;
        }

        Notes otherNotes = (Notes) other;
        return notes.equals(otherNotes.notes);
    }

    @Override
    public int hashCode() {
        return notes.hashCode();
    }
}
