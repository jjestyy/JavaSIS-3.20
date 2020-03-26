package pro.sisit.model;

import java.util.Objects;

public class Book implements CSVStorable{

    private String name;
    private String author;
    private String genre;
    private String isbn;

    public Book() {
    }

    public Book(String name, String author, String genre, String isbn) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return getName().equals(book.getName()) &&
            getAuthor().equals(book.getAuthor()) &&
            getGenre().equals(book.getGenre()) &&
            getIsbn().equals(book.getIsbn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAuthor(), getGenre(), getIsbn());
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }

    public void parseStringFromCSV(String string, String delimiter) {
        try {
            String[] strArr = string.split(";", 4);
            this.name = strArr[0];
            this.author = strArr[1];
            this.genre = strArr[2];
            this.isbn = strArr[3];
        } catch (Exception e) {
            System.out.println("Bad string to parse as Book : " + string);
            throw new RuntimeException("Bad string to parse - " + string);
        }
    }

    public String makeStringForCSV(String delimiter) {
        return String.join(delimiter,name, author, genre, isbn);
    }
}
