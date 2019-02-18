
package br.com.books.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for Only Number validation
 * 
 * @author Nathalia Temudo
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class OnlyNumbersFaultException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public OnlyNumbersFaultException(String msg) {
      super(msg);
   }
}
