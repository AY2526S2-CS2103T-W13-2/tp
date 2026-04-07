package seedu.address.model.opportunity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid names
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("John/Doe")); // contains forward slash
        assertFalse(Name.isValidName("John / Doe")); // contains forward slash with spaces
        assertFalse(Name.isValidName("SWE/ML")); // forward slash (role-like string)
        assertFalse(Name.isValidName("a".repeat(61))); // exceeds max length of 60
        assertFalse(Name.isValidName("/John")); // starts with forward slash
        assertFalse(Name.isValidName("/")); // only forward slash

        // valid names - basic
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("Peter")); // single word
        assertTrue(Name.isValidName("a")); // minimal length

        // valid names - with already-supported punctuation
        assertTrue(Name.isValidName("Peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr")); // long names
        assertTrue(Name.isValidName("O'Brien")); // with apostrophe
        assertTrue(Name.isValidName("Jean-Paul")); // with hyphen
        assertTrue(Name.isValidName("O'Connor-Smith")); // with apostrophe and hyphen

        // valid names - with newly-added punctuation (periods)
        assertTrue(Name.isValidName("Mr. Smith")); // period in title
        assertTrue(Name.isValidName("Dr. John")); // period in title
        assertTrue(Name.isValidName("John Smith Jr.")); // period in suffix
        assertTrue(Name.isValidName("Ph.D.")); // multiple periods
        assertTrue(Name.isValidName("A.")); // single char with period

        // valid names - with newly-added punctuation (commas)
        assertTrue(Name.isValidName("John Doe, Jr.")); // comma in suffix
        assertTrue(Name.isValidName("Smith, John")); // comma separating parts
        assertTrue(Name.isValidName("Name, , Name")); // multiple commas

        // valid names - with newly-added punctuation (parentheses)
        assertTrue(Name.isValidName("Mary (Mei Ling)")); // alternate name in parentheses
        assertTrue(Name.isValidName("John (Jr)")); // suffix in parentheses
        assertTrue(Name.isValidName("李明 (Li Ming)")); // Unicode with parentheses

        // valid names - with newly-added punctuation (ampersand)
        assertTrue(Name.isValidName("R&D Team")); // ampersand in middle
        assertTrue(Name.isValidName("John & Jane")); // ampersand with spaces
        assertTrue(Name.isValidName("Smith & Sons")); // company-style name
        assertTrue(Name.isValidName("HR & Recruiting Dept")); // department name

        // valid names - starting with punctuation (placeholder support)
        assertTrue(Name.isValidName("...")); // ellipsis placeholder
        assertTrue(Name.isValidName("(TBD)")); // to-be-determined placeholder
        assertTrue(Name.isValidName("---")); // hyphen placeholder
        assertTrue(Name.isValidName("(Nickname) RealName")); // parenthesis at start
        assertTrue(Name.isValidName("'The Artist'")); // apostrophe at start
        assertTrue(Name.isValidName("&Sons")); // ampersand at start
        assertTrue(Name.isValidName("-Redacted-")); // hyphen at start
        assertTrue(Name.isValidName(".hidden")); // period at start
        assertTrue(Name.isValidName(",")); // single comma (weird but allowed)

        // valid names - starting with digits
        assertTrue(Name.isValidName("1st Place")); // digit at start
        assertTrue(Name.isValidName("1John")); // digit at start
        assertTrue(Name.isValidName("2pac")); // digit at start (artist name)
        assertTrue(Name.isValidName("50 Cent")); // digit at start

        // valid names - combined punctuation
        assertTrue(Name.isValidName("Dr. O'Brien-Smith, Jr.")); // combination of all allowed punctuation
        assertTrue(Name.isValidName("Mr. Jean-Paul D'Angelo (PhD)")); // complex real-world example
        assertTrue(Name.isValidName("Dr. Mary-Anne O'Connor, Ph.D.")); // another complex example

        // valid names - edge cases
        assertTrue(Name.isValidName("Jr.")); // title/suffix only
        assertTrue(Name.isValidName("Mary-Anne")); // starts with allowed character
        assertTrue(Name.isValidName("a".repeat(60))); // exactly max length

        // valid names - special characters now allowed (simplified validation)
        assertTrue(Name.isValidName("@John")); // at symbol
        assertTrue(Name.isValidName("#TeamLead")); // hash symbol
        assertTrue(Name.isValidName("*StarEmployee*")); // asterisk
        assertTrue(Name.isValidName("$RichPerson")); // dollar sign
        assertTrue(Name.isValidName("100%")); // percentage
        assertTrue(Name.isValidName("C++")); // plus signs
        assertTrue(Name.isValidName("C#")); // hash (programming context)
        assertTrue(Name.isValidName("???")); // question marks
        assertTrue(Name.isValidName("!!!")); // exclamation marks
        assertTrue(Name.isValidName("[Bracket]")); // brackets
        assertTrue(Name.isValidName("{Brace}")); // braces
        assertTrue(Name.isValidName("Name:Value")); // colon
        assertTrue(Name.isValidName("Item;List")); // semicolon

        // valid names - Unicode support (already supported, confirming still works)
        assertTrue(Name.isValidName("李明")); // Chinese characters
        assertTrue(Name.isValidName("Müller")); // German umlaut
        assertTrue(Name.isValidName("José")); // Spanish accented character
        assertTrue(Name.isValidName("Владимир")); // Cyrillic characters
    }
}
