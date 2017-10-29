package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC_NO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMETABLE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.*;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER, PREFIX_MATRIC_NO,
                        PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_BIRTHDAY, PREFIX_TIMETABLE);
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GENDER, PREFIX_MATRIC_NO, PREFIX_ADDRESS, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_TIMETABLE, PREFIX_BIRTHDAY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER)).get();
            MatricNo matricNo = ParserUtil.parseMatricNo(argMultimap.getValue(PREFIX_MATRIC_NO)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            Timetable timetable = ParserUtil.parseTimetable(argMultimap.getValue(PREFIX_TIMETABLE)).get();
            Remark remark = new Remark("");
            PhotoPath photoPath = new PhotoPath("");
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            Birthday birthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).get();

            ReadOnlyPerson person = new Person(name, gender, matricNo, phone, email, address, timetable,
                    remark, photoPath, tagList, birthday);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
