package pro.sisit.adapter;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pro.sisit.adapter.impl.CSVAdapter;
import pro.sisit.model.Book;

// TODO: 2. Описать тестовые кейсы

public class CSVAdapterTest {

    @Before
    public void createFile() throws IOException {
        // TODO: создать и заполнить csv-файл для сущности Author
        // TODO: создать и заполнить csv-файл для сущности Book

        getBookPath().toFile().createNewFile();
        CSVAdapter<Book> bookCsvAdapter = getBookCSVAdapter();

        Book book0 = new Book(
                "Убик",
                "Филип Дик",
                "Научная фантастика",
                "978-5-699-97309-5");
        bookCsvAdapter.append(book0);
        Book book1 = new Book(
                "Будущее",
                "Глуховский",
                "Научная фантастика",
                "978-5-17-118366-0");
        bookCsvAdapter.append(book1);
        Book book = new Book(
                "Сегодня",
                "Иванов Иван",
                "Физика",
                "888-5-906902-91-7");
        bookCsvAdapter.append(book);

        // * По желанию можете придумать и свои сущности
    }

    @After
    public void deleteFile() throws IOException{
        getBookPath().toFile().delete();
    }

    @Test
    public void testRead() throws IOException {

        CSVAdapter<Book> bookCsvAdapter = getBookCSVAdapter();

        Book book1 = bookCsvAdapter.read(1);
        assertEquals("Глуховский", book1.getAuthor());
        assertEquals("Будущее", book1.getName());
        assertEquals("978-5-17-118366-0", book1.getIsbn());
        assertEquals("Научная фантастика", book1.getGenre());

        Book expectedBook0 = new Book(
            "Убик",
            "Филип Дик",
            "Научная фантастика",
            "978-5-699-97309-5");
        Book actualBook0 = bookCsvAdapter.read(0);
        assertEquals(expectedBook0, actualBook0);

        // TODO: написать тесты для проверки сущности автора
    }

    @Test
    public void testAppend() throws IOException {

        CSVAdapter<Book> bookCsvAdapter = getBookCSVAdapter();

        Book newBook = new Book(
            "Чертоги разума. Убей в себе идиота!",
            "Андрей Курпатов",
            "Психология",
            "978-5-906902-91-7");

        int bookIndex = bookCsvAdapter.append(newBook);
        Book bookAtIndex = bookCsvAdapter.read(bookIndex);
        assertEquals(newBook, bookAtIndex);

        // TODO: написать тесты для проверки сущности автора
    }

    private CSVAdapter<Book> getBookCSVAdapter() throws IOException {
        Path bookFilePath = getBookPath();

        BufferedReader bookReader = new BufferedReader(
                new FileReader(bookFilePath.toFile()));

        BufferedWriter bookWriter = new BufferedWriter(
                new FileWriter(bookFilePath.toFile(), true));

        return (CSVAdapter<Book>) new CSVAdapter(Book.class, bookReader, bookWriter);
    }

    private Path getBookPath() {
        return Paths.get("test-book-file.csv");
    }
}
