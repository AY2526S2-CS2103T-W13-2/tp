package seedu.address.model.opportunity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Company(null));
    }

    @Test
    public void constructor_invalidCompany_throwsIllegalArgumentException() {
        String invalidCompany = "";
        assertThrows(IllegalArgumentException.class, () -> new Company(invalidCompany));
    }

    @Test
    public void isValidCompany() {
        // null company
        assertThrows(NullPointerException.class, () -> Company.isValidCompany(null));

        // invalid companies
        assertFalse(Company.isValidCompany("")); // empty string
        assertFalse(Company.isValidCompany(" ")); // spaces only
        assertFalse(Company.isValidCompany("Google/Microsoft")); // contains forward slash
        assertFalse(Company.isValidCompany("Tech / HR")); // forward slash with spaces
        assertFalse(Company.isValidCompany("a".repeat(61))); // exceeds max length of 60
        assertFalse(Company.isValidCompany("/Company")); // starts with forward slash
        assertFalse(Company.isValidCompany("/")); // only forward slash

        // valid companies - basic
        assertTrue(Company.isValidCompany("Google")); // single word
        assertTrue(Company.isValidCompany("Apple Inc.")); // with period
        assertTrue(Company.isValidCompany("a")); // minimal length
        assertTrue(Company.isValidCompany("ABC")); // abbreviation

        // valid companies - with traditional punctuation
        assertTrue(Company.isValidCompany("Johnson & Johnson")); // ampersand
        assertTrue(Company.isValidCompany("AT&T")); // abbreviation with ampersand
        assertTrue(Company.isValidCompany("Apple Inc.")); // period
        assertTrue(Company.isValidCompany("Procter & Gamble")); // ampersand
        assertTrue(Company.isValidCompany("Ben & Jerry's")); // ampersand and apostrophe
        assertTrue(Company.isValidCompany("McDonald's")); // apostrophe
        assertTrue(Company.isValidCompany("Trader Joe's")); // apostrophe
        assertTrue(Company.isValidCompany("L'Oréal")); // apostrophe in French name

        // valid companies - with special characters (now allowed)
        assertTrue(Company.isValidCompany("3M Company")); // starts with digit
        assertTrue(Company.isValidCompany("7-Eleven")); // digit and hyphen
        assertTrue(Company.isValidCompany("21st Century Fox")); // digit at start
        assertTrue(Company.isValidCompany("@Company")); // at symbol
        assertTrue(Company.isValidCompany("#Startup")); // hash symbol
        assertTrue(Company.isValidCompany("$100M Startup")); // dollar sign
        assertTrue(Company.isValidCompany("Company+")); // plus sign
        assertTrue(Company.isValidCompany("C++ Technologies")); // plus signs
        assertTrue(Company.isValidCompany("???")); // question marks (placeholder)
        assertTrue(Company.isValidCompany("...")); // ellipsis (placeholder)
        assertTrue(Company.isValidCompany("(TBD)")); // placeholder
        assertTrue(Company.isValidCompany("Company*")); // asterisk
        assertTrue(Company.isValidCompany("123")); // all digits
        assertTrue(Company.isValidCompany("!!!")); // exclamation marks
        assertTrue(Company.isValidCompany("[Company]")); // brackets

        // valid companies - parentheses and commas
        assertTrue(Company.isValidCompany("Company (Singapore)")); // location in parentheses
        assertTrue(Company.isValidCompany("ABC Corp., Ltd.")); // comma and period
        assertTrue(Company.isValidCompany("XYZ (Pte.) Ltd.")); // complex structure

        // valid companies - Unicode and international
        assertTrue(Company.isValidCompany("北京公司")); // Chinese characters
        assertTrue(Company.isValidCompany("Société Générale")); // French with accents
        assertTrue(Company.isValidCompany("München GmbH")); // German with umlaut
        assertTrue(Company.isValidCompany("Москва")); // Cyrillic

        // valid companies - edge cases
        assertTrue(Company.isValidCompany("a".repeat(60))); // exactly max length
        assertTrue(Company.isValidCompany("-Company-")); // hyphens
        assertTrue(Company.isValidCompany("(Company)")); // parentheses only
    }

    @Test
    public void equals() {
        Company company = new Company("Google");

        // same values -> returns true
        assertTrue(company.equals(new Company("Google")));

        // same object -> returns true
        assertTrue(company.equals(company));

        // null -> returns false
        assertFalse(company.equals(null));

        // different types -> returns false
        assertFalse(company.equals(5.0f));

        // different values -> returns false
        assertFalse(company.equals(new Company("Microsoft")));
    }
}
