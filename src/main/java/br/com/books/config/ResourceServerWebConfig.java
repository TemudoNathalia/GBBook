
package br.com.books.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import br.com.books.interceptors.LoggerInterceptor;

/**
 * Interceptors confs of the project
 * 
 * @author Nathalia Temudo
 */
@Configuration
public class ResourceServerWebConfig extends WebMvcConfigurerAdapter {

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(new LoggerInterceptor());
   }
}
