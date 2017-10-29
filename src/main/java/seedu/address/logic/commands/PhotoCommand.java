package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhotoPath;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Edits the photo path of a person to the address book.
 */
public class PhotoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "photo";
    public static final String COMMAND_ALIAS = "ph";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": If adds photo path to the person identified by the index number used in the last person listing,"
            + " add the photo path to the person.\n"
            + "If the photo path field is empty, the old photo path is removed for the person.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PHOTO + "[PHOTO PATH] \n"
            + "Example: (add photo path) " + COMMAND_WORD + " 1 " + PREFIX_PHOTO + "C:\\Users\\User\\Desktop\\photo.jpg\n"
            + "Example: (delete photo path) " + COMMAND_WORD
            + " 2 "
            + PREFIX_PHOTO + "\n";

    public static final String MESSAGE_ADD_PHOTO_SUCCESS = "Added photo path to Person: %1$s";
    public static final String MESSAGE_DELETE_PHOTO_SUCCESS = "Removed photo path from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;
    private final PhotoPath photoPath;

    /**
     * @param targetIndex of the person in the list to edit the photo path
     * @param photo path of the person
     */
    public PhotoCommand(Index targetIndex, PhotoPath photoPath) {
        requireNonNull(targetIndex);
        requireNonNull(photoPath);

        this.targetIndex = targetIndex;
        this.photoPath = photoPath;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToPhoto = lastShownList.get(targetIndex.getZeroBased());

        Person photoedPerson = createPhotoedPerson(personToPhoto, photoPath);

        try {
            model.updatePerson(personToPhoto, photoedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMsg(photoedPerson));
    }

    /**
     * Generate the Successful Message accordingly.
     * @param personToPhoto
     * @return successful message for adding photo path if the photo path string is not empty.
     */
    private String generateSuccessMsg(ReadOnlyPerson personToPhoto) {
        if (photoPath.toString().isEmpty()) {
            return String.format(MESSAGE_DELETE_PHOTO_SUCCESS, personToPhoto);
        } else {
            return String.format(MESSAGE_ADD_PHOTO_SUCCESS, personToPhoto);
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToPhoto}
     * photographed with {@code photoPersonDescriptor}.
     */
    public static Person createPhotoedPerson(ReadOnlyPerson personToPhoto,
                                              PhotoPath photoPath) {
        assert personToPhoto != null;

        Person photoPerson = new Person(personToPhoto.getName(), personToPhoto.getGender(),
                personToPhoto.getMatricNo(), personToPhoto.getPhone(),
                personToPhoto.getEmail(), personToPhoto.getAddress(), personToPhoto.getTimetable(),
                personToPhoto.getRemark(), photoPath, personToPhoto.getTags(), personToPhoto.getBirthday());

        return photoPerson;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhotoCommand)) {
            return false;
        }

        // state check
        PhotoCommand ph = (PhotoCommand) other;
        return targetIndex.equals(ph.targetIndex)
                && photoPath.equals(ph.photoPath);
    }
}

