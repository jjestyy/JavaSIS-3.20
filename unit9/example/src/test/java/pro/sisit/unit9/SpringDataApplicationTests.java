package pro.sisit.unit9;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import pro.sisit.unit9.data.*;
import pro.sisit.unit9.entity.Author;
import pro.sisit.unit9.entity.AuthorOfBook;
import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.Customer;
import pro.sisit.unit9.service.SellingBookService;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private AuthorOfBookRepository authorOfBookRepository;

	@Autowired
	private SellingBookService sellingBookService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PurchasedBookRepository purchasedBookRepository;


	@Before
	public void init() {
		booksInit();
		sellingInit();
	}

	@After
	public void clear() {
		authorOfBookRepository.deleteAll();
		purchasedBookRepository.deleteAll();
		authorRepository.deleteAll();
		bookRepository.deleteAll();
		customerRepository.deleteAll();
	}

	private void sellingInit() {
		Customer customer1 = new Customer();
		customer1.setName("Иван Петрович");
		customer1.setAddress("2 ая продольная 47");
		customerRepository.save(customer1);

		Customer customer2 = new Customer();
		customer2.setName("Ирина Луговая");
		customer2.setAddress("Алексеева 115 кв 61");
		customerRepository.save(customer2);
	}

	private void booksInit() {
		Book book = new Book();
		book.setDescription("Увлекательные приключения Тома Сойера");
		book.setTitle("Приключения Тома Сойера");
		book.setYear(1876);
		bookRepository.save(book);

		Book book2 = new Book();
		book2.setTitle("Михаил Строгов");
		book2.setYear(1876);
		bookRepository.save(book2);

		Book book3 = new Book();
		book3.setTitle("Приключения Гекльберри Финна");
		book3.setYear(1884);
		bookRepository.save(book3);

		Author author = new Author();
		author.setFirstname("Марк");
		author.setLastname("Твен");
		authorRepository.save(author);

		AuthorOfBook authorOfBook = new AuthorOfBook();
		authorOfBook.setAuthor(author);
		authorOfBook.setBook(book);
		authorOfBookRepository.save(authorOfBook);

		AuthorOfBook authorOfBook2 = new AuthorOfBook();
		authorOfBook2.setAuthor(author);
		authorOfBook2.setBook(book3);
		authorOfBookRepository.save(authorOfBook2);

		Author author2 = new Author();
		author2.setFirstname("Жюль");
		author2.setLastname("Верн");
		authorRepository.save(author2);

		AuthorOfBook authorOfBook3 = new AuthorOfBook();
		authorOfBook3.setAuthor(author2);
		authorOfBook3.setBook(book2);
		authorOfBookRepository.save(authorOfBook3);

		Book book4 = new Book();
		book4.setTitle("Буратино");
		book4.setYear(1936);
		bookRepository.save(book4);

		Author author3 = new Author();
		author3.setFirstname("Алексей");
		author3.setLastname("Толстой");
		authorRepository.save(author3);

		AuthorOfBook authorOfBook4 = new AuthorOfBook();
		authorOfBook4.setAuthor(author3);
		authorOfBook4.setBook(book4);
		authorOfBookRepository.save(authorOfBook4);
	}



	@Test
	public void testFindByYear() {
		assertEquals(2, bookRepository.findByYear(1876).size());
		assertEquals(1, bookRepository.findByYear(1884).size());
		assertEquals(0, bookRepository.findByYear(2000).size());
	}

	@Test
	public void testFindAtPage() {
		PageRequest pageRequest = PageRequest.of(1, 1, Sort.Direction.ASC, "title");
		assertTrue(bookRepository.findAll(pageRequest)
				.get().allMatch(book -> book.getTitle().equals("Михаил Строгов")));
	}

	@Test
	public void testFindSame() {
		Book book = new Book();
		book.setYear(1876);

		assertEquals(2, bookRepository.findAll(Example.of(book)).size());
	}

	@Test
	public void testFindInRange() {
		assertEquals(3, bookRepository.findAll(
				BookSpecifications.byYearRange(1800, 1900)).size());
		assertEquals(0, bookRepository.findAll(
				BookSpecifications.byYearRange(2000, 2010)).size());
	}

	@Test
	public void testFindByAuthorLastname() {
		assertTrue(bookRepository.findByAuthor("Верн")
				.stream().allMatch(book -> book.getTitle().equals("Михаил Строгов")));
	}

	@Test
	public void testComplexQueryMethod() {
		assertEquals(4, bookRepository.complexQueryMethod().size());
	}

	@Test
	public void testSave() {
		boolean founded = false;
		for (Book iteratedBook : bookRepository.findAll()) {
			if (iteratedBook.getTitle().equals("Буратино")
					&& iteratedBook.getId() > 0) {
				founded = true;
				break;
			}
		}
		assertTrue(founded);
	}

	@Test
	public void sellingBookServiceTest() {
		Book book = bookRepository.findAll().get(0);
		Book book1 = bookRepository.findAll().get(1);
		Book book2 = bookRepository.findAll().get(2);
		Book book3 = bookRepository.findAll().get(3);
		Customer customer = ((ArrayList<Customer>) customerRepository.findAll()).get(0);
		Customer customer1 = ((ArrayList<Customer>) customerRepository.findAll()).get(1);

		sellingBookService.sellBook(customer, book, BigDecimal.valueOf(150));
		assertEquals(purchasedBookRepository.findByBookAndCustomer(book, customer).size(), 1);
		
		sellingBookService.sellBook(customer, book1, BigDecimal.valueOf(300));
		sellingBookService.sellBook(customer, book2, BigDecimal.valueOf(100));
		sellingBookService.sellBook(customer, book3, BigDecimal.valueOf(500));
		sellingBookService.sellBook(customer1, book3, BigDecimal.valueOf(500));


		assertEquals(sellingBookService.calculateCostForBook(book3).compareTo(BigDecimal.valueOf(1000)), 0);
		assertEquals(sellingBookService.calculateCostForBook(book1).compareTo(BigDecimal.valueOf(300)), 0);
		assertEquals(sellingBookService.calculateCostForCustomer(customer).compareTo(BigDecimal.valueOf(1050)), 0);
		assertEquals(sellingBookService.calculateCostForCustomer(customer1).compareTo(BigDecimal.valueOf(500)), 0);
	}


}
