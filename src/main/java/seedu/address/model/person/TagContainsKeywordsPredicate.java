package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests whether a {@link Person}'s tags contain any of the specified keywords.
 *
 * <p>The search is case-insensitive and matches substrings within tag names.
 * This predicate can be used to filter lists of persons based on their tags.</p>
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {

    /** The keywords to search for in person tags. */
    private final List<String> keywords;

    /**
     * Constructs a {@code TagContainsKeywordsPredicate} with the specified keywords.
     *
     * @param keywords The list of keywords to match against person tags.
     */
    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Tests whether the given {@code person} has at least one tag containing
     * any of the keywords (case insensitive).
     *
     * @param person The person to test.
     * @return true if the person has at least one matching tag, false otherwise.
     */
    @Override
    public boolean test(Person person) {
        Set<Tag> tags = person.getTags();
        return tags.stream()
                .map(tag -> tag.tagName.toLowerCase())
                .anyMatch(tagName -> keywords.stream()
                        .anyMatch(keyword -> tagName.contains(keyword.toLowerCase())));
    }
    /**
     * Compares this predicate with another object for equality.
     * Two objects are equal if they have the same keywords.
     *
     * @param other The object to compare with.
     * @return true if {@code other} is the same object or has the same keywords, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagContainsKeywordsPredicate)) {
            return false;
        }

        TagContainsKeywordsPredicate otherTagContainsKeywordsPredicate = (TagContainsKeywordsPredicate) other;
        return keywords.equals(otherTagContainsKeywordsPredicate.keywords);
    }

    /**
     * Returns a hash code value for this predicate.
     * The hash code is based on the keywords list.
     *
     * @return the hash code of this predicate.
     */
    @Override
    public int hashCode() {
        return keywords.hashCode();
    }

    /**
     * Returns a string representation of this predicate, including its keywords.
     *
     * @return a string containing the class name and the keywords list.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
