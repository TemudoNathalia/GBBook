
package br.com.books.loader;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.books.domain.Book;
import br.com.books.enums.IsbnStrategyEnum;
import br.com.books.enums.KotlingLangStrategy;
import br.com.books.repository.BookRepository;
import br.com.books.service.BookCommandService;

/**
 * Loader to persist the books on database
 * 
 * @author Nathalia Temudo
 */
public class BookLoader {

   private static final String MAIN_TAG_NAME = "article";
   private static final String URL_BOOKS = "https://kotlinlang.org/docs/books.html";
   private KotlingLangStrategy actualStep = KotlingLangStrategy.DEFAULT;
   private static Logger LOGGER = Logger.getLogger(BookLoader.class);

   @Autowired
   private BookCommandService bookCommandService;

   @Autowired
   private BookRepository bookRepository;

   public void load() throws IOException {
      try {
         Document doc = Jsoup.connect(URL_BOOKS).get();
         Book currentBook = new Book();
         doc.select(MAIN_TAG_NAME).first().children().forEach(element -> {
            this.controlCurrentStep(element, currentBook);
         });

         this.bookCommandService.clearCache();
      }
      catch (Exception e) {
         LOGGER.error("Error on loader", e);
      }
   }

   private void controlCurrentStep(Element element, Book currentBook) {
      KotlingLangStrategy loopStep = KotlingLangStrategy.getByElementName(element.tagName());

      if (this.actualStep.getNextElement().equals(loopStep)) {
         this.actualStep = actualStep.getNextElement();
         this.actualStep.fillBook(currentBook, element);

         if (KotlingLangStrategy.PARAGRAPH.equals(this.actualStep)) {
            this.fillIsbnBook(currentBook);
            this.persistBook(currentBook);
            currentBook = new Book();
         }
      }
   }

   private void persistBook(Book currentBook) {
      try {
         currentBook.setBookID(null);
         this.bookRepository.save(currentBook);
      }
      catch (Exception e) {
         LOGGER.error("Error on persist book ", e);
      }
   }

   private void fillIsbnBook(Book currentBook) {
      try {
         currentBook.setIsbn(IsbnStrategyEnum.getIsbnFactoryEnum(currentBook.getLink()).getIsbn(currentBook.getLink()));
      }
      catch (IOException e) {
         currentBook.setIsbn(null);
      }
   }
}
