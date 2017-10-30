package seedu.address.testutil;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting string.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static String getFilePathInSandboxFolder(String fileName) {
        try {
            FileUtil.createDirs(new File(SANDBOX_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER + fileName;
    }

    /**
     * Returns the middle index of the person in the {@code model}'s person list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getPersonList().size() / 2);
    }

    /**
     * Returns the last index of the person in the {@code model}'s person list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getPersonList().size());
    }

    /**
     * Returns the person in the {@code model}'s person list at {@code index}.
     */
    public static ReadOnlyPerson getPerson(Model model, Index index) {
        return model.getAddressBook().getPersonList().get(index.getZeroBased());
    }

    /**
     * Remove the temporary test files and parents if they exist till the root of disk C.
     * @param path
     * @throws IOException
     */
    public static void removeFileAndItsParentsTillRoot(Path path) throws IOException {
        if (path.toString().matches("[a-zA-Z]+[:]+[\\\\]") || path == null) {
            return;
        } else if (Files.isRegularFile(path)) {
            Files.deleteIfExists(path);

        }

        if (Files.isDirectory(path)) {
            try {
                Files.delete(path);
            } catch (DirectoryNotEmptyException e) {
                return;
            }
        }
        //use recursion here
        removeFileAndItsParentsTillRoot(path.getParent());
    }

    /**
     * Creates a temporary folder and a photo file for parseCommand_photo() test, i.e.,
     * "C:\\family\\photo.jpg"
     * @return the path string of the temporary file
     */
    public static String createTempFile() {
        final String photoPath = "C:\\family\\photo.jpg";
        File testFile = new File(photoPath);

        //create new test file
        testFile.getParentFile().mkdirs();
        try {
            testFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return photoPath;
    }
}
