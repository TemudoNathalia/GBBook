
package br.com.books.enums;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Strategy enum to fill Isbn
 * 
 * @author Nathalia Temudo
 */
public enum IsbnStrategyEnum implements EnumValue<String> {

      AMAZON("amazon") {

         @Override
         public String getIsbn(String link) throws IOException {
            Document doc = this.getDocument(link);
            Element element = doc.select("li:contains(ISBN-13)").first();
            if (element == null) {
               return IsbnStrategyEnum.DEFAULT.getIsbn(link);
            }
            return element.text().replaceAll("ISBN-13:", "").replaceAll("\\D", "");
         }
      },
      EDITIONS_ENIL("editions-eni") {

         @Override
         public String getIsbn(String link) throws IOException {
            Document doc = this.getDocument(link);
            Element element = doc.select("li:contains(ISBN)").first();
            if (element == null) {
               return IsbnStrategyEnum.DEFAULT.getIsbn(link);
            }
            return element.text().replaceAll("\\D", "");
         }
      },
      FUNDAMENTAL_KOTLIN("fundamental-kotlin") {

         @Override
         public String getIsbn(String link) throws IOException {
            Document doc = this.getDocument(link);
            Element element = doc.select("h2:contains(ISBN)").first();
            if (element == null) {
               return IsbnStrategyEnum.DEFAULT.getIsbn(link);
            }
            return element.text().replaceAll("\\D", "");
         }
      },
      KURAMKITAP("kuramkitap") {

         @Override
         public String getIsbn(String link) throws IOException {
            Document doc = this.getDocument(link);
            Element element = doc.select("div.prd_custom_fields_0").first();
            if (element == null) {
               return IsbnStrategyEnum.DEFAULT.getIsbn(link);
            }

            return element.children().get(2).text().replaceAll("\\D", "");
         }
      },
      MANNING_ISBN("manning") {

         @Override
         public String getIsbn(String link) throws IOException {
            Document doc = this.getDocument(link);
            Element element = doc.select("li:contains(ISBN)").first();
            if (element == null) {
               return IsbnStrategyEnum.DEFAULT.getIsbn(link);
            }
            return element.text().replaceAll("\\D", "");
         }
      },
      PACKETPUB("packtpub") {

         @Override
         public String getIsbn(String link) throws IOException {
            Document doc = this.getDocument(link);
            Element element = doc.select("[itemprop=isbn]").first();
            if (element == null) {
               return IsbnStrategyEnum.DEFAULT.getIsbn(link);
            }
            return element.text();

         }
      },
      DEFAULT("") {

         @Override
         public String getIsbn(String link) {
            return null;
         }
      };

   private String url;

   private static final int THIRD_GROUP_REGEX = 3;
   private static final String REGEX = "(http|https)?\\://(www\\.)?(\\w+|\\-)+\\.";
   private static final Pattern PATTERN = Pattern.compile(REGEX);

   /**
    * GET correspondent ISBN in the link
    * 
    * @param link URL for the book
    * @return
    * @throws IOException
    */
   public abstract String getIsbn(String link) throws IOException;

   protected Document getDocument(String link) throws IOException {
      return Jsoup.connect(link).userAgent("Mozilla/4.0").get();
   }

   IsbnStrategyEnum(String url) {
      this.url = url;
   }

   /**
    * Get the correspondent Enum
    * 
    * @param link URL for the book
    * @return
    */
   public static IsbnStrategyEnum getIsbnFactoryEnum(String link) {
      Matcher m = PATTERN.matcher(link);
      if (m.find()) {
         return Stream.of(IsbnStrategyEnum.values()).filter(d -> d.getValue().equalsIgnoreCase(m.group(THIRD_GROUP_REGEX))).findFirst()
                  .orElse(IsbnStrategyEnum.DEFAULT);
      }

      return IsbnStrategyEnum.DEFAULT;
   }

   @Override
   public String getValue() {
      return this.url;
   }
}
