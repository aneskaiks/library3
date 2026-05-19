package exception;

public enum ErrorCode {

    BOOK_IS_BORROWED("entity.Book is borrowed", 400),
    BOOK_LIMIT("entity.Reader has already 3 books", 400),
    READER_HAS_BOOKS("entity.Reader has books", 400),
    READER_NOT_FOUND("entity.Reader not found", 404),
    BOOK_NOT_FOUND("entity.Book not found", 404),
    BOOK_NOT_BORROWED("entity.Book is not borrowed", 400),
    BOOK_NOT_BELONG_TO_READER("entity.Book is not belong to reader", 400);

    private String message;
    private int code;

    ErrorCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}