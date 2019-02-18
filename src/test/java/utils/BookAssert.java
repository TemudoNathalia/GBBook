package utils;

import static org.assertj.core.api.Assertions.assertThat;
import br.com.books.domain.Book;

/**
 * Assert that verify fields of book
 * 
 * @author Nathalia Temudo
 */
public class BookAssert {

   public static void verify(Book actual, Book expected) {
      assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
      assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
      assertThat(actual.getIsbn()).isEqualTo(expected.getIsbn());
      assertThat(actual.getLanguage()).isEqualTo(expected.getLanguage());
   }
}
