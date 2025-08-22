package com.grazielleanaia.book_inventory.controller;

import com.grazielleanaia.book_inventory.business.InventoryService;
import com.grazielleanaia.book_inventory.controller.dto.InventoryDTORequest;
import com.grazielleanaia.book_inventory.controller.dto.InventoryDTOResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor

public class InventoryController {

    private final InventoryService service;

    @PostMapping("/save")
    public ResponseEntity<InventoryDTOResponse> createBook(@RequestBody InventoryDTORequest dtoRequest) {
        return ResponseEntity.ok(service.saveBook(dtoRequest));
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<InventoryDTOResponse>> listAllBooks() {
        return ResponseEntity.ok(service.findAllBooks());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<InventoryDTOResponse>findBookByTitle(@PathVariable String title) {
        return ResponseEntity.ok(service.findBookByTitle(title));
    }

    @GetMapping("/period")
    public ResponseEntity<List<InventoryDTOResponse>> findBookByPeriod(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                       LocalDate initialDate,
                                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                       LocalDate finalDate) {
        return ResponseEntity.ok(service.findBookByPeriod(initialDate, finalDate));
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<InventoryDTOResponse>> findBookByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(service.findBookByAuthor(author));
    }

    @PatchMapping("/update")
    public ResponseEntity<InventoryDTOResponse> updateBook(@RequestParam("id") Long id,
                                                           @RequestBody InventoryDTORequest dtoRequest) {
        return ResponseEntity.ok(service.updateBook(id, dtoRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        service.deleteBook(id);
        return ResponseEntity.ok().build();
    }




}
