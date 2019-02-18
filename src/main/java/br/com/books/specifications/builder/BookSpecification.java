
package br.com.books.specifications.builder;

import org.springframework.data.jpa.domain.Specification;
import br.com.books.domain.Book;

/**
 * This class represents a definied specs for the Entity
 * 
 * @author Nathalia Temudo {@link Book}
 */
public class BookSpecification {

   /**
    * Predicate to query all books that have ID different to passed input
    * 
    * @param id
    * @return
    */
   public static Specification<Book> differentID(long id) {
      return (root, query, cb) -> {
         return cb.notEqual(root.get("bookID"), id);
      };
   }

   /**
    * Predicate to query all books that have title equal to passed input
    * 
    * @param title
    * @return found book
    */
   public static Specification<Book> hasTitleEqual(String title) {
      return (root, query, cb) -> {
         return cb.equal(root.get("title"), title);
      };
   }

   /**
    * Predicate to query all books that have isbn equal to passed input
    * 
    * @param isbn
    * @return found book
    */
   public static Specification<Book> hasIsbnEqual(String isbn) {
      return (root, query, cb) -> {
         return cb.equal(root.get("isbn"), isbn);
      };
   }
}