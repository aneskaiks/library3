package entity;

import exception.ErrorCode;
import exception.LibraryException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Reader {

    private Long id;
    private String name;
    private List<Book> borrowedBooks;
    private AtomicInteger totalBooks;

    public Reader(String name, List<Book> borrowedBooks) {
        this.name = name;
        this.borrowedBooks = new CopyOnWriteArrayList<>();
        this.totalBooks = new AtomicInteger(0);
    }
    private final Semaphore bookSemaphore = new Semaphore(3);

    void borrowBook(Book book) {
        if (!bookSemaphore.tryAcquire()) {
            throw new LibraryException(ErrorCode.BOOK_LIMIT);
        }

        borrowedBooks.add(book);
        totalBooks.incrementAndGet();
    }

    void returnBook(Book book) {

        borrowedBooks.remove(book);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public int getTotalBooks() {
        return totalBooks.get();
    }
}