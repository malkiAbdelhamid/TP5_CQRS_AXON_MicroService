package com.esi.mscommandlibrary.aggregate;

import com.esi.coreapi.commands.AddBookCommand;
import com.esi.coreapi.commands.LibraryCreationCommand;
import com.esi.coreapi.commands.RemoveBookCommand;
import com.esi.coreapi.events.BookAddedEvent;
import com.esi.coreapi.events.BookRemovedEvent;
import com.esi.coreapi.events.LibraryCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Aggregate
@Data @AllArgsConstructor @NoArgsConstructor
public class LibraryAggregare {

    @AggregateIdentifier
      private String libraryId;
        private String name;
        private List<String>  isbnBoosks;


        @CommandHandler
        public LibraryAggregare(LibraryCreationCommand cmd)        {
            Assert.notNull(cmd.getLibraryId(), "LibraryId should be not null");
            Assert.notNull(cmd.getName(), "name should be not null");

            AggregateLifecycle.apply( new LibraryCreatedEvent(cmd.getLibraryId(),cmd.getName()));
        }

        @EventSourcingHandler
        public void on (LibraryCreatedEvent event)
        {
            this.libraryId=event.getLibraryId();
            this.name=event.getName();
            this.isbnBoosks=new ArrayList<>();
        }

        @CommandHandler
      public  void handles(AddBookCommand cmd) throws Exception {
            Assert.notNull(cmd.getLibraryId(),"LibraryId should be not null");
            Assert.notNull(cmd.getIsbn(),"ISBN should be not null");

            if(this.isbnBoosks.contains(cmd.getIsbn()))
                throw new Exception("Book ISBN must be unique");

           AggregateLifecycle.apply(new BookAddedEvent(cmd.getLibraryId(),cmd.getIsbn(),cmd.getTitle()));
        }

        @EventSourcingHandler
    public void on(BookAddedEvent event)
        {
            this.isbnBoosks.add(event.getIsbn());
        }

      @CommandHandler
    public void handler(RemoveBookCommand cmd) throws Exception {
          Assert.notNull(cmd.getLibraryId(),"LibraryId should be not null");
          Assert.notNull(cmd.getIsbn(),"ISBN should be not null");

          if(!this.isbnBoosks.contains(cmd.getIsbn()))
              throw new Exception("Book ISBN must be exist");

          AggregateLifecycle.apply(new BookRemovedEvent(cmd.getLibraryId(),cmd.getIsbn()));
      }

      @EventSourcingHandler
    public void on (BookRemovedEvent event )
      {
          this.isbnBoosks.remove(event.getIsbn());
      }

}
