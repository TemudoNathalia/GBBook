
package br.com.books.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import br.com.books.loader.BookLoader;

/**
 * Main Configurations of the project
 * 
 * @author Nathalia Temudo
 */
@Configuration
public class AppConfig {

   @Bean
   public ModelMapper modelMapper() {
      return new ModelMapper();
   }

   @Bean
   public BookLoader bookLoader() {
      return new BookLoader();
   }

}
