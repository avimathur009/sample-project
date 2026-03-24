package com.example.demo.service.orderManagement.cart;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Product;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    /** Add (or increase quantity of) a product in the cart. */
    public Cart addToCart(Long cartId, Long productId, int quantity) {
        Cart cart = getCart(cartId);
        Product product = getProduct(productId);

        if (product.getProductQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + product.getProductName());
        }

        cart.getProductVsCount().merge(product, quantity, Integer::sum);
        return cartRepository.save(cart);
    }

    /** Remove a product entirely from the cart. */
    public Cart deleteFromCart(Long cartId, Long productId) {
        Cart cart = getCart(cartId);
        Product product = getProduct(productId);

        cart.getProductVsCount().remove(product);
        return cartRepository.save(cart);
    }

    /** Clear all items from the cart. */
    public Cart emptyCart(Long cartId) {
        Cart cart = getCart(cartId);
        cart.getProductVsCount().clear();
        return cartRepository.save(cart);
    }

    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }
}
