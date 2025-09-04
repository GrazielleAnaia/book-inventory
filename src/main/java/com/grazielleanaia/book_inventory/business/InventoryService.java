package com.grazielleanaia.book_inventory.business;

import com.grazielleanaia.book_inventory.controller.dto.InventoryDTORequest;
import com.grazielleanaia.book_inventory.controller.dto.InventoryDTOResponse;
import com.grazielleanaia.book_inventory.controller.mapper.InventoryMapper;
import com.grazielleanaia.book_inventory.controller.mapper.InventoryUpdateMapper;
import com.grazielleanaia.book_inventory.infrastructure.entity.InventoryEntity;
import com.grazielleanaia.book_inventory.infrastructure.exception.BusinessException;
import com.grazielleanaia.book_inventory.infrastructure.exception.PersistenceException;
import com.grazielleanaia.book_inventory.infrastructure.exception.ResourceNotFoundException;
import com.grazielleanaia.book_inventory.infrastructure.repository.InventoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mapping.MappingException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryMapper mapper;
    private final InventoryUpdateMapper updateMapper;

    private final InventoryRepository repository;


    public InventoryDTOResponse saveBook(@Valid InventoryDTORequest dtoRequest) {
        try {
            InventoryEntity inventory = mapper.toEntity(dtoRequest);
            return mapper.toDTOResponse(repository.save(inventory));

        } catch (DataAccessException e) {
            throw new PersistenceException("Error occurred to save book", e);
        }
        catch (MappingException e) {
            throw new BusinessException("Failed to convert book data", e);
        }
        catch (Exception e) {
            throw new BusinessException("An unexpected error occurred", e);
        }
    }

    public List<InventoryDTOResponse> findAllBooks() {
        List<InventoryEntity> inventoryList = repository.findAll();
        try{
            if(inventoryList.isEmpty()) {
                throw new ResourceNotFoundException("Book list is empty " + inventoryList); //404
            }
            return mapper.toListInventoryDTOResponse(inventoryList);
        } catch (BusinessException e) {
            throw new BusinessException("Unexpected error occurred to retrieve books", e); //500
        }
    }

    public InventoryDTOResponse findBookByTitle(String title) {
        InventoryEntity inventory = repository.findByTitle(title).orElseThrow(() ->
                new ResourceNotFoundException("Book title not found " + title));
        return mapper.toDTOResponse(inventory);
    }

    public List<InventoryDTOResponse> findBookByPeriod(LocalDate initialDate, LocalDate finalDate) {
        List<InventoryEntity> bookList = repository.findByPublicationDateBetween(initialDate, finalDate);
        if(bookList.isEmpty()) {
            log.info("Book not found in the period between {} and {}", initialDate, finalDate);
            throw new ResourceNotFoundException("Book not found in the period " + initialDate + finalDate); //404
        }
        return mapper.toListInventoryDTOResponse(bookList);
    }

    public List<InventoryDTOResponse> findBookByAuthor(String author) {
        List<InventoryEntity> authorList = repository.findByAuthor(author);
        if(authorList.isEmpty()) {
            log.warn("Author not found: {}", author);
            return Collections.emptyList();
        }
        return authorList.stream().map(mapper::toDTOResponse).toList();
    }

    public InventoryDTOResponse updateBook(Long id, InventoryDTORequest dtoRequest) {
        return repository.findById(id)
                .map(inventoryEntity -> updateMapper.updateInventory(dtoRequest, inventoryEntity))
                .map(repository::save)
                .map(mapper::toDTOResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Book id not found"));
    }

    public void deleteBook(Long id) {
        repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Book id not found " + id));
        repository.deleteById(id);
    }

}
