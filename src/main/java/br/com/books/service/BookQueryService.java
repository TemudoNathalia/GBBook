
package br.com.books.service;

import br.com.books.domain.Book;
import br.com.books.domain.BookList;

/**
 * Interface that defines operation for a Service Book
 * 
 * @author Nathalia Temudo
 */
public interface BookQueryService {

   /**
    * Find an Book by ID
    * 
    * @param bookId
    * @return a found Book
    */
   Book findByID(Long bookId);

   /**
    * Retrieve all Books
    * 
    * @return Books
    */
   BookList listAllBooks();
}
