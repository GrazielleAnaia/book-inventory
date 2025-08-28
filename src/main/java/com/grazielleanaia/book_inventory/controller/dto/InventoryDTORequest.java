package com.grazielleanaia.book_inventory.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record InventoryDTORequest(@NotBlank(message = "title is required") String title,
                                  @NotBlank(message = "Author is required") String author,
                                  @NotBlank(message = "isbn is required") String isbn,
                                  @NotNull(message = "number of copies is required") Integer numberCopies,
                                  @NotNull(message = "publication date is required") LocalDate publicationDate) {
}
