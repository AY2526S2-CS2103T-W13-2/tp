package seedu.address.model.opportunity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents an opportunity's deadline in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {

    public static final String MESSAGE_CONSTRAINTS =
        "Deadline must be a valid date in YYYY-MM-DD.";

    public final LocalDate date;

    /**
     * Constructs a {@code Deadline}.
     *
     * @param deadline A valid deadline in YYYY-MM-DD format.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        checkArgument(isValidDeadline(deadline), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(deadline);
    }

    /**
     * Returns true if a given string is a valid deadline in YYYY-MM-DD format.
     * @param test The string to be tested.
     * @return true if the string is a valid deadline, false otherwise.
     */
    public static boolean isValidDeadline(String test) {
        requireNonNull(test);
        try {
            LocalDate.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Deadline)) {
            return false;
        }

        Deadline otherDeadline = (Deadline) other;
        return date.equals(otherDeadline.date);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
