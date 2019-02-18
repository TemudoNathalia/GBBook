
package br.com.books.controller;

import java.net.URI;
import java.text.MessageFormat;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import br.com.books.domain.Book;
import br.com.books.domain.BookRegisterCommand;
import br.com.books.domain.swagger.Error;
import br.com.books.exceptions.RequiredFieldFaultException;
import br.com.books.service.BookCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

/**
 * Rest controller for operations with book
 * 
 * @author Nathalia Temudo
 */
@RestController
@RequestMapping(value = "api/v1/books", produces = "application/json")
@Api(value = "book")
@EnableAutoConfiguration(exclude = HypermediaAutoConfiguration.class)
public class BookCommandController {

   private static final String KEY_MSG_FIELD_REQUIRED = "msg.book.field.required";

   private BookCommandService bookService;

   @Autowired
   private Environment env;
   
   @Autowired
   private ModelMapper modelMapper;

   @Autowired
   public BookCommandController(BookCommandService bookService) {
      this.bookService = bookService;
   }

   @PostMapping
   @ApiOperation(value = "Add a book", tags = {
      "Book API"})
   @ApiResponses(value = {
      @io.swagger.annotations.ApiResponse(code = 201, message = "Created Success."),
      @io.swagger.annotations.ApiResponse(code = 400, message = "General Valiations", response = Error.class)
   })

   public ResponseEntity<?> add(@Validated @RequestBody final BookRegisterCommand bookRegister, Errors errors) {

      if (errors.hasErrors()) {
         throw new RequiredFieldFaultException(
            MessageFormat.format(env.getProperty(KEY_MSG_FIELD_REQUIRED), errors.getFieldError().getField()));
      }

      Book book = modelMapper.map(bookRegister, Book.class);
      this.bookService.add(book);

      final URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}")
               .buildAndExpand(book.getBookID()).toUri();

      return ResponseEntity.created(uri).build();
   }
}
