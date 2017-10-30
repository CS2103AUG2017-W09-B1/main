package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the path of a person's photo in the address book.
 */
public class PhotoPath {

    public static final String MESSAGE_APP_PHOTOPATH_CONSTRAINTS =
            "The app photo path should be a string starting with 'docs/images/contactPhotos/'," +
                    "following by the file name, like'photo.jpg'.";
    public static final String FILE_SAVED_PARENT_PATH = "docs/images/contactPhotos/";

    public final String value;  //photo path

    public PhotoPath(String photoPath) throws IllegalValueException {
        requireNonNull(photoPath);

        if (!isValidPhotoPath(photoPath)) {
            throw new IllegalValueException(MESSAGE_APP_PHOTOPATH_CONSTRAINTS);
        }
        this.value = photoPath;
    }

    /**
     * Returns if a given string is a valid photo path.
     */
    public static boolean isValidPhotoPath(String test) {
        if (test.equals("")) {
            return true;
        }
        String[] parts = test.split("\\.");
        Boolean isFileSpecified = (parts.length == 2);
        return test.startsWith(FILE_SAVED_PARENT_PATH) && isFileSpecified;
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
