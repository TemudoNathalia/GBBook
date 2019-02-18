
package br.com.books.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.books.BookStoreApplicationTest;
import br.com.books.builder.BookBuilder;
import br.com.books.domain.Book;
import br.com.books.domain.BookRegisterCommand;
import br.com.books.service.BookCommandService;

/**
 * Class that test Book Commands
 * 
 * @author Nathalia Temudo
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookStoreApplicationTest.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class BookCommandsTests {

   private static final Long ID_ONE = 1l;
   private static final String ERROR_TITLE = "Teste Para Erro";
   private static final String URL_POST = "/api/v1/books";
   private static final String TEST_BOOK_TITLE = "Book title example";
   private static final String TEST_BOOK_DESCRIPTION = "Book description example";
   private static final String TEST_ISBN = "9781617293290";
   private static final String TEST_LANGUAGE = "BR";

   private MockMvc mockMvc;

   @Mock
   private BookCommandService bookService;

   @InjectMocks
   private BookCommandController bookController;

   private Book book;

   @Mock
   private Environment env;

   @Mock
   private Errors errors;

   @Mock
   private ModelMapper modelMapper;

   @Before
   public void Init() {
      MockitoAnnotations.initMocks(this);
      this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
      this.book = new BookBuilder().build(ID_ONE);
   }

   @Test
   public void givenBookWithoutRequiredField_whenPostBook_thenStatus400() throws Exception {
      this.book.setTitle(ERROR_TITLE);
      this.mockMvc.perform(MockMvcRequestBuilders.post(URL_POST, book)).andExpect(status().is4xxClientError());
   }

   @Test
   public void givenBookWithoutRequiredField_whenPostBook_thenStatus200() throws Exception {
      Book newBook = new BookBuilder().build(ID_ONE);
      BookRegisterCommand bookRegisterCommand = new BookRegisterCommand();
      bookRegisterCommand.setTitle(TEST_BOOK_TITLE);
      bookRegisterCommand.setDescription(TEST_BOOK_DESCRIPTION);
      bookRegisterCommand.setIsbn(TEST_ISBN);
      bookRegisterCommand.setLanguage(TEST_LANGUAGE);
      when(modelMapper.map(bookRegisterCommand, Book.class)).thenReturn(newBook);

      ObjectMapper mapper = new ObjectMapper();
      String jsonInString = mapper.writeValueAsString(bookRegisterCommand);
      this.mockMvc.perform(MockMvcRequestBuilders.post(URL_POST, errors).contentType(MediaType.APPLICATION_JSON).content(jsonInString))
               .andExpect(status().is2xxSuccessful());
   }
}