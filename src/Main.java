import entity.Book;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        System.out.println("\nБИБЛИОТЕЧНАЯ СИСТЕМА");
//        System.out.println("=".repeat(40));

        // 1. Создаем библиотеку
        Library library = new Library();

        Book book = new Book("Narnia", LocalDate.of(1900, 6, 21));
        library.addBook(book);
        Book updatedBook = new Book();
        updatedBook.setId();
        updatedBook.setTitle("Narnia 2");
        library.update(updatedBook);
        library.getAllBooks();
//        library.removeBook(11L);
//        library.getBookById(1);

//
//        // 2. Создаем авторов
//        entity.Author kostenko = new entity.Author("Лина Костенко", 93);
//        entity.Author orwell = new entity.Author("Джордж Оруэлл", 46);
//        entity.Author elrod = new entity.Author("Хэл Элрод", 45);
//
//        // 3. Создаем книги
//        entity.Book book1 = new entity.Book("Записки українського самашедшого", kostenko, 2010);
//        entity.Book book2 = new entity.Book("1984", orwell, 1949);
//        entity.Book book3 = new entity.Book("Скотный двор", orwell, 1945);
//        entity.Book book4 = new entity.Book("Магия утра", elrod, 2016);
//
//        // Добавляем книги
//        library.addBook(book1);
//        library.addBook(book2);
//        library.addBook(book3);
//        library.addBook(book4);
//
////        System.out.println("Добавлено книг: " + library.getAllBooks().size());
//        System.out.println("  - " + book1.getTitle() + " (" + book1.getAuthor().getName() + ")");
//        System.out.println("  - " + book2.getTitle() + " (" + book2.getAuthor().getName() + ")");
//        System.out.println("  - " + book3.getTitle() + " (" + book3.getAuthor().getName() + ")");
//        System.out.println("  - " + book4.getTitle() + " (" + book4.getAuthor().getName() + ")");
//
//        // 4. Создаем читателей
//        entity.Reader reader1 = new entity.Reader("Анна Шевченко", null);
//        entity.Reader reader2 = new entity.Reader("Марія Коваленко", null);
//        entity.Reader reader3 = new entity.Reader("Олег Мельник", null);
//
//        // Добавляем читателей
//        library.addReader(reader1);
//        library.addReader(reader2);
//        library.addReader(reader3);
//
//        System.out.println("\nДобавлено читателей: " + library.getAllReaders().size());
//        System.out.println("  - " + reader1.getName());
//        System.out.println("  - " + reader2.getName());
//        System.out.println("  - " + reader3.getName());
//
//        System.out.println("\n" + "=".repeat(40));
//
//        // ТЕСТ 1: Последовательная выдача
//        System.out.println("\nТЕСТ 1: Обычная выдача книги");
//        System.out.println("-".repeat(30));
//
//        library.borrowBook(reader1.getId(), book2.getId());
//        System.out.println(reader1.getName() + " взял(а) книгу: " + book2.getTitle());
//        System.out.println("Книга выдана? " + book2.isBorrowed());
//        System.out.println("У читателя книг: " + reader1.getBorrowedBooks().size());
//
//        // ТЕСТ 2: Попытка взять уже выданную книгу
//        System.out.println("\nТЕСТ 2: Попытка взять книгу, которая уже выдана");
//        System.out.println("-".repeat(30));
//
//        try {
//            library.borrowBook(reader2.getId(), book2.getId());
//        } catch (Exception e) {
//            System.out.println("Ошибка: " + e.getMessage());
//        }
//
//        // ТЕСТ 3: Возврат книги
//        System.out.println("\nТЕСТ 3: Возврат книги");
//        System.out.println("-".repeat(30));
//
//        library.returnBook(reader1.getId(), book2.getId());
//        System.out.println(reader1.getName() + " вернул(а) книгу: " + book2.getTitle());
//        System.out.println("Книга выдана? " + book2.isBorrowed());
//        System.out.println("У читателя книг: " + reader1.getBorrowedBooks().size());
//
//        // ТЕСТ 4: Многопоточная выдача (5 потоков пытаются взять одну книгу)
//        System.out.println("\nТЕСТ 4: Многопоточная выдача (5 потоков пытаются взять 'Магия утра')");
//        System.out.println("-".repeat(30));
//
//        ExecutorService executor = Executors.newFixedThreadPool(5);
//        AtomicInteger successCount = new AtomicInteger(0);
//        AtomicInteger failCount = new AtomicInteger(0);
//
//        for (int i = 0; i < 5; i++) {
//            final int readerNum = i + 1;
//            executor.submit(() -> {
//                try {
//                    if (readerNum == 1) {
//                        library.borrowBook(reader1.getId(), book4.getId());
//                    } else if (readerNum == 2) {
//                        library.borrowBook(reader2.getId(), book4.getId());
//                    } else if (readerNum == 3) {
//                        library.borrowBook(reader3.getId(), book4.getId());
//                    } else {
//                        // дополнительный читатель
//                        entity.Reader temp = new entity.Reader("Temp" + readerNum, null);
//                        library.addReader(temp);
//                        library.borrowBook(temp.getId(), book4.getId());
//                    }
//                    successCount.incrementAndGet();
//                    System.out.println("  Поток " + Thread.currentThread().getName() + " - УСПЕШНО взял книгу");
//                } catch (Exception e) {
//                    failCount.incrementAndGet();
//                    System.out.println("  Поток " + Thread.currentThread().getName() + " - НЕ УДАЛОСЬ: " + e.getMessage());
//                }
//            });
//        }
//
//        executor.shutdown();
//        executor.awaitTermination(5, TimeUnit.SECONDS);
//
//        System.out.println("\nРезультат многопоточной выдачи:");
//        System.out.println("  Успешно: " + successCount.get() + " потоков");
//        System.out.println("  Ошибок: " + failCount.get() + " потоков");
//        System.out.println("  Книгу взял: " + book4.getBorrowedBy().getName());
//
//        // ТЕСТ 5: Статистика по книгам
//        System.out.println("\nТЕСТ 5: Статистика популярности книг");
//        System.out.println("-".repeat(30));
//
//        System.out.println(book2.getTitle() + " брали " + book2.getBorrowedCount() + " раз(а)");
//        System.out.println(book4.getTitle() + " брали " + book4.getBorrowedCount() + " раз(а)");
//
//        // ТЕСТ 6: Топ читателей
//        System.out.println("\nТЕСТ 6: Топ читателей по количеству книг");
//        System.out.println("-".repeat(30));
//
//        LibraryUtils utils = new LibraryUtils();
//        List<entity.Reader> topReaders = utils.getTopThreeReaders(library);
//        for (int i = 0; i < topReaders.size(); i++) {
//            entity.Reader r = topReaders.get(i);
//            System.out.println("  " + (i+1) + ". " + r.getName() + " - " + r.getTotalBooks() + " книг(и)");
//        }
//
//        System.out.println("\n" + "=".repeat(40));
//        System.out.println("ВСЕ ТЕСТЫ ЗАВЕРШЕНЫ");
//    }
    }
}