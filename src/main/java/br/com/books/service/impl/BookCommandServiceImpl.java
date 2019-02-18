
package br.com.books.service.impl;

import java.text.MessageFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import br.com.books.domain.Book;
import br.com.books.exceptions.DuplicateBook;
import br.com.books.exceptions.RequiredFieldFaultException;
import br.com.books.repository.BookRepository;
import br.com.books.service.BookCommandService;
import br.com.books.service.BookQueryService;
import br.com.books.specifications.builder.BookSpecification;

/**
 * Service that implements operation for a interface {@link BookQueryService}
 * 
 * @author Nathalia Temudo
 */
@Service
public class BookCommandServiceImpl implements BookCommandService {

   private static final String FIELD_ISBN_NAME = "isbn";
   private static final String MSG_BOOK_DUPLICATED_TITLE = "msg.book.duplicated.title";
   private static final String MSG_BOOK_DUPLICATED_ISBN = "msg.book.duplicated.isbn";
   private static final String MSG_BOOK_REQUIED_FIELD = "msg.book.field.required";
   private BookRepository bookRepository;

   @Autowired
   private Environment env;

   @Autowired
   public BookCommandServiceImpl(BookRepository bookRepository) {
      this.bookRepository = bookRepository;
   }

   @Override
   @CacheEvict(allEntries = true, cacheNames = {"book.id", "books"})
   public void add(final Book book) {
      this.validateIsbnOnlyNumber(book);
      this.validateUniqueTitleAndIsbn(null, book);
      Book bookPersisted = this.bookRepository.save(book);
      book.setBookID(bookPersisted.getBookID());
   }

   @CacheEvict(allEntries = true, cacheNames = {"book.id", "books"})
   public void clearCache() {

   }
   
   private void validateIsbnOnlyNumber(Book book) {

      if (StringUtils.isEmpty(book.getRealIsbn())) {
         throw new RequiredFieldFaultException(MessageFormat.format(env.getProperty(MSG_BOOK_REQUIED_FIELD), FIELD_ISBN_NAME));
      }
   }

   private void validateUniqueTitleAndIsbn(Long id, Book book) {
      if (isTitleExist(id, book.getTitle())) {
         throw new DuplicateBook(env.getProperty(MSG_BOOK_DUPLICATED_TITLE));
      }
      if (isIsbnExist(id, book.getIsbn())) {
         throw new DuplicateBook(env.getProperty(MSG_BOOK_DUPLICATED_ISBN));
      }
   }

   private boolean isTitleExist(Long id, String value) {
      Specifications<Book> spec = Specifications.where(BookSpecification.hasTitleEqual(value));
      spec = this.setSpecificationID(id, spec);
      return !this.bookRepository.findAll(spec).isEmpty();
   }

   private boolean isIsbnExist(Long id, String value) {
      Specifications<Book> spec = Specifications.where(BookSpecification.hasIsbnEqual(value));
      spec = this.setSpecificationID(id, spec);
      return !this.bookRepository.findAll(spec).isEmpty();
   }

   private Specifications<Book> setSpecificationID(Long id, Specifications<Book> spec) {
      if (id != null) {
         spec = spec.and(BookSpecification.differentID(id));
      }
      return spec;
   }

}
