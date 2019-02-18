
package br.com.books.repository;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.books.builder.BookBuilder;
import br.com.books.domain.Book;
import utils.BookAssert;

/**
 * Class that test Book Repository
 * 
 * @author Nathalia Temudo
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@EntityScan("br.com.books.domain")
public class BookRepositoryTests {

   private static final Long ID_TWO = 2l;
   private static final String TEST_BOOK_TITLE = "Book title2 example";

   @Autowired
   private TestEntityManager entityManager;

   @Autowired
   private BookRepository bookRepository;

   @Before
   public void Init() {
   }

   @Test
   public void whenFindByID_thenReturnBook() {
      // Arrange
      Book book = new BookBuilder().build(null);
      this.bookRepository.save(book);
      this.entityManager.flush();

      // Act
      Book found = this.bookRepository.findOne(book.getBookID());

      // Assert
      BookAssert.verify(found, book);
   }

   @Test
   public void whenFindByID_IDInexistent_thenReturnNotFoundBook() throws Exception {
      // Act
      Book found = this.bookRepository.findOne(ID_TWO);

      // Assert
      Assert.assertNull(found);
   }

   @Test
   public void whenFindAllBooks_thenReturnAllBooks() {
      // Arrange
      Book book = new BookBuilder().build(null);
      book.setTitle(TEST_BOOK_TITLE);

      this.entityManager.persist(book);
      this.entityManager.flush();
      List<Book> listBooks = new ArrayList<Book>();
      listBooks.add(book);

      List<Book> listBooksFound = null;

      // Act
      listBooksFound = this.bookRepository.findAll();

      // Assert
      Assert.assertNotNull(listBooksFound);
      Assert.assertTrue(listBooks.equals(listBooksFound));
   }
}
