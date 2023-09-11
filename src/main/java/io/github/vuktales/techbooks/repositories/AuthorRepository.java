package io.github.vuktales.techbooks.repositories;

import io.github.vuktales.techbooks.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository  extends CrudRepository<Author, Long> {

}
