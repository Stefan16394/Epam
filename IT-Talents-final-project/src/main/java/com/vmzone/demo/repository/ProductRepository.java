package com.vmzone.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vmzone.demo.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductIdInAndIsDeletedIsNull(List<Long> categoriesIds);

    List<Product> findByPriceBetweenAndIsDeletedIsFalse(double min, double max);

    List<Product> findByTitleContainingOrInformationContainingAndIsDeletedIsFalse(String title, String information);

    List<Product> findByQuantityLessThanEqualAndIsDeletedIsFalse(int quantity);
}
