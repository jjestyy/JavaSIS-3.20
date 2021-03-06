package pro.sisit.adapter.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.List;

import pro.sisit.adapter.IOAdapter;
import pro.sisit.model.CSVStorable;

public class CSVAdapter<T extends CSVStorable> implements IOAdapter<T> {

    private Class<T> entityType;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String delimiter = ";";

    public CSVAdapter(Class<T> entityType, BufferedReader reader,
        BufferedWriter writer, String delimiter) {

        this.entityType = entityType;
        this.reader = reader;
        this.writer = writer;
        this.delimiter = delimiter;
    }

    @Override
    public T read(int index) {
        T object = null;
        try (BufferedReader reader = this.reader) {
            object =  entityType.getDeclaredConstructor().newInstance();
            for (int i = 0; i <= index; i++) {
                String line = reader.readLine();
                if(i == index) {
                    object.parseStringFromCSV(line, delimiter);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return object;
    }

    @Override
    public int append(T entity) {
        int index = 0;
        try (BufferedReader reader = this.reader; BufferedWriter writer = this.writer) {
            while (reader.readLine() != null ) index++;
            writer.write(entity.makeStringForCSV(delimiter));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return index;
    }

    public void write(List<T> entities) {
        try (BufferedWriter writer = this.writer) {
            for (T entity: entities) {
                writer.write(entity.makeStringForCSV(delimiter));
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
