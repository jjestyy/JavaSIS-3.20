package pro.sisit.unit9.data;

import org.springframework.data.repository.CrudRepository;
import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.Customer;
import pro.sisit.unit9.entity.PurchasedBook;

import java.util.List;

public interface PurchasedBookRepository extends CrudRepository<PurchasedBook, Long> {
    List<PurchasedBook> findByBookAndCustomer(Book book, Customer customer);
    List<PurchasedBook> findByBook(Book book);
    List<PurchasedBook> findByCustomer(Customer customer);

}
