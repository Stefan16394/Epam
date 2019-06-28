package com.vmzone.demo.repository;

import java.util.List;

import com.vmzone.demo.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vmzone.demo.models.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long>{

	List<OrderDetails> findByOrderId(long id);
}
