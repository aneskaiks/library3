package exception;

public class LibraryException extends RuntimeException {

    private int code;

    public LibraryException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}