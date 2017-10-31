package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class PhotoPathTest {

    @Test
    public void isValidPhotoPath() {

        // spaces
        assertFalse(PhotoPath.isValidPhotoPath(" "));

        // / missing parts： not start with 'docs/images/contactPhotos/'
        assertFalse(PhotoPath.isValidPhotoPath("photo.jpg"));
        assertFalse(PhotoPath.isValidPhotoPath("c:photo.jpg"));
        assertFalse(PhotoPath.isValidPhotoPath("d:photo.jpg"));
        assertFalse(PhotoPath.isValidPhotoPath("c:\\\\photo.jpg"));
        assertFalse(PhotoPath.isValidPhotoPath("c:\\"));
        assertFalse(PhotoPath.isValidPhotoPath("c:\\"));

        // valid photo path
        assertTrue(PhotoPath.isValidPhotoPath("")); // accept empty string by default
        assertTrue(PhotoPath.isValidPhotoPath("docs/images/contactPhotos/1234.jpg"));
        assertTrue(PhotoPath.isValidPhotoPath("docs/images/contactPhotos/12345678.jpg"));  //
        assertTrue(PhotoPath.isValidPhotoPath("docs/images/contactPhotos/1234_5678.jpg"));  // underscore
    }

    @Test
    public void equals() throws IllegalValueException {
        PhotoPath validPhotoPath_1 = new PhotoPath("docs/images/contactPhotos/1234.jpg");
        PhotoPath validPhotoPath_2 = new PhotoPath("docs/images/contactPhotos/5678.jpg");

        // same object -> returns true
        assertTrue(validPhotoPath_1.equals(validPhotoPath_1));

        // same value -> returns true
        PhotoPath copy = new PhotoPath(validPhotoPath_1.value);
        assertTrue(validPhotoPath_1.equals(copy));

        // different types -> returns false
        assertFalse(validPhotoPath_1.equals(1));

        // null -> returns false
        assertFalse(validPhotoPath_1 == null);

        // different objects -> returns false
        assertFalse(validPhotoPath_1.equals(validPhotoPath_2));
    }
}
