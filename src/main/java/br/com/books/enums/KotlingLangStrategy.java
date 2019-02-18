
package br.com.books.enums;

import java.util.stream.Stream;
import org.jsoup.nodes.Element;
import br.com.books.domain.Book;

/**
 * Enum for flow of loader
 * 
 * @author Nathalia Temudo
 */
public enum KotlingLangStrategy {

      PARAGRAPH("P", null) {

         @Override
         public void fillBook(Book book, Element element) {
            book.setDescription(this.getElementValue(element));
         }
      },
      LINK("A", PARAGRAPH) {

         @Override
         public String getElementValue(Element element) {
            return element.attr(HREF);
         }

         @Override
         public void fillBook(Book book, Element element) {
            book.setLink(this.getElementValue(element));
         }
      },
      LANGUAGE("DIV", LINK) {

         @Override
         public void fillBook(Book book, Element element) {
            book.setLanguage(this.getElementValue(element));
         }
      },
      TITLE("H2", LANGUAGE) {

         @Override
         public void fillBook(Book book, Element element) {
            book.setTitle(this.getElementValue(element));
         }
      },
      DEFAULT("", TITLE) {

         @Override
         public void fillBook(Book book, Element element) {
         }
      },
      ;

   private static final String HREF = "href";
   private String element;
   private KotlingLangStrategy nextElement;

   KotlingLangStrategy(String element, KotlingLangStrategy nextElement) {
      this.element = element;
      this.nextElement = nextElement;
   }

   public abstract void fillBook(Book book, Element element);

   public String getElement() {
      return element;
   }

   protected String getElementValue(Element element) {
      return element.text();
   }

   public static KotlingLangStrategy getByElementName(String element) {
      return Stream.of(KotlingLangStrategy.values()).filter(d -> d.getElement().equalsIgnoreCase(element)).findFirst()
               .orElse(KotlingLangStrategy.DEFAULT);
   }

   public KotlingLangStrategy getNextElement() {
      if (nextElement == null) {
         return TITLE;
      }

      return nextElement;
   }

}
