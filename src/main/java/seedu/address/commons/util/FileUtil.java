package seedu.address.commons.util;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.*;
import java.nio.file.Files;

import oracle.jrockit.jfr.StringConstantPool;

/**
 * Writes and reads files
 */
public class FileUtil {

    private static final String CHARSET = "UTF-8";

    /**
     * Get the extension of the file path by split the path string by regex "."
     * @param filePath
     * @return extension string
     */
    public static String getFileExtension(String filePath) {
        return "." + filePath.split("\\.")[1];
    }

    /**
     * Copy all the content from the file in original path to the one in destination path.
     * @param oriPath
     * @param destPath
     * @return true if the file is successfully copied to the specified place.
     */
    public static boolean copyFile(String oriPath, String destPath) throws IOException {

        //create a buffer to store content
        byte[] buffer = new byte[1024];

        //bufferedInputStream
        FileInputStream fis = new FileInputStream(oriPath);
        BufferedInputStream bis = new BufferedInputStream(fis);

        //bufferedOutputStream
        FileOutputStream fos = new FileOutputStream(destPath);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        int numBytes = bis.read(buffer);
        while (numBytes > 0) {
            bos.write(buffer, 0, numBytes);
            numBytes = bis.read(buffer);
        }

        //close input,output stream
        bis.close();
        bos.close();

        return true;
    }

    public static boolean isFileExists(File file) {
        return file.exists() && file.isFile();
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(File file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories
     *
     * @return true if file is created, false if file already exists
     */
    public static boolean createFile(File file) throws IOException {
        if (file.exists()) {
            return false;
        }

        createParentDirsOfFile(file);

        return file.createNewFile();
    }

    /**
     * Creates the given directory along with its parent directories
     *
     * @param dir the directory to be created; assumed not null
     * @throws IOException if the directory or a parent directory cannot be created
     */
    public static void createDirs(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to make directories of " + dir.getName());
        }
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(File file) throws IOException {
        File parentDir = file.getParentFile();

        if (parentDir != null) {
            createDirs(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()), CHARSET);
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(File file, String content) throws IOException {
        Files.write(file.toPath(), content.getBytes(CHARSET));
    }

    /**
     * Converts a string to a platform-specific file path
     * @param pathWithForwardSlash A String representing a file path but using '/' as the separator
     * @return {@code pathWithForwardSlash} but '/' replaced with {@code File.separator}
     */
    public static String getPath(String pathWithForwardSlash) {
        checkArgument(pathWithForwardSlash.contains("/"));
        return pathWithForwardSlash.replace("/", File.separator);
    }

}
