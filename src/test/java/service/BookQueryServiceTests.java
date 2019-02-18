
package service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.books.builder.BookBuilder;
import br.com.books.domain.Book;
import br.com.books.domain.BookList;
import br.com.books.exceptions.NotFoundException;
import br.com.books.repository.BookRepository;
import br.com.books.service.impl.BookQueryServiceImpl;
import utils.BookAssert;

/**
 * Class that test Book Services
 * 
 * @author Nathalia Temudo
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class BookQueryServiceTests {

	private static final Long ID_ZERO = 0l;
	private static final Long ID_ONE = 1l;
	private static final Long ID_TWO = 2l;
	private static final String MSG_BOOK_NOTFOUND = "msg.book.notfound";
	private static final String MSG_BOOK_REPOSITORY_EMPTY = "msg.book.repository.empty";
	private static final String MSG_BOOK_DUPLICATED_TITLE = "msg.book.duplicated.title";
	private static final String MSG_BOOK_DUPLICATED_ISBN = "msg.book.duplicated.isbn";

	@Mock
	private BookRepository bookRepository;

	@Mock
	private Environment env;

	@InjectMocks
	private BookQueryServiceImpl bookService;
	
	private Book book;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		when(env.getProperty(MSG_BOOK_NOTFOUND)).thenReturn(MSG_BOOK_NOTFOUND);
		when(env.getProperty(MSG_BOOK_REPOSITORY_EMPTY)).thenReturn(MSG_BOOK_REPOSITORY_EMPTY);
		when(env.getProperty(MSG_BOOK_DUPLICATED_TITLE)).thenReturn(MSG_BOOK_DUPLICATED_TITLE);
		when(env.getProperty(MSG_BOOK_DUPLICATED_ISBN)).thenReturn(MSG_BOOK_DUPLICATED_ISBN);

		this.book = new BookBuilder().build(ID_ONE);
		when(this.bookRepository.findOne(ID_ONE)).thenReturn(this.book);
		when(this.bookRepository.save(this.book)).thenReturn(this.book);
	}
	

	@Test
	public void whenFindByID_thenReturnBook() {

		// Arrange
		Book book = new BookBuilder().build(ID_ONE);

		// Act
		Book found = this.bookService.findByID(ID_ONE);

		// Assert
		BookAssert.verify(found, book);
	}

	@Test(expected = NotFoundException.class)
	public void whenFindByID_IDInexistent_thenReturnNotFoundBook() throws Exception {

		// Act
		this.bookService.findByID(ID_ZERO);

		// Assert
		this.exceptionRule.expect(NotFoundException.class);
		this.exceptionRule.expectMessage(this.env.getProperty(MSG_BOOK_NOTFOUND));
	}

	@Test
	public void whenListAllBooks_NotEmptyRepository_thenReturnAllBooks() throws Exception {
		// Arrange
		Book book_2 = new BookBuilder().build(ID_TWO);
		List<Book> listBooks = new ArrayList<Book>();
		listBooks.add(this.book);
		listBooks.add(book_2);
		when(this.bookRepository.findAll()).thenReturn(listBooks);

		BookList bookList = null;

		// Act
		bookList = this.bookService.listAllBooks();

		// Assert
		Assert.assertNotNull(bookList);
		Assert.assertTrue(listBooks.size() == bookList.getNumberBooks());
		Assert.assertTrue(listBooks.equals(bookList.getBooks()));

	}

	@Test(expected = NotFoundException.class)
	public void whenListAllBooks_EmptyRepository_thenReturnNoneBook() throws Exception {
		// Act
		this.bookService.listAllBooks();

		// Assert
		this.exceptionRule.expect(NotFoundException.class);
		this.exceptionRule.expectMessage(this.env.getProperty(MSG_BOOK_REPOSITORY_EMPTY));
	}
}
