
package br.com.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.books.domain.Book;
import br.com.books.domain.BookList;
import br.com.books.domain.swagger.Error;
import br.com.books.service.BookQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;

/**
 * Rest controller for query operations with book
 * 
 * @author Nathalia Temudo
 */
@RestController
@RequestMapping(value = "api/v1/books", produces = "application/json")
@Api(value = "book")
@EnableAutoConfiguration(exclude = HypermediaAutoConfiguration.class)
public class BookQueryController {

   private BookQueryService bookService;

   @Autowired
   public BookQueryController(BookQueryService bookService) {
      this.bookService = bookService;
   }

   @RequestMapping(method = RequestMethod.GET, value = "/{id}")
   @ApiOperation(value = "Find a book by ID", response = Book.class, tags = {
      "Book API"})
   @ApiResponses(value = {
      @io.swagger.annotations.ApiResponse(code = 200, message = "Operation Success.", response = Book.class),
      @io.swagger.annotations.ApiResponse(code = 404, message = "Not found book.", response = Error.class)})
   public ResponseEntity<Book> findByID(@PathVariable(name = "id") @ApiParam(name = "id", required = true) Long id) {
      Book book = this.bookService.findByID(id);
      return ResponseEntity.ok(book);
   }

   @RequestMapping(method = RequestMethod.GET)
   @ApiOperation(value = "Retrieve all books", tags = {
      "Book API"})
   @ApiResponses(value = {
      @io.swagger.annotations.ApiResponse(code = 200, message = "Operation Success.", response = BookList.class),
      @io.swagger.annotations.ApiResponse(code = 404, message = "Not found books.", response = Error.class)})
   public ResponseEntity<BookList> listBooks() {
      return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(bookService.listAllBooks());
   }

}
