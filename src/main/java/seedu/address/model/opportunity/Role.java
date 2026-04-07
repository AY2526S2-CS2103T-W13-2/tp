package seedu.address.model.opportunity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Role in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {

    public static final String MESSAGE_CONSTRAINTS =
        "Role names must be 1-80 characters and cannot contain the forward slash (/) character, "
            + "as it is reserved for command syntax. All other characters are allowed.";

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 80;

    /*
     * Simplified validation: block only forward slash (/) which conflicts with CLI prefix syntax.
     * First character cannot be whitespace (enforced by trim()).
     * All other characters (letters, digits, symbols, Unicode, emoji) are allowed.
     */
    public static final String VALIDATION_REGEX = "[^/\\s][^/]*";

    public final String roleName;

    /**
     * Constructs a {@code Role}.
     *
     * @param role A valid role name.
     */
    public Role(String role) {
        requireNonNull(role);
        String trimmedRole = role.trim();
        checkArgument(isValidRole(trimmedRole), MESSAGE_CONSTRAINTS);
        this.roleName = trimmedRole;
    }

    /**
     * Returns true if a given string is a valid role name.
     * @param test The string to be tested.
     * @return true if the string is a valid role name, false otherwise.
     */
    public static boolean isValidRole(String test) {
        requireNonNull(test);
        String trimmedTest = test.trim();
        return trimmedTest.length() >= MIN_LENGTH
            && trimmedTest.length() <= MAX_LENGTH
            && trimmedTest.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return roleName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return roleName.equals(otherRole.roleName);
    }

    @Override
    public int hashCode() {
        return roleName.hashCode();
    }

}
