
package br.com.books.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for Not found Books
 * 
 * @author Nathalia Temudo
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public NotFoundException() {
   }

   public NotFoundException(String msg) {
      super(msg);
   }

}
