package io.github.vuktales.techbooks.bootstrap;

import io.github.vuktales.techbooks.domain.Author;
import io.github.vuktales.techbooks.domain.Book;
import io.github.vuktales.techbooks.repositories.AuthorRepository;
import io.github.vuktales.techbooks.repositories.BookRepository;
//import io.github.vuktales.techbooks.repositories.PagesRepository;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
public class BootstrapData implements CommandLineRunner{

    private final AuthorRepository authorrepository;
    private final BookRepository bookRepository;

//    private final PagesRepository pagesRepository;

    public BootstrapData(AuthorRepository authorrepository, BookRepository bookRepository) {
        this.authorrepository = authorrepository;
        this.bookRepository = bookRepository;

//        this.pagesRepository = pagesRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author eric =  new Author();
        eric.setFirstName("Uday");
        eric.setLastName("Kumar");

        Book ddd= new Book();
        ddd.setTitle("CS");
        ddd.setIsbn("123");

        Author ericSaved = this.authorrepository.save(eric);
        Book noejdsaved = this.bookRepository.save(ddd);

        ericSaved.getBooks().add(noejdsaved);

        System.out.println("In bootstrap");
        System.out.println("Author count:" + authorrepository.count());
        System.out.println("Book count "+ bookRepository.count());

    }
}
