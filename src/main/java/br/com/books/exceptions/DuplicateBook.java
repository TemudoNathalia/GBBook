
package br.com.books.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for Duplicated books
 * 
 * @author Nathalia Temudo
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateBook extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public DuplicateBook(String msg) {
      super(msg);
   }
}
