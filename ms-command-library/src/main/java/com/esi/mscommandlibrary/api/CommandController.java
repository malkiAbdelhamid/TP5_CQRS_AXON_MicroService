package com.esi.mscommandlibrary.api;

import com.esi.coreapi.commands.AddBookCommand;
import com.esi.coreapi.commands.LibraryCreationCommand;
import com.esi.coreapi.commands.RemoveBookCommand;
import com.esi.coreapi.dto.BookDTO;
import com.esi.coreapi.dto.LibraryDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("command")
public class CommandController {

    @Autowired
    private CommandGateway commandGateway;


    @PostMapping("/library")
    public  String Createlb(@RequestBody LibraryDTO libraryDTO)
    {
        commandGateway.send(new LibraryCreationCommand(libraryDTO.getLibraryId(), libraryDTO.getName()));

        return "Created";
    }

    @PostMapping("/library/{library}/book")
    public String addBook(@PathVariable String library, @RequestBody BookDTO book) {
        commandGateway.send(new AddBookCommand(library, book.getIsbn(), book.getTitle()));
        return "Added";
    }

    @DeleteMapping("/library/{library}/{isbn}")
    public String addBook(@PathVariable String library, @PathVariable String isbn) {
        commandGateway.send(new RemoveBookCommand(library, isbn));
        return "Removed";
    }


}
