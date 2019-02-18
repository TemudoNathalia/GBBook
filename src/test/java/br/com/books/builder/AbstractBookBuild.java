
package br.com.books.builder;

import java.io.Serializable;

/**
 * Abstract builder for test Book objects
 * 
 * @author Nathalia Temudo
 */
public abstract class AbstractBookBuild<OBJETO extends Serializable> {

   protected String title = "Book title example";
   protected String description = "Book description example";
   protected String isbn = "9781617293290";
   protected String language = "BR";

   public AbstractBookBuild<OBJETO> withTitle(String title) {
      this.title = title;
      return this;
   }

   public AbstractBookBuild<OBJETO> withDescription(String description) {
      this.description = description;
      return this;
   }

   public AbstractBookBuild<OBJETO> withIsbn(String isbn) {
      this.isbn = isbn;
      return this;
   }

   public AbstractBookBuild<OBJETO> withLanguage(String language) {
      this.language = language;
      return this;
   }

   public abstract OBJETO build(Long id);
}
