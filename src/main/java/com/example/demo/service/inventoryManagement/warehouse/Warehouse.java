package com.example.demo.service.inventoryManagement.warehouse;

import com.example.demo.dto.InventoryDto;
import com.example.demo.dto.WarehouseDto;
import com.example.demo.service.inventoryManagement.inventory.Inventory;
import com.example.demo.util.Address;

import java.util.List;

public interface Warehouse {
    Long addWarehouse(WarehouseDto warehouse);

    Long addInventoryInWarehouse(InventoryDto inventoryDto);

    List<Inventory> getInventoryListBasedOnAddress(Address address);

    List<Address> getListOfAddresses();
}
