package com.tiagoamp.booksapi;

import com.tiagoamp.booksapi.model.AppUser;
import com.tiagoamp.booksapi.model.Book;
import com.tiagoamp.booksapi.model.Role;
import com.tiagoamp.booksapi.repository.BookEntity;
import com.tiagoamp.booksapi.repository.BookRepository;
import com.tiagoamp.booksapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.HashSet;
import java.util.Set;



@SpringBootApplication
public class BooksApiApplication {

	@Autowired
	BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(BooksApiApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {  // inserting data after application is up
			Set<Role> roles = new HashSet<>();
			roles.add(Role.ROLE_ADMIN);
			roles.add(Role.ROLE_USER);
			userService.save(new AppUser("James Kirk", "james@enterprise.com", "123456",roles));
			BookEntity book= new BookEntity();
			book.setAuthors("John John");
			book.setTitle("title1");
			book.setLanguage("Azerbaycan");
			bookRepository.save(book);
		};
	}

	@Bean
	public ModelMapper getModelMapper() {
		var mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled(true);
		return mapper;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(10); //reload messages every 10 seconds
		return messageSource;
	}

	@Bean
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

}
