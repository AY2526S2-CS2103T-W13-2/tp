package seedu.address.model.opportunity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        String invalidRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidRole() {
        // null role
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        // invalid roles
        assertFalse(Role.isValidRole("")); // empty string
        assertFalse(Role.isValidRole(" ")); // spaces only
        assertFalse(Role.isValidRole("SWE/ML Engineer")); // contains forward slash (no longer allowed!)
        assertFalse(Role.isValidRole("Frontend/Backend")); // forward slash with spaces
        assertFalse(Role.isValidRole("a".repeat(81))); // exceeds max length of 80
        assertFalse(Role.isValidRole("/Engineer")); // starts with forward slash
        assertFalse(Role.isValidRole("/")); // only forward slash

        // valid roles - basic
        assertTrue(Role.isValidRole("Software Engineer")); // multiple words
        assertTrue(Role.isValidRole("Engineer")); // single word
        assertTrue(Role.isValidRole("a")); // minimal length
        assertTrue(Role.isValidRole("SWE")); // abbreviation

        // valid roles - with traditional punctuation
        assertTrue(Role.isValidRole("Sr. Software Engineer")); // period in title
        assertTrue(Role.isValidRole("Software Engineer, Backend")); // comma
        assertTrue(Role.isValidRole("Engineer (Mobile)")); // parentheses for specialization
        assertTrue(Role.isValidRole("R&D Engineer")); // ampersand
        assertTrue(Role.isValidRole("Full-Stack Engineer")); // hyphen
        assertTrue(Role.isValidRole("Software Engineer's Role")); // apostrophe

        // valid roles - workarounds for previously allowed slash
        assertTrue(Role.isValidRole("SWE-ML Engineer")); // hyphen instead of slash
        assertTrue(Role.isValidRole("SWE & ML Engineer")); // ampersand instead of slash
        assertTrue(Role.isValidRole("SWE (ML) Engineer")); // parentheses instead of slash
        assertTrue(Role.isValidRole("Frontend-Backend Engineer")); // hyphen instead of slash

        // valid roles - with special characters (now allowed)
        assertTrue(Role.isValidRole("C++ Developer")); // plus signs
        assertTrue(Role.isValidRole("C# Engineer")); // hash
        assertTrue(Role.isValidRole("@Engineer")); // at symbol
        assertTrue(Role.isValidRole("#TechLead")); // hash symbol
        assertTrue(Role.isValidRole("*StarPerformer*")); // asterisks
        assertTrue(Role.isValidRole("$Developer")); // dollar sign
        assertTrue(Role.isValidRole("???")); // question marks (placeholder)
        assertTrue(Role.isValidRole("...")); // ellipsis (placeholder)
        assertTrue(Role.isValidRole("(TBD)")); // placeholder
        assertTrue(Role.isValidRole("!!!")); // exclamation marks
        assertTrue(Role.isValidRole("[Developer]")); // brackets
        assertTrue(Role.isValidRole("{Engineer}")); // braces
        assertTrue(Role.isValidRole("Role:Value")); // colon
        assertTrue(Role.isValidRole("Item;List")); // semicolon

        // valid roles - starting with digits
        assertTrue(Role.isValidRole("1st Line Engineer")); // digit at start
        assertTrue(Role.isValidRole("2IC")); // digit at start
        assertTrue(Role.isValidRole("3D Modeler")); // digit at start

        // valid roles - complex real-world examples
        assertTrue(Role.isValidRole("Sr. Full-Stack Engineer (React & Node.js)")); // complex
        assertTrue(Role.isValidRole("Principal Engineer, AI & Machine Learning")); // very complex
        assertTrue(Role.isValidRole("VP, Engineering & Product Development")); // executive role

        // valid roles - Unicode and international
        assertTrue(Role.isValidRole("软件工程师")); // Chinese characters
        assertTrue(Role.isValidRole("Ingénieur Logiciel")); // French with accents
        assertTrue(Role.isValidRole("Инженер")); // Cyrillic

        // valid roles - edge cases
        assertTrue(Role.isValidRole("a".repeat(80))); // exactly max length
        assertTrue(Role.isValidRole("-Engineer-")); // hyphens
        assertTrue(Role.isValidRole("(Engineer)")); // parentheses only
        assertTrue(Role.isValidRole("&Engineer")); // ampersand at start
    }

    @Test
    public void equals() {
        Role role = new Role("Software Engineer");

        // same values -> returns true
        assertTrue(role.equals(new Role("Software Engineer")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(5.0f));

        // different values -> returns false
        assertFalse(role.equals(new Role("Data Scientist")));
    }
}
