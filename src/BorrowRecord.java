import java.time.LocalDate;

public class BorrowRecord {

    private final Long readerId;
    private final Long bookId;
    private final LocalDate borrowedAt;
    private LocalDate returnedAt;

    public BorrowRecord(Long readerId, Long bookId, LocalDate borrowedAt) {
        this.readerId = readerId;
        this.bookId = bookId;
        this.borrowedAt = borrowedAt;
    }

    public void markReturned(LocalDate returnedAt) {
        this.returnedAt = returnedAt;
    }

    public boolean isReturned() {
        return returnedAt != null;
    }

    public Long getReaderId() {
        return readerId;
    }

    public Long getBookId() {
        return bookId;
    }

    public LocalDate getBorrowedAt() {
        return borrowedAt;
    }

    public LocalDate getReturnedAt() {
        return returnedAt;
    }
}
