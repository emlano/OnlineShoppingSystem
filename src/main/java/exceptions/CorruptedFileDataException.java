package exceptions;

public class CorruptedFileDataException extends Exception {
    public CorruptedFileDataException() {
        super("Unable to read file, Data corrupted!");
    }
}
