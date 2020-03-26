package pro.sisit.model;

import java.util.Objects;

public class Publisher implements CSVStorable{
    private String name;
    private int year;

    public Publisher() {
    }

    public Publisher(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return Objects.equals(name, publisher.name) &&
                Objects.equals(year, publisher.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year);
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "name='" + name + '\'' +
                ", year=" + year +
                '}';
    }

    public void parseStringFromCSV(String string, String delimiter) {
        try {
            String[] strArr = string.split(";", 2);
            this.name = strArr[0];
            this.year = Integer.parseInt(strArr[1]);
        } catch (Exception e) {
            System.out.println("Bad string to parse as Publisher: " + string);
            throw new RuntimeException("Bad string to parse - " + string);
        }
    }

    public String makeStringForCSV(String delimiter) {
        return String.join(delimiter,name, String.valueOf(year)) ;
    }
}
