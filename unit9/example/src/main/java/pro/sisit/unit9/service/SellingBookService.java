package pro.sisit.unit9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sisit.unit9.data.PurchasedBookRepository;
import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.Customer;
import pro.sisit.unit9.entity.PurchasedBook;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SellingBookService {

    @Autowired
    private PurchasedBookRepository purchasedBookRepository;

    public void sellBook(Customer customer, Book book, BigDecimal price) {
        PurchasedBook purchasedBook = new PurchasedBook();
        purchasedBook.setBook(book);
        purchasedBook.setCustomer(customer);
        purchasedBook.setPrice(price);
        purchasedBookRepository.save(purchasedBook);
    }

    public BigDecimal calculateCostForBook(Book book) {
        return calculateCost(purchasedBookRepository.findByBook(book));
    }

    public BigDecimal calculateCostForCustomer(Customer customer) {
        return calculateCost(purchasedBookRepository.findByCustomer(customer));
    }

    private BigDecimal calculateCost (List<PurchasedBook> list) {
        return list.stream()
                .map(PurchasedBook::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

}
