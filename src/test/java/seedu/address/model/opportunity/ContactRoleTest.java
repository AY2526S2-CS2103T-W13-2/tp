package seedu.address.model.opportunity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ContactRoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ContactRole(null));
    }

    @Test
    public void constructor_invalidContactRole_throwsIllegalArgumentException() {
        String invalidContactRole = "";
        assertThrows(IllegalArgumentException.class, () -> new ContactRole(invalidContactRole));
    }

    @Test
    public void isValidContactRole() {
        // null contact role
        assertThrows(NullPointerException.class, () -> ContactRole.isValidContactRole(null));

        // invalid contact roles
        assertFalse(ContactRole.isValidContactRole("")); // empty string
        assertFalse(ContactRole.isValidContactRole(" ")); // spaces only
        assertFalse(ContactRole.isValidContactRole("Tech/HR")); // contains forward slash
        assertFalse(ContactRole.isValidContactRole("HR / Recruiting")); // contains forward slash with spaces
        assertFalse(ContactRole.isValidContactRole("a".repeat(51))); // exceeds max length of 50
        assertFalse(ContactRole.isValidContactRole("/recruiter")); // starts with forward slash
        assertFalse(ContactRole.isValidContactRole("/")); // only forward slash

        // valid contact roles - basic
        assertTrue(ContactRole.isValidContactRole("recruiter")); // single word
        assertTrue(ContactRole.isValidContactRole("hiring manager")); // multiple words
        assertTrue(ContactRole.isValidContactRole("a")); // minimal length
        assertTrue(ContactRole.isValidContactRole("HR")); // abbreviation

        // valid contact roles - with already-supported punctuation
        assertTrue(ContactRole.isValidContactRole("Co-Founder")); // with hyphen
        assertTrue(ContactRole.isValidContactRole("HR-Manager")); // hyphenated role

        // valid contact roles - with newly-added punctuation (periods)
        assertTrue(ContactRole.isValidContactRole("Sr. Recruiter")); // period in title
        assertTrue(ContactRole.isValidContactRole("Jr. Manager")); // period in title
        assertTrue(ContactRole.isValidContactRole("V.P.")); // multiple periods (Vice President)
        assertTrue(ContactRole.isValidContactRole("Ph.D. Supervisor")); // period in qualification

        // valid contact roles - with newly-added punctuation (commas)
        assertTrue(ContactRole.isValidContactRole("VP, Engineering")); // comma separator
        assertTrue(ContactRole.isValidContactRole("Manager, HR")); // comma separator
        assertTrue(ContactRole.isValidContactRole("Director, R&D")); // comma with abbreviation

        // valid contact roles - with newly-added punctuation (parentheses)
        assertTrue(ContactRole.isValidContactRole("Hiring Manager (Tech)")); // specialization in parentheses
        assertTrue(ContactRole.isValidContactRole("Recruiter (Finance)")); // department in parentheses
        assertTrue(ContactRole.isValidContactRole("Manager (HR & Recruiting)")); // complex specialization

        // valid contact roles - with newly-added punctuation (ampersands)
        assertTrue(ContactRole.isValidContactRole("HR & Recruiting")); // compound role with ampersand
        assertTrue(ContactRole.isValidContactRole("Research & Development")); // R&D expanded
        assertTrue(ContactRole.isValidContactRole("Talent & Operations")); // compound departments

        // valid contact roles - with newly-added punctuation (apostrophes)
        assertTrue(ContactRole.isValidContactRole("Director's Assistant")); // possessive form
        assertTrue(ContactRole.isValidContactRole("CEO's Office")); // possessive with office
        assertTrue(ContactRole.isValidContactRole("Manager's Deputy")); // possessive deputy

        // valid contact roles - starting with punctuation (placeholder support)
        assertTrue(ContactRole.isValidContactRole("...")); // ellipsis placeholder
        assertTrue(ContactRole.isValidContactRole("(TBD)")); // to-be-determined placeholder
        assertTrue(ContactRole.isValidContactRole("---")); // hyphen placeholder
        assertTrue(ContactRole.isValidContactRole("'Chief' Officer")); // apostrophe at start
        assertTrue(ContactRole.isValidContactRole("&Associates")); // ampersand at start
        assertTrue(ContactRole.isValidContactRole(".Manager")); // period at start
        assertTrue(ContactRole.isValidContactRole("(Tech) Lead")); // parenthesis at start
        assertTrue(ContactRole.isValidContactRole("-Assistant-")); // hyphen at start
        assertTrue(ContactRole.isValidContactRole(",")); // single comma (weird but allowed)

        // valid contact roles - starting with digits
        assertTrue(ContactRole.isValidContactRole("1st Line Manager")); // digit at start
        assertTrue(ContactRole.isValidContactRole("2IC")); // digit at start (Second-in-Command)
        assertTrue(ContactRole.isValidContactRole("3rd Party Recruiter")); // digit at start

        // valid contact roles - combined punctuation
        assertTrue(ContactRole.isValidContactRole("Sr. HR & Recruiting Manager")); // period and ampersand
        assertTrue(ContactRole.isValidContactRole("VP, Engineering & Operations")); // comma and ampersand
        assertTrue(ContactRole.isValidContactRole("Director, R&D (Tech)")); // comma, ampersand, parentheses
        assertTrue(ContactRole.isValidContactRole("Sr. Manager's Assistant (HR)")); // period, apostrophe, parentheses

        // valid contact roles - real-world examples
        assertTrue(ContactRole.isValidContactRole("Sr. HR & Talent Acquisition Manager")); // comprehensive example
        assertTrue(ContactRole.isValidContactRole("VP, Engineering (Mobile & Web)")); // complex structure
        assertTrue(ContactRole.isValidContactRole("Director's Office, R&D (AI)")); // very complex

        // valid contact roles - edge cases
        assertTrue(ContactRole.isValidContactRole("Sr.")); // title only
        assertTrue(ContactRole.isValidContactRole("R&D")); // abbreviation with ampersand
        assertTrue(ContactRole.isValidContactRole("a".repeat(50))); // exactly max length

        // valid contact roles - special characters now allowed (simplified validation)
        assertTrue(ContactRole.isValidContactRole("@recruiter")); // at symbol
        assertTrue(ContactRole.isValidContactRole("#TechLead")); // hash symbol
        assertTrue(ContactRole.isValidContactRole("C++ Developer")); // plus signs
        assertTrue(ContactRole.isValidContactRole("C# Engineer")); // hash in programming context
        assertTrue(ContactRole.isValidContactRole("*Lead*")); // asterisks
        assertTrue(ContactRole.isValidContactRole("???")); // question marks
        assertTrue(ContactRole.isValidContactRole("!!!")); // exclamation marks
        assertTrue(ContactRole.isValidContactRole("[Temp]")); // brackets
        assertTrue(ContactRole.isValidContactRole("recruiter*")); // asterisk now allowed

        // valid contact roles - with numbers
        assertTrue(ContactRole.isValidContactRole("L5 Manager")); // level indicator
        assertTrue(ContactRole.isValidContactRole("Recruiter 2")); // numbered role
    }
}
