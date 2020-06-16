package com.esi.msquerylibrary.api;

import com.esi.coreapi.queries.GetBooksByLibraryQuery;
import com.esi.msquerylibrary.entities.Book;
import com.esi.msquerylibrary.entities.Library;
import com.esi.msquerylibrary.entities.LibraryRepository;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Future;

@RestController
@RequestMapping("query")
public class QueryController {

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private LibraryRepository libraryRepository;

    @GetMapping("/library/{id}")
    public Library getLibraryById(@PathVariable String id) {
        return libraryRepository.findById(id).get();
    }

    @GetMapping("/library")
    public List<Library> getLibraries() {
        return libraryRepository.findAll();
    }

    //--------------------  withOut QueryHandler------------------------
   /* @GetMapping("/library/{id}/book")
    public List<Book> getBooksByLibre(@PathVariable String id) {
        return libraryRepository.findById(id).get().getBooks();
        }*/

    //--------------------  With QueryHandler------------------------
    @GetMapping("/library/{id}/book")
    public Future<List<Book>> getBooksByLibrary(@PathVariable String id)  {
        return  queryGateway.query(new GetBooksByLibraryQuery(id), ResponseTypes.multipleInstancesOf(Book.class));
    }

}
