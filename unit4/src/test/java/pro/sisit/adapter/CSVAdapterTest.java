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
import pro.sisit.model.Author;
import pro.sisit.model.Book;

public class CSVAdapterTest {

    @Before
    public void createFile() throws IOException {

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
        
        getAuthorPath().toFile().createNewFile();
        Author author0 = new Author("Тургенев", "Московская область");
        Author author1 = new Author("Пушкин", "Москва");
        Author author = new Author("Марк Твен", "Нью Йорк");
        getAuthorCSVAdapter().write(Arrays.asList(author0, author1, author));


        // * По желанию можете придумать и свои сущности
    }
    @After
    public void deleteFile() {
        getBookPath().toFile().delete();
        getAuthorPath().toFile().delete();
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

        Author expectedAuthor0 = new Author("Тургенев", "Московская область");
        Author expectedAuthor1 = new Author("Пушкин", "Москва");
        Author expectedAuthor = new Author("Марк Твен", "Нью Йорк");
        Author actualAuthor0 = getAuthorCSVAdapter().read(0);
        Author actualAuthor1 = getAuthorCSVAdapter().read(1);
        Author actualAuthor  = getAuthorCSVAdapter().read(2);
        assertEquals(expectedAuthor0,actualAuthor0);
        assertEquals(expectedAuthor1,actualAuthor1);
        assertEquals(expectedAuthor ,actualAuthor);

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

        Author newAuthor = new Author("Андрей Курпатов", "Cызрань");
        int authorIndex = getAuthorCSVAdapter().append(newAuthor);
        Author authorAtIndex = getAuthorCSVAdapter().read(authorIndex);
        assertEquals(newAuthor, authorAtIndex);

    }

    private CSVAdapter<Book> getBookCSVAdapter() throws IOException {
        BufferedReader bookReader = new BufferedReader(new FileReader(getBookPath().toFile()));
        BufferedWriter bookWriter = new BufferedWriter(new FileWriter(getBookPath().toFile(), true));
        return (CSVAdapter<Book>) new CSVAdapter(Book.class, bookReader, bookWriter);
    }

    private Path getBookPath() {
        return Paths.get("test-book-file.csv");
    }

    private CSVAdapter<Author> getAuthorCSVAdapter() throws IOException {
        BufferedReader authorReader = new BufferedReader(new FileReader(getAuthorPath().toFile()));
        BufferedWriter authorWriter = new BufferedWriter(new FileWriter(getAuthorPath().toFile(), true));
        return (CSVAdapter<Author>) new CSVAdapter(Author.class, authorReader, authorWriter);
    }

    private Path getAuthorPath() {
        return Paths.get("test-author-file.csv");
    }


}