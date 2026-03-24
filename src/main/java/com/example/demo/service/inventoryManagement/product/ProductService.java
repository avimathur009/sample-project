package com.example.demo.service.inventoryManagement.product;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Inventory;
import com.example.demo.entity.Product;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    public Product createProduct(ProductDto dto) {
        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setProductBrand(dto.getProductBrand());
        product.setProductCategory(dto.getProductCategory());
        product.setProductQuantity(dto.getProductQuantity());
        product.setPrice(dto.getPrice());
        product.setManufacturedDate(dto.getManufacturedDate());

        if (dto.getInventoryId() != null) {
            Inventory inventory = inventoryRepository.findById(dto.getInventoryId())
                    .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + dto.getInventoryId()));
            product.setInventory(inventory);
        }

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByProductCategory(category.toUpperCase());
    }

    public List<Product> getProductsByInventoryId(Long inventoryId) {
        return productRepository.findByInventory_InventoryId(inventoryId);
    }

    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByProductBrand(brand);
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByProductNameContainingIgnoreCase(name);
    }

    public Product updateProduct(Long id, ProductDto dto) {
        Product product = getProductById(id);
        product.setProductName(dto.getProductName());
        product.setProductBrand(dto.getProductBrand());
        product.setProductCategory(dto.getProductCategory());
        product.setProductQuantity(dto.getProductQuantity());
        product.setPrice(dto.getPrice());
        product.setManufacturedDate(dto.getManufacturedDate());

        if (dto.getInventoryId() != null) {
            Inventory inventory = inventoryRepository.findById(dto.getInventoryId())
                    .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + dto.getInventoryId()));
            product.setInventory(inventory);
        }

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
