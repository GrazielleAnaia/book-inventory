package com.grazielleanaia.book_inventory.infrastructure.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory")
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank
    @Column(name = "author", nullable = false)
    private String author;

    @NotBlank
    @Column(name = "isbn", nullable = false)
    private String isbn;

    @NotNull
    @Column(name = "copies", nullable = false)
    private Integer numberCopies;

    @NotNull
    @Column(name = "publication_date")
    private LocalDate publicationDate;

}
