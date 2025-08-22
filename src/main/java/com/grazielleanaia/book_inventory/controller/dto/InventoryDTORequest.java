package com.grazielleanaia.book_inventory.controller.dto;

import jakarta.persistence.Column;

import java.time.LocalDate;

public record InventoryDTORequest(String title,
                                  String author,
                                  String isbn,
                                  Integer numberCopies,
                                  LocalDate publicationDate) {
}
