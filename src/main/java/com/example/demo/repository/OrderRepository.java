package com.example.demo.repository;

import com.example.demo.entity.AppOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<AppOrder, Long> {
    List<AppOrder> findByUser_Id(Long userId);
}
