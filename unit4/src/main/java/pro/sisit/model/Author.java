package pro.sisit.model;

import java.util.Objects;

public class Author implements CSVStorable{

    private String name;
    private String birthPlace;

    public Author() {
    }

    public Author(String name, String birthPlace) {
        this.name = name;
        this.birthPlace = birthPlace;
    }

    public String getName() {
        return name;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        return getName().equals(author.getName()) &&
            getBirthPlace().equals(author.getBirthPlace());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getBirthPlace());
    }

    public void parseStringFromCSV(String string) {
        try {
            String[] strArr = string.split(";", 2);
            this.name = strArr[0];
            this.birthPlace = strArr[1];
        } catch (Exception e) {
            System.out.println("Bad string to parse as Author: " + string);
        }
    }

    public String makeStringForCSV() {
        return String.join(";" ,name, birthPlace) + System.lineSeparator();
    }
}
