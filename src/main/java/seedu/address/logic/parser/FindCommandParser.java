package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ARCHIVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.opportunity.OpportunityContainsSubstringPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String MESSAGE_ARCHIVE_MISSING_VALUE =
            "When using a/, keywords must follow it directly. Use 'find a/KEYWORD' to search archived by name, "
            + "or 'find a/ c/COMPANY' to search archived by company.";

    public static final String MESSAGE_AMBIGUOUS_ARCHIVE_KEYWORDS =
            "Keywords cannot appear both before and after a/.\n"
            + "Use 'find a/KEYWORD' to search archived opportunities.";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(" " + args, PREFIX_ARCHIVE, PREFIX_COMPANY);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ARCHIVE, PREFIX_COMPANY);

        Optional<String> archivedValue = argMultimap.getValue(PREFIX_ARCHIVE);
        boolean searchArchived = archivedValue.isPresent();
        boolean archiveHasValue = archivedValue.isPresent() && !archivedValue.get().trim().isEmpty();

        List<String> preambleKeywords = splitKeywords(argMultimap.getPreamble());

        if (searchArchived && !archiveHasValue && !preambleKeywords.isEmpty()) {
            throw new ParseException(MESSAGE_ARCHIVE_MISSING_VALUE);
        }

        if (archiveHasValue && !preambleKeywords.isEmpty()) {
            throw new ParseException(MESSAGE_AMBIGUOUS_ARCHIVE_KEYWORDS);
        }

        List<String> nameKeywords = archiveHasValue
                ? new ArrayList<>(splitKeywords(archivedValue.get()))
                : new ArrayList<>(preambleKeywords);

        Optional<String> companyValue = argMultimap.getValue(PREFIX_COMPANY);
        List<String> companyKeywords = companyValue.map(this::splitKeywords).orElse(List.of());

        if ((nameKeywords.isEmpty() && companyKeywords.isEmpty())
                || (companyValue.isPresent() && companyKeywords.isEmpty())) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new OpportunityContainsSubstringPredicate(nameKeywords, companyKeywords),
                searchArchived);
    }

    private List<String> splitKeywords(String value) {
        String trimmedValue = value.trim();
        return trimmedValue.isEmpty() ? List.of() : Arrays.asList(trimmedValue.split("\\s+"));
    }
}
