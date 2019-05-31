package com.vmzone.demo.repository;

import com.vmzone.demo.models.ShoppingCartItem;
import com.vmzone.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartItem,Long> {

    List<ShoppingCartItem> findByUser(User user);

    @Transactional
    void deleteByUserAndProductProductId(User user, long id);

    @Transactional
    void deleteByUser(User user);
}
