package com.grazielleanaia.book_inventory.infrastructure.repository;

import com.grazielleanaia.book_inventory.infrastructure.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

    Optional<InventoryEntity> findByTitle(String title);

    List<InventoryEntity> findByAuthor(String author);

    List<InventoryEntity> findByPublicationDateBetween(LocalDate initialDate, LocalDate finalDate);
}
