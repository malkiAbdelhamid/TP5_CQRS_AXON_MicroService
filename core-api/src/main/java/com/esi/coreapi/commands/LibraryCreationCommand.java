package com.esi.coreapi.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data @AllArgsConstructor @NoArgsConstructor
public class LibraryCreationCommand {
    @TargetAggregateIdentifier
    private String libraryId;
    private String name;
}
