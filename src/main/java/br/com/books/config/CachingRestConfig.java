
package br.com.books.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

/**
 * Cache rest ETag Configurations of the project
 * 
 * @author Nathalia Temudo
 */
@Configuration
public class CachingRestConfig {

   @Bean
   public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
      return new ShallowEtagHeaderFilter();
   }

   @Bean
   public FilterRegistrationBean shallowEtagHeaderFilterRegistration() {
      FilterRegistrationBean result = new FilterRegistrationBean();
      result.setFilter(this.shallowEtagHeaderFilter());
      result.addUrlPatterns("/*");
      result.setName("shallowEtagHeaderFilter");
      result.setOrder(1);
      return result;
   }
}
