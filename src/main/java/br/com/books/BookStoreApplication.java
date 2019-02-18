
package br.com.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Profile;
import br.com.books.loader.BookLoader;

@SpringBootApplication
@EnableCaching
@Profile("!test")
public class BookStoreApplication implements ApplicationRunner {

   @Autowired
   private BookLoader bookLoader;

   public static void main(String[] args) {
      SpringApplication.run(BookStoreApplication.class, args);
   }

   @Override
   public void run(ApplicationArguments arg0) throws Exception {
      this.bookLoader.load();
   }
}
