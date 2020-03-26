package pro.sisit.adapter.impl;

import pro.sisit.adapter.IOAdapter;
import pro.sisit.model.CSVStorable;

import java.util.List;

public class CSVAdapterWrapper <T extends CSVStorable> implements IOAdapter<T> {
    private T entity;

    public CSVAdapterWrapper(CSVAdapter<T> adapter) {
        this.adapter = adapter;
    }
    private CSVAdapter<T> adapter;

    public T read(int index) {
        logger("read", String.valueOf(index));
        return adapter.read(index);
    }
    public int append(T entity) {
        logger("read", entity.toString());
        return adapter.append(entity);
    }
    public void write(List<T> entities) {
        String str = "";
        for (T entity: entities) {
            str = String.join(" ", str, entity.toString());
        }
        logger("read", str);
        adapter.write(entities);
    }
    public void logger(String action, String args) {
        System.out.format("Вызыван метод %s класса CSVAdapter с аргументами - %s \n", action, args);
    }
}
