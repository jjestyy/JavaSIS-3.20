package pro.sisit.adapter;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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
        Book book0 = new Book(
                "Убик",
                "Филип Дик",
                "Научная фантастика",
                "978-5-699-97309-5");
        Book book1 = new Book(
                "Будущее",
                "Глуховский",
                "Научная фантастика",
                "978-5-17-118366-0");
        Book book = new Book(
                "Сегодня",
                "Иванов Иван",
                "Физика",
                "888-5-906902-91-7");
        getBookCSVAdapter().write(Arrays.asList(book0, book1, book));


        // * По желанию можете придумать и свои сущности
    }

    @After
    public void deleteFile() {
        getBookPath().toFile().delete();
    }

    @Test
    public void testRead() throws IOException {
        Book expectedBook0 = new Book(
                "Убик",
                "Филип Дик",
                "Научная фантастика",
                "978-5-699-97309-5");
        Book actualBook0 = getBookCSVAdapter().read(0);
        assertEquals(expectedBook0, actualBook0);
        Book book1 = getBookCSVAdapter().read(1);
        assertEquals("Глуховский", book1.getAuthor());
        assertEquals("Будущее", book1.getName());
        assertEquals("978-5-17-118366-0", book1.getIsbn());
        assertEquals("Научная фантастика", book1.getGenre());


        // TODO: написать тесты для проверки сущности автора
    }

    @Test
    public void testAppend() throws IOException {

        Book newBook = new Book(
            "Чертоги разума. Убей в себе идиота!",
            "Андрей Курпатов",
            "Психология",
            "978-5-906902-91-7");

        int bookIndex = getBookCSVAdapter().append(newBook);
        Book bookAtIndex = getBookCSVAdapter().read(bookIndex);
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
