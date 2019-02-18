package br.com.books.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import br.com.books.domain.Book;
import br.com.books.domain.BookList;
import br.com.books.exceptions.NotFoundException;
import br.com.books.repository.BookRepository;
import br.com.books.service.BookCommandService;
import br.com.books.service.BookQueryService;

/**
 * Service that implements operation for a interface {@link BookCommandService}
 * 
 * @author Nathalia Temudo
 */
@Service
public class BookQueryServiceImpl implements BookQueryService {

	private static final String MSG_BOOK_NOTFOUND = "msg.book.notfound";
	private static final String MSG_BOOK_REPOSITORY_EMPTY = "msg.book.repository.empty";
	private BookRepository bookRepository;

	@Autowired
	private Environment env;

	@Autowired
	public BookQueryServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	@Cacheable(value = "book.id", key = "#bookId")
	public Book findByID(Long bookId) {
		Book book = this.bookRepository.findOne(bookId);
		if (book == null) {
			throw new NotFoundException(env.getProperty(MSG_BOOK_NOTFOUND));
		}
		return book;
	}

	@Override
	@Cacheable(value = "books")
	public BookList listAllBooks() {
		List<Book> listBook = this.bookRepository.findAll();

		if (isListBookEmpty(listBook)) {
			throw new NotFoundException(env.getProperty(MSG_BOOK_REPOSITORY_EMPTY));
		}

		BookList booklist = new BookList(listBook);
		return booklist;
	}

	private boolean isListBookEmpty(List<Book> listBook) {
		return listBook == null || listBook.isEmpty();
	}
}
