
package service;

import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.books.builder.BookBuilder;
import br.com.books.domain.Book;
import br.com.books.exceptions.DuplicateBook;
import br.com.books.exceptions.RequiredFieldFaultException;
import br.com.books.repository.BookRepository;
import br.com.books.service.impl.BookCommandServiceImpl;
import utils.BookAssert;

/**
 * Class that test Book Services
 * 
 * @author Nathalia Temudo
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class BookCommandServiceTests {

   private static final Long ID_ONE = 1l;
   private static final String MSG_BOOK_NOTFOUND = "msg.book.notfound";
   private static final String MSG_BOOK_REPOSITORY_EMPTY = "msg.book.repository.empty";
   private static final String MSG_BOOK_DUPLICATED_TITLE = "msg.book.duplicated.title";
   private static final String MSG_BOOK_DUPLICATED_ISBN = "msg.book.duplicated.isbn";
   private static final String MSG_BOOK_REQUIRED_FIELD = "msg.book.field.required";

   @Mock
   private BookRepository bookRepository;

   @Mock
   private Environment env;

   @InjectMocks
   @Spy
   private BookCommandServiceImpl bookService;
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
      when(env.getProperty(MSG_BOOK_REQUIRED_FIELD)).thenReturn(MSG_BOOK_REQUIRED_FIELD);

      this.book = new BookBuilder().build(ID_ONE);
      when(bookRepository.findOne(ID_ONE)).thenReturn(book);
      when(bookRepository.save(book)).thenReturn(book);
   }

   @Test
   public void whenAddBook_thenAddedBook() {

      // Arrange
      Book book = new BookBuilder().build(ID_ONE);

      // Act
      bookService.add(book);

      // Assert
      BookAssert.verify(book, this.book);
   }

   @SuppressWarnings("unchecked")
   @Test(expected = DuplicateBook.class)
   public void whenAddBook_SameTitle_thenReturnDuplicateTitleBook() {

      // Arrange
      Book book = new BookBuilder().build(ID_ONE);

      when(this.bookRepository.findAll((Specifications<Book>) Mockito.any())).thenReturn(Arrays.asList(book));

      // Act
      this.bookService.add(book);

   }

   @SuppressWarnings("unchecked")
   @Test(expected = DuplicateBook.class)
   public void whenAddBook_SameISBN_thenReturnDuplicateISBNBook() {

      // Arrange
      Book book = new BookBuilder().build(ID_ONE);

      when(this.bookRepository.findAll((Specifications<Book>) Mockito.any())).thenAnswer(new Answer<ArrayList<Book>>() {

         private int count = 0;

         @Override
         public ArrayList<Book> answer(InvocationOnMock invocation) throws Throwable {
            if (++count == 2) {
               return new ArrayList<Book>(Arrays.asList(book));
            }
            return new ArrayList<Book>();
         }
      });

      // Act
      this.bookService.add(book);
   }

   @Test(expected = RequiredFieldFaultException.class)
   public void whenAddBook_WithoutISBN_thenReturnRequiredFieldFaultException() {

      // Arrange
      Book book = new BookBuilder().build(ID_ONE);
      book.setIsbn(null);
      // Act
      this.bookService.add(book);
   }
}
