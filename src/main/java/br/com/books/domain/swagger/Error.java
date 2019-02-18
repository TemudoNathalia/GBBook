
package br.com.books.domain.swagger;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * Model of error (Swagger Only)
 * 
 * @author Nathalia Temudo
 */
public class Error {

   private Date timeStamp;
   @ApiModelProperty(example = "404")
   private Integer status;
   @ApiModelProperty(example = "Not found")
   private String error;
   @ApiModelProperty(example = "br.com.books.exceptions.NotFoundException")
   private String exception;
   @ApiModelProperty(example = "Book not found")
   private String message;
   @ApiModelProperty(example = "/api/v1/books/1")
   private String path;

   public Date getTimeStamp() {
      return timeStamp;
   }

   public void setTimeStamp(Date timeStamp) {
      this.timeStamp = timeStamp;
   }

   public Integer getStatus() {
      return status;
   }

   public void setStatus(Integer status) {
      this.status = status;
   }

   public String getError() {
      return error;
   }

   public void setError(String error) {
      this.error = error;
   }

   public String getException() {
      return exception;
   }

   public void setException(String exception) {
      this.exception = exception;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public String getPath() {
      return path;
   }

   public void setPath(String path) {
      this.path = path;
   }
}
