package com.esi.msquerylibrary.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Library {


    @Id
    private String libraryId;
    private String name;

    @ElementCollection
    private List<Book> Books;
}
