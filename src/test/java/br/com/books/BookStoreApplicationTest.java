package br.com.books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableCaching
@Profile("test")
public class BookStoreApplicationTest {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplicationTest.class, args);
	}
}
