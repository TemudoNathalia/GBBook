
package br.com.books.domain;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;

/**
 * This class represents a get books
 * 
 * @author Nathalia Temudo
 */
@JsonPropertyOrder({"numberBooks", "books"})
public class BookList implements br.com.books.domain.Entity {

   private static final long serialVersionUID = -8107515084130191034L;
   @ApiModelProperty(position = 2)
   private List<Book> books;

   public BookList(List<Book> books) {
      this.books = books;
   }

   @ApiModelProperty(position = 1)
   public int getNumberBooks() {
      return books.size();
   }

   public List<Book> getBooks() {
      return books;
   }

   public void add(Book book) {
      books.add(book);
   }
}
