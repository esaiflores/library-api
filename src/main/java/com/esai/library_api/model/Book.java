package com.esai.library_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private String genre;
    private String isbn;
    private Integer totalCopies;
    private Integer availableCopies;
    private String notes;
    private LocalDate addedDate;
    private String coverUrl;
}