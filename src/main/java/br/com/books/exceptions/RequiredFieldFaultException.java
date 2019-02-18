
package br.com.books.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for Required field
 * 
 * @author Nathalia Temudo
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RequiredFieldFaultException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public RequiredFieldFaultException(String msg) {
      super(msg);
   }
}
