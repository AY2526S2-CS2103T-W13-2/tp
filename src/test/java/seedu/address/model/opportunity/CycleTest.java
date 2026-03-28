package seedu.address.model.opportunity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CycleTest {

    @Test
    public void isValidCycle_null_throwsNullPointerException() {
        // EP: null parititon
        assertThrows(NullPointerException.class, () -> Cycle.isValidCycle(null));
    }

    @Test
    public void isValidCycle() {
        // EP: Invalid cycles (Violates strict Model domain constraints)
        assertFalse(Cycle.isValidCycle("")); // EP: empty string
        assertFalse(Cycle.isValidCycle(" ")); // EP: spaces only
        assertFalse(Cycle.isValidCycle("SUMMER")); // EP: missing year
        assertFalse(Cycle.isValidCycle("AUTUMN 2026")); // EP: outside the 4 accepted values
        assertFalse(Cycle.isValidCycle("summer 2026")); // EP: wrong case (Model is strictly case-sensitive!)

        // EP: Invalid year lengths (Boundary Value Analysis on the 4-digit constraint)
        assertFalse(Cycle.isValidCycle("SUMMER 26")); // EP: year too short
        assertFalse(Cycle.isValidCycle("SUMMER 20266")); // EP: year too long

        // EP: Invalid spacing
        assertFalse(Cycle.isValidCycle(" SUMMER 2026")); // EP: leading space
        assertFalse(Cycle.isValidCycle("SUMMER  2026")); // EP: multiple spaces

        // EP: Valid cycles (Exactly one of the 4 accepted prefixes + space + 4 digits)
        assertTrue(Cycle.isValidCycle("SUMMER 2025"));
        assertTrue(Cycle.isValidCycle("WINTER 2026"));
        assertTrue(Cycle.isValidCycle("S1 2027"));
        assertTrue(Cycle.isValidCycle("S2 2028"));
    }

}
