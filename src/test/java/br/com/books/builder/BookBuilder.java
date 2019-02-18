/**
 * 
 */

package br.com.books.builder;

import br.com.books.domain.Book;

/**
 * Builder for test Book objects
 * 
 * @author Nathalia Temudo
 */
public class BookBuilder extends AbstractBookBuild<Book> {

   public Book build(Long id) {
      return new Book(id, this.title, this.description, this.isbn, this.language);
   }
}
