package seedu.address.model;

import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Address;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Show map of the given person **/
    void showMapOf(ReadOnlyPerson person);

    /** Show direction to the given person from a given address **/
    void showDirectionsTo(ReadOnlyPerson target, Address address);

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Deletes the given list of persons. */
    void deletePersons(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException;


    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /**
     * Deletes the specified tag from everyone in the address book
     *
     * @throws DuplicatePersonException if deleting a tag from the person causes the person to be equivalent to
     *      another existing person in the list
     * @throws PersonNotFoundException if unable able to find a person in the list when iterating through
     *      all the persons
     * @throws TagNotFoundException if {@code tag} could not be found in the list.
     */
    void deleteTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException, TagNotFoundException;

    /**
     * Deletes the specified tag from everyone in the address book
     *
     * @throws DuplicatePersonException if updating a tag for the person causes the person to be equivalent to
     *      another existing person in the list
     * @throws PersonNotFoundException if unable to find a person in the list when iterating through
     *      all the persons
     * @throws TagNotFoundException if {@code tag} could not be found in the list.
     */
    void editTag(Tag oldTag, Tag newTag) throws DuplicatePersonException, PersonNotFoundException,
            TagNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /** Checks if the master list of tags in the address book has every tag being used */
    void checkMasterTagListHasAllTagsUsed ();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

}
