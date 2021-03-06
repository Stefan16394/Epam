package com.vmzone.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.vmzone.demo.models.ProductInSale;

public interface ProductInSaleRepository extends JpaRepository<ProductInSale, Long> {

	List<ProductInSale> findById(long id);

	@Query(value = "select * from in_sale where product_id = :prodId and start_date = :startDate and end_date = :endDate and discount_percentage = :percentage", nativeQuery = true)
	ProductInSale getProduct(@Param("prodId") long prodId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("percentage") int percentage );

	@Transactional
	void deleteByEndDateLessThan(LocalDateTime date);

	@Transactional
	void deleteById(long id);
}
