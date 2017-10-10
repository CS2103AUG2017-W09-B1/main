package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";
    public static final String COMMAND_SECONDARY = "remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "  OR the tag specified from all people containing the specific tag\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "        OR  " + PREFIX_TAG + "TAG (case-sensitive)\n "
            + "Example: " + COMMAND_WORD + " 1\n"
            + "         " + COMMAND_WORD + " " + PREFIX_TAG + "friend\n"
            + "         " + COMMAND_WORD + " " + PREFIX_TAG + "friend " + PREFIX_TAG + "enemy";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tags";

    private final Index targetIndex;

    private final Set<Tag> targetTags;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetTags = null;
    }

    public DeleteCommand(Set<Tag> targetTags) {
        this.targetTags = targetTags;
        this.targetIndex = null;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        // this code block is command execution for delete [index]
        if (targetTags == null && targetIndex != null) {
            List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());

            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }

            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
        } else { // this code block is command execution for delete t/[tag...]
            ArrayList<Tag> arrayTags = new ArrayList<Tag>(targetTags);
            List<Tag> listOfExistingTags = model.getAddressBook().getTagList();

            if (!listOfExistingTags.containsAll(arrayTags)) {
                throw new CommandException(Messages.MESSAGE_INVALID_TAG_PROVIDED);
            }

            for (Tag tagToBeDeleted: arrayTags) {
                try {
                    model.deleteTag(tagToBeDeleted);
                } catch (DuplicatePersonException dpe) {
                    assert false : "[Delete Tag] A duplicate person is there";
                } catch (PersonNotFoundException pnfe) {
                    assert false : "[Delete Tag] A person not found";
                }
            }

            return new CommandResult(MESSAGE_DELETE_TAG_SUCCESS);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (targetTags == null) {
            return other == this // short circuit if same object
                    || (other instanceof DeleteCommand // instanceof handles nulls
                    && this.targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
        } else {
            return other == this // short circuit if same object
                    || (other instanceof DeleteCommand // instanceof handles nulls
                    && this.targetTags.equals(((DeleteCommand) other).targetTags)); // state check
        }
    }
}
