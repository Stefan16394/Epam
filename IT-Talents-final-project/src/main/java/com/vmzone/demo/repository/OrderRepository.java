package com.vmzone.demo.repository;

import java.util.List;

import com.vmzone.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vmzone.demo.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByUserAndIsDeletedIsFalse(User user);

	Order findByOrderIdAndUser(long id,User user);
}
