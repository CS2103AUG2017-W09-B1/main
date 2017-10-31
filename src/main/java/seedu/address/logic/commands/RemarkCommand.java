package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Edits the remark of a person to the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_ALIAS = "rm";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": If remarks the person identified by the index number used in the last person listing,"
            + " add the remark to the person.\n"
            + "If the remark field is empty, the remark is removed for the person.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "[REMARK] \n"
            + "Example: (add remark) " + COMMAND_WORD + " 1 " + PREFIX_REMARK + "Likes to drink coffee.\n"
            + "Example: (delete remark) " + COMMAND_WORD
            + " 2 "
            + PREFIX_REMARK + "\n";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added Remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed Remark from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;
    private final Remark remark;

    /**
     * @param targetIndex of the person in the list to edit the remark
     * @param remark of the person
     */
    public RemarkCommand(Index targetIndex, Remark remark) {
        requireNonNull(targetIndex);
        requireNonNull(remark);

        this.targetIndex = targetIndex;
        this.remark = remark;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToRemark = lastShownList.get(targetIndex.getZeroBased());

        Person remarkedPerson = createRemarkedPerson(personToRemark, remark);

        try {
            model.updatePerson(personToRemark, remarkedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMsg(remarkedPerson));
    }

    /**
     * Generate the Successful Message accordingly.
     * @param personToRemark
     * @return successful message for adding remark if the remark string is not empty.
     */
    private String generateSuccessMsg(ReadOnlyPerson personToRemark) {
        if (remark.toString().isEmpty()) {
            return String.format(MESSAGE_DELETE_REMARK_SUCCESS, personToRemark);
        } else {
            return String.format(MESSAGE_ADD_REMARK_SUCCESS, personToRemark);
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToRemark}
     * remarked with {@code remarkPersonDescriptor}.
     */
    public static Person createRemarkedPerson(ReadOnlyPerson personToRemark,
                                                 Remark remark) {
        assert personToRemark != null;

        Person remarkPerson = new Person(personToRemark.getName(), personToRemark.getGender(),
                personToRemark.getMatricNo(), personToRemark.getPhone(),
                personToRemark.getEmail(), personToRemark.getAddress(), personToRemark.getTimetable(),
                remark, personToRemark.getPhotoPath(), personToRemark.getTags(), personToRemark.getBirthday());

        return remarkPerson;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand r = (RemarkCommand) other;
        return targetIndex.equals(r.targetIndex)
                && remark.equals(r.remark);
    }
}
