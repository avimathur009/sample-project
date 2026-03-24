package com.example.demo.service.inventoryManagement.warehouse;

import com.example.demo.dto.InventoryDto;
import com.example.demo.dto.WarehouseDto;
import com.example.demo.service.inventoryManagement.inventory.Inventory;
import com.example.demo.util.Address;

import java.util.List;

public class WarehouseTypeA implements Warehouse{
    @Override
    public Long addWarehouse(WarehouseDto warehouse) {
        return 0L;
    }

    @Override
    public Long addInventoryInWarehouse(InventoryDto inventoryDto) {
        return 0L;
    }

    @Override
    public List<Inventory> getInventoryListBasedOnAddress(Address address) {
        return List.of();
    }

    @Override
    public List<Address> getListOfAddresses() {
        return List.of();
    }
}
