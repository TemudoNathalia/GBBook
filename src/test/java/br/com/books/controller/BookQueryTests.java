
package br.com.books.controller;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import br.com.books.BookStoreApplicationTest;
import br.com.books.builder.BookBuilder;
import br.com.books.domain.Book;
import br.com.books.domain.BookList;
import br.com.books.exceptions.NotFoundException;
import br.com.books.service.BookQueryService;

/**
 * Class that test Book Queries
 * 
 * @author Nathalia Temudo
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookStoreApplicationTest.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class BookQueryTests {

   private static final Long ID_ONE = 1l;
   private static final long ID_TWO = 2l;
   private static final long ID_THREE = 3l;

   private static final String TEST_BOOK_TITLE = "Book title example";
   private static final String TEST_BOOK_DESCRIPTION = "Book description example";
   private static final String TEST_ISBN = "9781617293290";
   private static final String TEST_LANGUAGE = "BR";

   private static final String JSON_BOOK_ID = "$.id";
   private static final String JSON_TITLE = "$.title";
   private static final String JSON_DESCRIPTION = "$.description";
   private static final String JSON_ISBN = "$.isbn";
   private static final String JSON_LANGUAGE = "$.language";

   private static final String TITLE_NODE = "title";
   private static final String DESCRIPTION_NODE = "description";
   private static final String ISBN_NODE = "isbn";
   private static final String LANGUAGE_NODE = "language";

   private static final String URL_POST = "/api/v1/books";
   private static final String GET_URL = "/api/v1/books/{id}";
   private static final String ETAG = "ETag";

   private MockMvc mockQueryMvc;

   @Mock
   private BookQueryService bookQueryService;

   @InjectMocks
   private BookQueryController bookQueryController;

   @Autowired
   private ShallowEtagHeaderFilter shallowEtagHeaderFilter;

   private Book book;

   @Before
   public void Init() {
      MockitoAnnotations.initMocks(this);
      this.mockQueryMvc = MockMvcBuilders.standaloneSetup(bookQueryController).addFilters(this.shallowEtagHeaderFilter).build();
      this.book = new BookBuilder().build(ID_ONE);
   }

   @Test
   public void givenExistentBookId_whenGetBook_thenStatus200() throws Exception {
      when(this.bookQueryService.findByID(ID_ONE)).thenReturn(book);

      this.mockQueryMvc.perform(get(GET_URL, ID_ONE)).andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath(JSON_BOOK_ID, is(ID_ONE.intValue())))
               .andExpect(jsonPath(JSON_TITLE, is(TEST_BOOK_TITLE)))
               .andExpect(jsonPath(JSON_DESCRIPTION, is(TEST_BOOK_DESCRIPTION)))
               .andExpect(jsonPath(JSON_ISBN, is(TEST_ISBN))).andExpect(jsonPath(JSON_LANGUAGE, is(TEST_LANGUAGE)));

      verify(this.bookQueryService).findByID(ID_ONE);
      verifyNoMoreInteractions(this.bookQueryService);
   }

   @Test
   public void givenInexistentBookId_whenGetBook_thenStatus404() throws Exception {
      when(this.bookQueryService.findByID(ID_TWO)).thenThrow(new NotFoundException());
      this.mockQueryMvc.perform(get(GET_URL, ID_TWO)).andExpect(status().is4xxClientError());
      verify(bookQueryService).findByID(ID_TWO);
      verifyNoMoreInteractions(this.bookQueryService);
   }

   @Test
   public void givenListOfBooks_whenPostBook_thenStatus201() throws Exception {
      when(this.bookQueryService.listAllBooks()).thenReturn(new BookList(fillBookList()));

      ObjectMapper mapper = new ObjectMapper();
      ObjectNode objectNode = fillObjectNode(mapper);

      this.mockQueryMvc.perform(MockMvcRequestBuilders.get(URL_POST).contentType(MediaType.APPLICATION_JSON)
               .content(mapper.writeValueAsString(objectNode))).andExpect(status().is2xxSuccessful());
   }

   @Test
   public void givenNoBooks_whenRetrieveBooks_thenStatus404() throws Exception {
      when(this.bookQueryService.listAllBooks()).thenThrow(new NotFoundException());

      ObjectMapper mapper = new ObjectMapper();
      ObjectNode objectNode = fillObjectNode(mapper);

      this.mockQueryMvc.perform(MockMvcRequestBuilders.get(URL_POST).contentType(MediaType.APPLICATION_JSON)
               .content(mapper.writeValueAsString(objectNode))).andExpect(status().is4xxClientError());
   }

   @Test
   public void givenExistintBookId_whenRetrievingResource_thenEtagIsAlsoReturned() throws Exception {
      when(this.bookQueryService.findByID(ID_ONE)).thenReturn(book);

      final String etag = mockQueryMvc.perform(get(GET_URL, ID_ONE)).andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath(JSON_BOOK_ID, is(ID_ONE.intValue())))
               .andExpect(jsonPath(JSON_TITLE, is(TEST_BOOK_TITLE)))
               .andExpect(jsonPath(JSON_DESCRIPTION, is(TEST_BOOK_DESCRIPTION)))
               .andExpect(jsonPath(JSON_ISBN, is(TEST_ISBN))).andExpect(jsonPath(JSON_LANGUAGE, is(TEST_LANGUAGE)))
               .andExpect(header().string(ETAG, instanceOf(String.class))).andReturn().getResponse().getHeader(ETAG);

      this.mockQueryMvc.perform(get(GET_URL, ID_ONE).header("If-None-Match", etag)).andExpect(status().isNotModified());
   }

   public ShallowEtagHeaderFilter getShallowEtagHeaderFilter() {
      return this.shallowEtagHeaderFilter;
   }

   public void setShallowEtagHeaderFilter(ShallowEtagHeaderFilter shallowEtagHeaderFilter) {
      this.shallowEtagHeaderFilter = shallowEtagHeaderFilter;
   }

   private List<Book> fillBookList() {
      List<Book> books = new ArrayList<>();

      books.add(new BookBuilder().build(ID_TWO));
      books.add(new BookBuilder().build(ID_THREE));

      return books;
   }

   private ObjectNode fillObjectNode(ObjectMapper mapper) {
      ObjectNode objectNode = mapper.createObjectNode();

      objectNode.put(TITLE_NODE, book.getTitle());
      objectNode.put(DESCRIPTION_NODE, book.getDescription());
      objectNode.put(ISBN_NODE, book.getIsbn());
      objectNode.put(LANGUAGE_NODE, book.getLanguage());

      return objectNode;
   }

}
