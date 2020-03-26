package pro.sisit.adapter;

import java.util.List;

public interface IOAdapter<T> {

    T read(int index);
    int append(T entity);
    void write(List<T> entities);
}
