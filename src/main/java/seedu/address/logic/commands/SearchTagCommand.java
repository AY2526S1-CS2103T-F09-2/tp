package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Finds and lists all persons in the address book whose tags contain any of the specified keywords.
 * The search is case-insensitive and matches substrings within tag names.
 */
public class SearchTagCommand extends Command {

    /** The command word used to invoke this command. */
    public static final String COMMAND_WORD = "searchtag";

    /** Usage message describing how to use this command. */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friend colleague";

    /** Predicate used to filter persons by tags. */
    private final TagContainsKeywordsPredicate predicate;

    /**
     * Creates a SearchTagCommand with the given predicate.
     * @param predicate The predicate used to filter persons by tag keywords.
     */
    public SearchTagCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Executes the SearchTagCommand.
     * Filters the person list in the given {@code model} using the predicate and returns
     * a CommandResult summarizing the number of persons found.
     *
     * @param model The model containing the full list of persons.
     * @return A CommandResult containing the number of persons matching the search.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    /**
     * Compares this SearchTagCommand with another object for equality.
     * Two SearchTagCommand objects are equal if they have the same predicate.
     *
     * @param other The object to compare with.
     * @return true if {@code other} is the same object or has the same predicate, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SearchTagCommand)) {
            return false;
        }

        SearchTagCommand otherTagCommand = (SearchTagCommand) other;
        return predicate.equals(otherTagCommand.predicate);
    }

    /**
     * Returns a hash code value for this command.
     * The hash code is based on the predicate.
     *
     * @return the hash code of this command.
     */
    @Override
    public int hashCode() {
        return predicate.hashCode();
    }

    /**
     * Returns a string representation of this command, including its predicate.
     *
     * @return a string containing the class name and the predicate.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
