package com.example.demo.controller;

import com.example.demo.dto.InventoryDto;
import com.example.demo.entity.Inventory;
import com.example.demo.service.inventoryManagement.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody InventoryDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createInventory(dto));
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<Inventory>> getInventoriesByWarehouse(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(inventoryService.getInventoriesByWarehouseId(warehouseId));
    }

    @PostMapping("/{id}/categories")
    public ResponseEntity<Inventory> addCategory(@PathVariable Long id, @RequestParam String category) {
        return ResponseEntity.ok(inventoryService.addCategoryToInventory(id, category));
    }

    @DeleteMapping("/{id}/categories/{category}")
    public ResponseEntity<Inventory> removeCategory(@PathVariable Long id, @PathVariable String category) {
        return ResponseEntity.ok(inventoryService.removeCategoryFromInventory(id, category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody InventoryDto dto) {
        return ResponseEntity.ok(inventoryService.updateInventory(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}
