
package br.com.books.service;

import br.com.books.domain.Book;

/**
 * Interface that defines operation for a Service Book
 * 
 * @author Nathalia Temudo
 */
public interface BookCommandService {

   /**
    * ADD an book
    * 
    * @param book
    */
   void add(Book book);

   /**
    * Clear the book cache
    */
   void clearCache();
}
