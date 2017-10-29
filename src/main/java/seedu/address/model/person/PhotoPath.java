package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the path of a person's photo in the address book.
 */
public class PhotoPath {

    public static final String MESSAGE_PHOTOPATH_CONSTRAINTS =
            "Photo Path should be the absolute path in your PC. It should be a string started with the name of " +
                    "your disk, followed by several groups of backslash and string, like c:\\desktop\\happy.jpg";



    public static final String PHOTOPATH_VALIDATION_REGEX = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?";

    public final String value;  //photo path

    public PhotoPath(String photoPath) throws IllegalValueException {
        requireNonNull(photoPath);
        String trimmedPhotoPath = photoPath.trim();

        //copy the photo file in PC to the picture folders.
        if (photoPath.equals("") || isValidPhotoPath(trimmedPhotoPath)) {
            //not specified yet
            this.value = trimmedPhotoPath;
        } else {
            throw new IllegalValueException(MESSAGE_PHOTOPATH_CONSTRAINTS);
        }

    }


    /**
     * Returns if a given string is a valid photo path.
     */
    public static boolean isValidPhotoPath(String test) {
        return test.matches(PHOTOPATH_VALIDATION_REGEX);
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
