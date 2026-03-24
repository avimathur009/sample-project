package com.example.demo.service.inventoryManagement.inventory;

import com.example.demo.dto.InventoryDto;
import com.example.demo.entity.Inventory;
import com.example.demo.entity.Warehouse;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final WarehouseRepository warehouseRepository;

    public Inventory createInventory(InventoryDto dto) {
        Inventory inventory = new Inventory();
        inventory.setInventoryName(dto.getInventoryName());
        inventory.setAddress(dto.getAddress());
        inventory.setMaximumCapacity(dto.getMaximumCapacity());
        inventory.setCurrentCapacity(0);
        inventory.setListOfCategories(dto.getListOfCategories() != null ? dto.getListOfCategories() : new ArrayList<>());

        if (dto.getWarehouseId() != null) {
            Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + dto.getWarehouseId()));
            inventory.setWarehouse(warehouse);
        }

        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));
    }

    public List<Inventory> getInventoriesByWarehouseId(Long warehouseId) {
        return inventoryRepository.findByWarehouse_WarehouseId(warehouseId);
    }

    public Inventory addCategoryToInventory(Long id, String category) {
        Inventory inventory = getInventoryById(id);
        String upperCategory = category.toUpperCase();
        if (!inventory.getListOfCategories().contains(upperCategory)) {
            inventory.getListOfCategories().add(upperCategory);
        }
        return inventoryRepository.save(inventory);
    }

    public Inventory removeCategoryFromInventory(Long id, String category) {
        Inventory inventory = getInventoryById(id);
        inventory.getListOfCategories().remove(category.toUpperCase());
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventory(Long id, InventoryDto dto) {
        Inventory inventory = getInventoryById(id);
        inventory.setInventoryName(dto.getInventoryName());
        inventory.setAddress(dto.getAddress());
        inventory.setMaximumCapacity(dto.getMaximumCapacity());

        if (dto.getListOfCategories() != null) {
            inventory.setListOfCategories(dto.getListOfCategories());
        }

        if (dto.getWarehouseId() != null) {
            Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + dto.getWarehouseId()));
            inventory.setWarehouse(warehouse);
        }

        return inventoryRepository.save(inventory);
    }

    public void deleteInventory(Long id) {
        Inventory inventory = getInventoryById(id);
        inventoryRepository.delete(inventory);
    }
}
