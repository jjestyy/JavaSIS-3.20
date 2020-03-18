package pro.sisit.adapter.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.List;

import pro.sisit.adapter.IOAdapter;
import pro.sisit.model.CSVStorable;

// 1. TODO: написать реализацию адаптера

public class CSVAdapter<T extends CSVStorable> implements IOAdapter<T> {

    private Class<T> entityType;
    private BufferedReader reader;
    private BufferedWriter writer;

    public CSVAdapter(Class<T> entityType, BufferedReader reader,
        BufferedWriter writer) {

        this.entityType = entityType;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public T read(int index) {
        T object = null;
        try (BufferedReader reader = this.reader) {
            object =  entityType.getDeclaredConstructor().newInstance();
            for (int i = 0; i <= index; i++) {
                String line = reader.readLine();
                if(i == index) {
                    object.parseStringFromCSV(line);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return object;
    }

    @Override
    public int append(T entity) {
        int index = 0;
        try (BufferedReader reader = this.reader; BufferedWriter writer = this.writer) {
            while (reader.readLine() != null ) index++;
            writer.write(entity.makeStringForCSV());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return index;
    }

    public void write(List<T> entityes) {
        try (BufferedWriter writer = this.writer) {
            for (T entity: entityes) {
                writer.write(entity.makeStringForCSV());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
