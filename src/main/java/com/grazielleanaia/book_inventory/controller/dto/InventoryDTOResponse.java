package com.grazielleanaia.book_inventory.controller.dto;

import java.time.LocalDate;

public record InventoryDTOResponse(Long id,
                                   String title,
                                   String author,
                                   String isbn,
                                   Integer numberCopies,
                                   LocalDate publicationDate) {
}
