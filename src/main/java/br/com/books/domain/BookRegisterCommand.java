
package br.com.books.domain;

import org.hibernate.validator.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

/**
 * This class represents a book register command
 * 
 * @author Nathalia Temudo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookRegisterCommand implements br.com.books.domain.Entity {

   private static final long serialVersionUID = 1L;

   @ApiModelProperty(name = "title", required = true, example = "Book Title Example", position = 1)
   @NotEmpty
   private String title;

   @ApiModelProperty(name = "description", required = true, readOnly = false, example = "Book Description Example", position = 2)
   @NotEmpty
   private String description;

   @ApiModelProperty(name = "isbn", required = true, example = "9781617293290", position = 3)
   @NotEmpty
   private String isbn;

   @ApiModelProperty(name = "language", required = true, example = "BR", position = 4)
   @NotEmpty
   private String language;

   public BookRegisterCommand() {
   }

   public BookRegisterCommand(String title, String description, String isbn, String language) {
      this.title = title;
      this.description = description;
      this.isbn = isbn;
      this.language = language;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getIsbn() {
      return this.isbn;
   }

   public void setIsbn(String isbn) {
      this.isbn = isbn;
   }

   public String getLanguage() {
      return language;
   }

   public void setLanguage(String language) {
      this.language = language;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
      result = prime * result + ((this.isbn == null) ? 0 : this.isbn.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      BookRegisterCommand other = (BookRegisterCommand) obj;
      if (this.title == null) {
         if (other.title != null)
            return false;
      } else if (!this.title.equals(other.title))
         return false;
      if (this.isbn == null) {
         if (other.isbn != null)
            return false;
      } else if (!this.isbn.equals(other.isbn))
         return false;
      return true;
   }
}
