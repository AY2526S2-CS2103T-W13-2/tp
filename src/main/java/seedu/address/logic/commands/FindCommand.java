package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.OpportunityContainsSubstringPredicate;

/**
 * Finds and lists all opportunities in address book whose name and company match the provided keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String ARCHIVED_FLAG = "a/";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds opportunities by name and/or company.\n"
            + "Format:\n"
            + "  " + COMMAND_WORD
            + " NAME_KEYWORD [MORE_NAME_KEYWORDS]... [c/COMPANY_KEYWORD [MORE_COMPANY_KEYWORDS]...]\n"
            + "  " + COMMAND_WORD
            + " c/COMPANY_KEYWORD [MORE_COMPANY_KEYWORDS]...\n"
            + "  " + COMMAND_WORD
            + " " + ARCHIVED_FLAG
            + "NAME_KEYWORD [MORE_NAME_KEYWORDS]... [c/COMPANY_KEYWORD [MORE_COMPANY_KEYWORDS]...]\n"
            + "  " + COMMAND_WORD
            + " " + ARCHIVED_FLAG
            + " c/COMPANY_KEYWORD [MORE_COMPANY_KEYWORDS]...\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " alice\n"
            + "  " + COMMAND_WORD + " alice bob c/stripe\n"
            + "  " + COMMAND_WORD + " c/stripe google\n"
            + "  " + COMMAND_WORD + " " + ARCHIVED_FLAG + "jan c/stripe\n"
            + "  " + COMMAND_WORD + " " + ARCHIVED_FLAG + " c/stripe\n"
            + "Note: archived name keywords must follow a/, not precede it "
            + "(e.g. 'find a/jan', not 'find jan a/').";

    private final OpportunityContainsSubstringPredicate predicate;
    private final boolean searchArchived;

    /**
     * Creates a {@code FindCommand} that searches unarchived opportunities.
     */
    public FindCommand(OpportunityContainsSubstringPredicate predicate) {
        this(predicate, false);
    }

    /**
     * Creates a {@code FindCommand} that searches opportunities within the requested archive scope.
     */
    public FindCommand(OpportunityContainsSubstringPredicate predicate, boolean searchArchived) {
        requireNonNull(predicate);
        this.predicate = predicate;
        this.searchArchived = searchArchived;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Predicate<Opportunity> archiveScopePredicate = searchArchived
                ? PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES
                : PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;
        model.setArchiveView(searchArchived);
        model.updateFilteredOpportunityList(archiveScopePredicate.and(predicate));

        int count = model.getFilteredOpportunityList().size();
        return new CommandResult(
                String.format(Messages.MESSAGE_OPPORTUNITIES_LISTED_OVERVIEW,
                        count, Messages.getOpportunityWord(count)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate)
                && searchArchived == otherFindCommand.searchArchived;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .add("searchArchived", searchArchived)
                .toString();
    }
}
