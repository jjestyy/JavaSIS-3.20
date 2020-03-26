package pro.sisit.model;

public interface CSVStorable {

    public void parseStringFromCSV(String string, String delimiter);

    public String makeStringForCSV(String delimiter);

}
