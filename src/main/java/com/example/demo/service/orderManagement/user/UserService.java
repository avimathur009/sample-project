package com.example.demo.service.orderManagement.user;

import com.example.demo.dto.OrderDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.AppOrder;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.Cart;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.orderManagement.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final OrderService orderService;

    /** Create a new user with an empty cart. */
    public AppUser addUser(UserDto dto) {
        AppUser user = new AppUser();
        user.setName(dto.getName());

        Cart cart = new Cart();
        cart.setProductVsCount(new HashMap<>());
        user.setCart(cart);
        user.setOrders(new ArrayList<>());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        AppUser user = getUserById(id);
        userRepository.delete(user);
    }

    public AppUser getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    /** Delegates to OrderService — triggers full order placement flow. */
    public AppOrder placeOrder(OrderDto dto) {
        return orderService.placeOrder(dto);
    }

    /** Returns all orders for this user. */
    public List<AppOrder> seeOrders(Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    /** Returns the user's profile information. */
    public AppUser checkProfile(Long userId) {
        return getUserById(userId);
    }
}
