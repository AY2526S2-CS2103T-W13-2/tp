package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OPPORTUNITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OPPORTUNITY;
import static seedu.address.testutil.TypicalOpportunities.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.opportunity.Opportunity;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ArchiveCommand}.
 */
public class ArchiveCommandTest {

    @Test
    public void execute_validIndexFullStorageUnarchivedList_success() {
        Model model = createModelWithArchivedOpportunities(INDEX_FIRST_OPPORTUNITY);

        Opportunity opportunityToArchive = getUnarchivedOpportunities(model)
                .get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        Opportunity archivedOpportunity = createArchivedOpportunity(opportunityToArchive);

        ArchiveCommand archiveCommand = new ArchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY));

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_OPPORTUNITY_SUCCESS,
                "\n" + Messages.format(archivedOpportunity));

        Model expectedModel = createModelWithArchivedOpportunities(INDEX_FIRST_OPPORTUNITY);
        expectedModel.setOpportunity(opportunityToArchive, archivedOpportunity);
        expectedModel.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFullStorageUnarchivedList_throwsCommandException() {
        Model model = createModelWithArchivedOpportunities(INDEX_FIRST_OPPORTUNITY);
        // ensures that outOfBoundIndex is still in bounds of unarchived opportunity list
        Index outOfBoundIndex = Index.fromOneBased(getUnarchivedOpportunities(model).size() + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(List.of(outOfBoundIndex));

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_multipleValidIndicesFullStorageUnarchivedList_success() {
        Model model = createModelWithArchivedOpportunities();

        Opportunity firstOpportunityToArchive = getUnarchivedOpportunities(model)
                .get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        Opportunity secondOpportunityToArchive = getUnarchivedOpportunities(model)
                .get(INDEX_SECOND_OPPORTUNITY.getZeroBased());

        Opportunity archivedFirstOpportunity = createArchivedOpportunity(firstOpportunityToArchive);
        Opportunity archivedSecondOpportunity = createArchivedOpportunity(secondOpportunityToArchive);

        // Pass indices in ascending order to test the descending sort in execute()
        ArchiveCommand archiveCommand =
                new ArchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY, INDEX_SECOND_OPPORTUNITY));

        // The expected message will have them in descending order because of the sorting implementation
        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_OPPORTUNITY_SUCCESS,
                "\n" + Messages.format(archivedSecondOpportunity)
                        + "\n" + Messages.format(archivedFirstOpportunity));

        Model expectedModel = createModelWithArchivedOpportunities();

        // Archive in descending order to match expectedModel state execution safely
        expectedModel.setOpportunity(secondOpportunityToArchive, archivedSecondOpportunity);
        expectedModel.setOpportunity(firstOpportunityToArchive, archivedFirstOpportunity);
        expectedModel.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validAndInvalidIndicesFullStorageUnarchivedList_throwsCommandException() {
        Model model = createModelWithArchivedOpportunities(INDEX_FIRST_OPPORTUNITY);
        // Should throw exception as long as one index is invalid
        Index outOfBoundIndex = Index.fromOneBased(getUnarchivedOpportunities(model).size() + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY, outOfBoundIndex));

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noUnarchivedOpportunities_throwsCommandException() {
        Model model = createModelWithAllOpportunitiesArchived();

        ArchiveCommand archiveCommand = new ArchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY));

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY));
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(List.of(INDEX_SECOND_OPPORTUNITY));

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        ArchiveCommand archiveFirstCommandCopy = new ArchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY));
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different opportunity -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ArchiveCommand archiveCommand = new ArchiveCommand(List.of(targetIndex));
        String expected = ArchiveCommand.class.getCanonicalName() + "{targetIndices=" + List.of(targetIndex) + "}";
        assertEquals(expected, archiveCommand.toString());
    }

    /**
     * Creates a model where the given one-based indices are archived.
     */
    private Model createModelWithArchivedOpportunities(Index... indicesToArchive) {
        Model model = new ModelManager(new AddressBook(getTypicalAddressBook()), new UserPrefs());

        for (Index index : indicesToArchive) {
            Opportunity originalOpportunity = model.getAddressBook().getOpportunityList().get(index.getZeroBased());
            Opportunity archivedOpportunity = createArchivedOpportunity(originalOpportunity);
            model.setOpportunity(originalOpportunity, archivedOpportunity);
        }

        model.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);
        return model;
    }

    /**
     * Creates a model where all opportunities are archived.
     *
     * @return a model where all opportunities are archived
     */
    private Model createModelWithAllOpportunitiesArchived() {
        Model model = new ModelManager(new AddressBook(getTypicalAddressBook()), new UserPrefs());

        // By making a copy of the opportunity list, we avoid concurrent modification issues
        List<Opportunity> opportunities = List.copyOf(model.getAddressBook().getOpportunityList());
        for (Opportunity opportunity : opportunities) {
            Opportunity archivedOpportunity = createArchivedOpportunity(opportunity);
            model.setOpportunity(opportunity, archivedOpportunity);
        }

        model.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);
        return model;
    }

    /**
     * Returns a list of unarchived opportunities from full storage order.
     *
     * @param model the model to retrieve unarchived opportunities from
     * @return a list of unarchived opportunities from full storage order
     */
    private List<Opportunity> getUnarchivedOpportunities(Model model) {
        return model.getAddressBook().getOpportunityList().stream()
                .filter(opportunity -> !opportunity.isArchived())
                .toList();
    }

    /**
     * Creates an archived copy of the given opportunity.
     *
     * @param opportunity the opportunity to archive
     * @return an archived copy of the given opportunity
     */
    private Opportunity createArchivedOpportunity(Opportunity opportunity) {
        return new Opportunity(
                opportunity.getName(),
                opportunity.getEmail(),
                opportunity.getContactRole(),
                opportunity.getCompany(),
                opportunity.getRole(),
                opportunity.getStatus(),
                true,
                opportunity.getPhone().orElse(null)
        );
    }
}
