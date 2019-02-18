
package br.com.books.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;

/**
 * This class represents a book in the system
 * 
 * @author Nathalia Temudo
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "book")
@JsonPropertyOrder({"id", "title", "description", "isbn", "language"})
public class Book implements br.com.books.domain.Entity {

   private static final long serialVersionUID = 1L;
   private static final String UNAVAILABLE = "unavailable";

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(nullable = false, updatable = false, unique = true)
   @JsonProperty(value = "id")
   @ApiModelProperty(readOnly = true, name = "id", position = 1)
   private Long bookID;

   @Column(nullable = false, unique = true)
   @NotEmpty
   @ApiModelProperty(name = "title", required = true, example = "Book Title Example", position = 2)
   private String title;

   @Lob
   @Column(nullable = false, length = 3000)
   @NotEmpty
   @ApiModelProperty(name = "description", required = true, readOnly = false, example = "Book Description Example", position = 3)
   private String description;

   @Column(unique = true)
   @ApiModelProperty(name = "isbn", required = true, example = "9781617293290", position = 4)
   private String isbn;

   @Column(nullable = false)
   @NotEmpty
   @ApiModelProperty(name = "language", required = true, example = "BR", position = 5)
   private String language;

   @Transient
   @JsonIgnore
   private String link;

   public Book() {
   }

   public Book(Long id, String title, String description, String isbn, String language) {
      this.bookID = id;
      this.title = title;
      this.description = description;
      this.isbn = isbn;
      this.language = language;
   }

   public Long getBookID() {
      return bookID;
   }

   public void setBookID(Long bookID) {
      this.bookID = bookID;
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
      return StringUtils.isEmpty(this.isbn) ? UNAVAILABLE : this.isbn;
   }

   @JsonIgnore
   public String getRealIsbn() {
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
      Book other = (Book) obj;
      if (title == null) {
         if (other.title != null)
            return false;
      } else if (!title.equals(other.title))
         return false;
      if (isbn == null) {
         if (other.isbn != null)
            return false;
      } else if (!isbn.equals(other.isbn))
         return false;
      return true;
   }

   @JsonIgnore
   public String getLink() {
      return link;
   }

   public void setLink(String link) {
      this.link = link;
   }
}
