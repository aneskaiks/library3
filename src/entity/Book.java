package entity;

import exception.ErrorCode;
import exception.LibraryException;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

import static exception.ErrorCode.BOOK_NOT_BORROWED;

public class Book {

    private Long id;
    private String title;
    private volatile Reader borrowedBy;
    private volatile boolean isBorrowed;
    private AtomicInteger borrowedCount;
    private LocalDate publicationDate;

    public Book(){
        
    }
    public Book(String title, LocalDate publicationDate) {
        this.title = title;
        this.publicationDate = publicationDate;
    }

    public Book(Long id, String title, LocalDate publicationDate) {
        this.id = id;
        this(title, publicationDate);
    }

    void borrow(Reader reader) {
        synchronized (this) {
            if (isBorrowed) {
                throw new LibraryException(ErrorCode.BOOK_IS_BORROWED);
            }
            isBorrowed = true;
            borrowedBy = reader;
            borrowedCount.incrementAndGet();
        }
    }


    void returnBack() {
        synchronized (this) {
            if (!isBorrowed) {
                throw new LibraryException(BOOK_NOT_BORROWED);
            }
            isBorrowed = false;
            borrowedBy = null;
        }
    }

    void getInfo() {
        System.out.println("entity.Book name:" + title);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

//    public Author getAuthor() {
//        return author;
//    }

    public Reader getBorrowedBy() {
        return borrowedBy;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public int getBorrowedCount() {
        return borrowedCount.get();
    }

    public LocalDate getYear() {
        return publicationDate;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setId() {
    }

    public void setTitle(String s) {

    }
}