package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHOTOPATH_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHOTOPATH_BOB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.io.FileNotFoundException;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class PhotoCommandTest {

    @Test
    public void isValidLocalPhotoPath() {
        // blank email
        assertFalse(PhotoCommand.isValidLocalPhotoPath("")); // empty string
        assertFalse(PhotoCommand.isValidLocalPhotoPath(" ")); // spaces only

        // missing parts
        assertFalse(PhotoCommand.isValidLocalPhotoPath("photo.jpg")); // missing disk part
        assertFalse(PhotoCommand.isValidLocalPhotoPath("c:photo.jpg")); // missing backslash
        assertFalse(PhotoCommand.isValidLocalPhotoPath("d:photo.jpg")); // missing backslash

        // invalid parts
        assertFalse(PhotoCommand.isValidLocalPhotoPath("c:\\\\photo.jpg")); // too many backslashes
        assertFalse(PhotoCommand.isValidLocalPhotoPath("c:\\")); // no file name
        assertFalse(PhotoCommand.isValidLocalPhotoPath("c:\\")); // no file name

        // valid photo path
        assertTrue(PhotoCommand.isValidLocalPhotoPath("c:\\desktop\\baby.jpg"));
        assertTrue(PhotoCommand.isValidLocalPhotoPath("d:\\myself.jpg"));  //
        assertTrue(PhotoCommand.isValidLocalPhotoPath("d:\\my_photo.jpg"));  // underscore
    }

    @Test
    public void equals() throws FileNotFoundException, IllegalValueException {
        PhotoCommand addFirstPersonPhotoPath = new PhotoCommand(INDEX_FIRST_PERSON, VALID_PHOTOPATH_AMY);
        PhotoCommand addSecondPersonPhotoPath = new PhotoCommand(INDEX_SECOND_PERSON, VALID_PHOTOPATH_BOB);

        // same object -> returns true
        assertTrue(addFirstPersonPhotoPath.equals(addFirstPersonPhotoPath));

        // same value -> returns true
        PhotoCommand copy = new PhotoCommand(addFirstPersonPhotoPath.getIndex(),
                addFirstPersonPhotoPath.getLocalPhotoPath());
        assertTrue(addFirstPersonPhotoPath.equals(copy));

        // different types -> returns false
        assertFalse(addFirstPersonPhotoPath.equals(1));

        // null -> returns false
        assertFalse(addFirstPersonPhotoPath == null);

        // different objects -> returns false
        assertFalse(addFirstPersonPhotoPath.equals(addSecondPersonPhotoPath));
    }
}
