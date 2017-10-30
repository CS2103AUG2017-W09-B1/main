package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.MatricNo;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PhotoPath;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[]{
                new Person(new Name("Alex Yeoh"), new Gender("Male"), new MatricNo("A0162531N"),
                        new Phone("87438807"), new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"), new Timetable("http://modsn.us/0ECRP"),
                        new Remark(""), new PhotoPath(""), getTagSet("friends"), new Birthday("23051997")),
                new Person(new Name("Bernice Yu"), new Gender("Male"), new MatricNo("A0162541N"),
                        new Phone("99272758"), new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        new Timetable("http://modsn.us/0ECRP"),
                        new Remark("Likes to drink tea"), new PhotoPath(""),
                        getTagSet("colleagues", "friends"), new Birthday("16021991")),
                new Person(new Name("Charlotte Oliveiro"), new Gender("Female"), new MatricNo("A0163331N"),
                        new Phone("93210283"), new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        new Timetable("http://modsn.us/0ECRP"),
                        new Remark(""), new PhotoPath(""), getTagSet("neighbours"), new Birthday("18081993")),
                new Person(new Name("David Li"), new Gender("Male"), new MatricNo("A0165231N"),
                        new Phone("91031282"), new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        new Timetable("http://modsn.us/0ECRP"),
                        new Remark("Likes to drink coffee"), new PhotoPath(""), getTagSet("family"),
                        new Birthday("23091992")),
                new Person(new Name("Irfan Ibrahim"), new Gender("Male"), new MatricNo("A0175531N"),
                        new Phone("92492021"), new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"), new Timetable("http://modsn.us/0ECRP"),
                        new Remark(""), new PhotoPath(""), getTagSet("classmates"), new Birthday("07121998")),
                new Person(new Name("Roy Balakrishnan"), new Gender("Male"), new MatricNo("A0155531K"),
                        new Phone("92624417"), new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"), new Timetable("http://modsn.us/0ECRP"),
                        new Remark("CAP 5.0"), new PhotoPath(""),
                        getTagSet("colleagues"), new Birthday("05111998"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }
        return tags;
    }
}
