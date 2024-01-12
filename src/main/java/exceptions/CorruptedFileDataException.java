package exceptions;

// Thrown when file data can not be read or when program is unsure whether data in file is correct
public class CorruptedFileDataException extends Exception {
    public CorruptedFileDataException() {
        super("Unable to read file, Data corrupted!");
    }
}
