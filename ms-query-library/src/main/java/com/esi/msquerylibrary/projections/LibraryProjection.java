package com.esi.msquerylibrary.projections;

import com.esi.coreapi.events.BookAddedEvent;
import com.esi.coreapi.events.BookRemovedEvent;
import com.esi.coreapi.events.LibraryCreatedEvent;
import com.esi.coreapi.queries.GetBooksByLibraryQuery;
import com.esi.msquerylibrary.entities.Book;
import com.esi.msquerylibrary.entities.Library;
import com.esi.msquerylibrary.entities.LibraryRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LibraryProjection {

    @Autowired
    private LibraryRepository libraryRepository;

    @EventHandler
    public void AddLibrayry(LibraryCreatedEvent event) {
        Library library = new Library(event.getLibraryId(), event.getName(), null);
        libraryRepository.save(library);
    }

    @EventHandler
    public void addBook(BookAddedEvent event) {
        Library library = libraryRepository.findById(event.getLibraryId()).get();
        library.getBooks().add(new Book(event.getIsbn(), event.getTitle()));
        libraryRepository.save(library);
    }

    @EventHandler
    public void removebook(BookRemovedEvent event) {

        Library library = libraryRepository.findById(event.getLibraryId()).get();
        library.getBooks().remove(new Book(event.getIsbn(), null));
        libraryRepository.save(library);
    }

    @QueryHandler
    public List<Book> getBooks(GetBooksByLibraryQuery query) {
        return libraryRepository.findById(query.getLibraryId()).get().getBooks();
    }
}
