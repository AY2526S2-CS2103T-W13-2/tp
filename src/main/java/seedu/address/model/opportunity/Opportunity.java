package seedu.address.model.opportunity;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Opportunity in the address book.
 * Guarantees: required fields are present and not null, optional fields may be null,
 * field values are validated by their respective classes, immutable.
 */
public class Opportunity {

    // Required fields
    private final Company company;
    private final Role role;

    // Optional fields
    private final Deadline deadline;
    private final Status status;
    private final ContactName contactName;
    private final ContactDetail contactDetail;
    private final Notes notes;

    /**
     * Constructs an {@code Opportunity}.
     *
     * @param company Required company.
     * @param role Required role.
     * @param deadline Optional deadline.
     * @param status Optional status.
     * @param contactName Optional contact name.
     * @param contactDetail Optional contact detail.
     * @param notes Optional notes.
     */
    public Opportunity(Company company, Role role, Deadline deadline, Status status,
            ContactName contactName, ContactDetail contactDetail, Notes notes) {

        requireNonNull(company);
        requireNonNull(role);

        this.company = company;
        this.role = role;
        this.deadline = deadline;
        this.status = status;
        this.contactName = contactName;
        this.contactDetail = contactDetail;
        this.notes = notes;
    }

    public Company getCompany() {
        return company;
    }

    public Role getRole() {
        return role;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public Status getStatus() {
        return status;
    }

    public ContactName getContactName() {
        return contactName;
    }

    public ContactDetail getContactDetail() {
        return contactDetail;
    }

    public Notes getNotes() {
        return notes;
    }

    /**
     * Returns true if both opportunities have the same company and role.
     * This defines a weaker notion of equality between two opportunities.
     *
     * @param otherOpportunity The other opportunity to be compared with.
     * @return true if both opportunities have the same company and role, false otherwise.
     */
    public boolean isSameOpportunity(Opportunity otherOpportunity) {
        if (otherOpportunity == this) {
            return true;
        }

        return otherOpportunity != null
            && otherOpportunity.getCompany().equals(getCompany())
            && otherOpportunity.getRole().equals(getRole());
    }

    /**
     * Returns true if both opportunities have the same identity and data fields.
     * This defines a stronger notion of equality between two opportunities.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Opportunity)) {
            return false;
        }

        Opportunity otherOpportunity = (Opportunity) other;
        // Use Objects.equals to handle null optional fields
        return company.equals(otherOpportunity.company)
            && role.equals(otherOpportunity.role)
            && Objects.equals(deadline, otherOpportunity.deadline)
            && Objects.equals(status, otherOpportunity.status)
            && Objects.equals(contactName, otherOpportunity.contactName)
            && Objects.equals(contactDetail, otherOpportunity.contactDetail)
            && Objects.equals(notes, otherOpportunity.notes);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(company, role, deadline, status, contactName, contactDetail, notes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("company", company)
            .add("role", role)
            .add("deadline", deadline)
            .add("status", status)
            .add("contactName", contactName)
            .add("contactDetail", contactDetail)
            .add("notes", notes)
            .toString();
    }

}
