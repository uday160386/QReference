package io.github.vuktales.techbooks.services;

import io.github.vuktales.techbooks.domain.Book;

public interface BookService {

    Iterable<Book> findAll();
}
