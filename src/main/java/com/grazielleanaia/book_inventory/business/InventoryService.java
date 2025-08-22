package com.grazielleanaia.book_inventory.business;

import com.grazielleanaia.book_inventory.controller.dto.InventoryDTORequest;
import com.grazielleanaia.book_inventory.controller.dto.InventoryDTOResponse;
import com.grazielleanaia.book_inventory.controller.mapper.InventoryMapper;
import com.grazielleanaia.book_inventory.controller.mapper.InventoryUpdateMapper;
import com.grazielleanaia.book_inventory.infrastructure.entity.InventoryEntity;
import com.grazielleanaia.book_inventory.infrastructure.exception.BusinessException;
import com.grazielleanaia.book_inventory.infrastructure.exception.IllegalArgumentException;
import com.grazielleanaia.book_inventory.infrastructure.exception.ResourceNotFoundException;
import com.grazielleanaia.book_inventory.infrastructure.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryMapper mapper;
    private final InventoryUpdateMapper updateMapper;

    private final InventoryRepository repository;



    public InventoryDTOResponse saveBook(InventoryDTORequest dtoRequest) {
        if (dtoRequest == null) {
            throw new com.grazielleanaia.book_inventory.infrastructure.exception.IllegalArgumentException("Book data is required to catalog it");
        }
        try {
            InventoryEntity inventory = mapper.toEntity(dtoRequest);
            return mapper.toDTOResponse(repository.save(inventory));

        } catch (Exception e) {
            throw new BusinessException("Error occurred to save book", e);
        }
    }

    public List<InventoryDTOResponse> findAllBooks() {
        try{
            List<InventoryEntity> inventoryList = repository.findAll();
            return mapper.toListInventoryDTOResponse(inventoryList);
        } catch (Exception e) {
            throw new BusinessException("Error occurred to retrieve books", e);
        }
    }

    public InventoryDTOResponse findBookByTitle(String title) {
        InventoryEntity inventory = repository.findByTitle(title).orElseThrow(() ->
                new ResourceNotFoundException("Book title not found" + title));
        return mapper.toDTOResponse(inventory);
    }

    public List<InventoryDTOResponse> findBookByPeriod(LocalDate initialDate, LocalDate finalDate) {
        try{
            List<InventoryEntity> bookList = repository.findByPublicationDateBetween(initialDate, finalDate);
            return mapper.toListInventoryDTOResponse(bookList);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Book not found in the period", e);
        }
    }

    public List<InventoryDTOResponse> findBookByAuthor(String author) {
        try{
            List<InventoryEntity> authorList = repository.findByAuthor(author);
            return mapper.toListInventoryDTOResponse(authorList);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Author not found", e);
        }
    }

    public InventoryDTOResponse updateBook(Long id, InventoryDTORequest dtoRequest) {
//        if (!(dtoRequest != null && repository.existsById(id))) {
//            throw new IllegalArgumentException("Book data is required to update it");
//        }
        InventoryEntity inventory = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("book id not found"));

        InventoryEntity inventory1 = updateMapper.updateInventory(dtoRequest, inventory);
        return mapper.toDTOResponse(repository.save(inventory1));
    }

    public void deleteBook(Long id) {
        InventoryEntity inventory = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Book id not found" + id));
        repository.deleteById(id);
    }

}
