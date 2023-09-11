package io.github.vuktales.techbooks.repositories;

import io.github.vuktales.techbooks.domain.Book;
import org.springframework.data.repository.CrudRepository;


public interface BookRepository extends CrudRepository<Book, Long> {
}
