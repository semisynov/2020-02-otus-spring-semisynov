package ru.semisynov.otus.spring.homework05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.semisynov.otus.spring.homework05.dao.BookDao;
import ru.semisynov.otus.spring.homework05.model.Book;


@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = SpringApplication.run(Main.class);

        BookDao bookDao = context.getBean(BookDao.class);

        System.out.println(bookDao.count());

        bookDao.insert(new Book(2L, "Test", 1L));

        System.out.println(bookDao.count());
        //Console.main(args);
    }

}
