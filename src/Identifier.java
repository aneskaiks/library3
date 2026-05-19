import java.util.concurrent.atomic.AtomicLong;

public class Identifier {

    private static final AtomicLong bookId = new AtomicLong(1L);
    private static final AtomicLong readerId = new AtomicLong(1L);;
    private static final AtomicLong authorId = new AtomicLong(1L);;

    private Identifier() {

    }

    public static Long generateBookId() {
        return bookId.getAndIncrement();
    }

    public static Long generateReaderId() {
        return readerId.getAndIncrement();
    }

    public static Long generateAuthorId() {
        return authorId.getAndIncrement();
    }

}