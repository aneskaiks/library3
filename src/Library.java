import entity.Book;
import entity.Reader;
import exception.LibraryException;
import repository.BookRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static exception.ErrorCode.*;

public class Library {

    Map<Long, Book> bookMap = new ConcurrentHashMap<>();
    Map<Long, Reader> readerMap = new ConcurrentHashMap<>();
    Map<Long, List<Book>> authorBookMap = new ConcurrentHashMap<>();
    NavigableMap<LocalDate, List<BorrowRecord>> borrowHistoryMap = new ConcurrentSkipListMap<>();

    private final BookRepository bookRepository = new BookRepository();

    private final ReentrantReadWriteLock globalLock = new ReentrantReadWriteLock();
    private final Lock readLock = globalLock.readLock();
    private final Lock writeLock = globalLock.writeLock();


    void addBook(Book book) {
        writeLock.lock();
        try {
            bookRepository.save(book.getTitle(), book.getPublicationDate());
        } finally {
            writeLock.unlock();
        }
    }
void update (Book book){
        bookRepository.update(book.getTitle(), book.getId());
}
    Book getBookById (long bookId) {
        Book book = bookRepository.getById(bookId);
        if (Objects.isNull(book)){
            throw new LibraryException(BOOK_NOT_FOUND);
        }
        return book;
    }

    void removeBook(Long id) {
       bookRepository.remove(id);
    }

    void addReader(Reader reader) {
        writeLock.lock();
        try {
            readerMap.put(reader.getId(), reader);
        } finally {
            writeLock.unlock();
        }
    }

    Reader findReader(long readerId) {
        readLock.lock();
        try {
            Reader reader = readerMap.get(readerId);

            if (Objects.isNull(reader)) {
                throw new LibraryException(READER_NOT_FOUND);
            }
            return reader;
        }finally {
            readLock.unlock();
        }
    }

    void removeReader(Reader reader) {
    writeLock.lock();
    try {
        if (!reader.getBorrowedBooks().isEmpty()) {
            throw new LibraryException(READER_HAS_BOOKS);
        }
        readerMap.remove(reader.getId());
    } finally {
        writeLock.unlock();
    }
    }

//    void borrowBook(long readerId, long bookId) {
//        writeLock.lock();
//        try {
//            Reader reader = findReader(readerId);
//            Book book = findBook(bookId);
//
//            if (book.isBorrowed()) {
//                throw new LibraryException(BOOK_IS_BORROWED);
//            }
//
//            reader.borrowBook(book);
//            book.borrow(reader);
//
//            addBorrowRecord(readerId, bookId, LocalDate.now());
//
//        } finally {
//            writeLock.unlock();
//        }

//    }
//
//    public List<Reader> getAllReaders() {
//        readLock.lock();
//        try {
//            return readerMap.values()
//                    .stream()
//                    .toList();
//        } finally {
//            readLock.unlock();
//        }
//    }

    public void getAllBooks() {
        bookRepository.getAll();
    }

    public List<Book> getBorrowedBooks() {
        readLock.lock();
        try {
            return bookMap.values().stream()
                    .filter(Book::isBorrowed)
                    .toList();
        } finally {
            readLock.unlock();
        }
    }

    public void addBorrowRecord(long readerId, long bookId, LocalDate borrowedAt) {
        BorrowRecord record = new BorrowRecord(readerId, bookId, borrowedAt);

        borrowHistoryMap
                .computeIfAbsent(borrowedAt, _ -> new ArrayList<>())
                .add(record);
    }

    void markReturn(long readerId, long bookId, LocalDate returnedAt) {

        for (Map.Entry<LocalDate, List<BorrowRecord>> entry : borrowHistoryMap.descendingMap().entrySet()) {

            List<BorrowRecord> daysRecord = entry.getValue();

            for (int i = daysRecord.size() - 1; i >= 0; i--) {

                BorrowRecord record = daysRecord.get(i);

                if (record.getReaderId().equals(readerId)
                        && record.getBookId().equals(bookId)
                        && !record.isReturned()) {

                    record.markReturned(returnedAt);
                    return;
                }
            }
        }

        throw new LibraryException(BOOK_NOT_BORROWED);
    }

//    void returnBook(long readerId, long bookId) {
//        writeLock.lock();
//        try {
//            Reader reader = findReader(readerId);
//            Book book = findBook(bookId);
//
//            if (!book.getBorrowedBy().getId().equals(readerId)) {
//                throw new LibraryException(BOOK_NOT_BELONG_TO_READER);
//            }
//            if (!reader.getBorrowedBooks().contains(book)) {
//                throw new LibraryException(BOOK_NOT_BORROWED);
//            }
//            book.returnBack();
//            reader.returnBook(book);
//
//            markReturn(readerId, bookId, LocalDate.now());
//        } finally {
//            writeLock.unlock();
//        }
//    }

    List<BorrowRecord> getBorrowHistoryByRange(LocalDate startDate, LocalDate endDate) {
        NavigableMap<LocalDate, List<BorrowRecord>> rangeMap =
                borrowHistoryMap.subMap(startDate, true, endDate, true);

        List<BorrowRecord> result = new ArrayList<>();

        for (List<BorrowRecord> records : rangeMap.values()) {
            for (BorrowRecord record : records) {
                result.add(record);
            }
        }

        return result;
    }


}