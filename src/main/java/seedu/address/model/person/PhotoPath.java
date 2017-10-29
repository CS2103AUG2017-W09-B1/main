package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents the path of a person's photo in the address book.
 */
public class PhotoPath {

    public static final String MESSAGE_PHOTOPATH_CONSTRAINTS =
            "Photo Path should be the absolute path in your PC";

    public final String value;

    public PhotoPath(String photoPath) {
        requireNonNull(photoPath);
        this.value = photoPath;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhotoPath // instanceof handles nulls
                && this.value.equals(((PhotoPath) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
