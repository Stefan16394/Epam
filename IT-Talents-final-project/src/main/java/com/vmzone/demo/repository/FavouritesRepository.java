package com.vmzone.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vmzone.demo.models.Favourite;
import com.vmzone.demo.models.Product;

@Repository
public interface FavouritesRepository extends JpaRepository<Favourite, Long> {
    Favourite findById(long id);

    Favourite findByUserUserIdAndFavouritesId(long id, long favId);

    List<Favourite> findByUserUserId(long id);
}
