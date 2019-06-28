package com.vmzone.demo.repository;

import java.util.List;

import com.vmzone.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vmzone.demo.models.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

   Photo findById(long id);

   List<Photo> findByProduct(Product product);
}
