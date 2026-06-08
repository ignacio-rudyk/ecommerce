package com.ignacio.rudyk.generic.ecommerce.repository;

import com.ignacio.rudyk.generic.ecommerce.repository.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICartProductRepository extends JpaRepository<CartProduct, Long> {

    Optional<CartProduct> findByCartIdAndProductId(Long cartId, Long productId);

    @Query("SELECT cp FROM CartProduct cp JOIN FETCH cp.product WHERE cp.cartId = :cartId")
    List<CartProduct> findCartProductByCartId(@Param("cartId") Long cartId);

    void deleteByCartId(Long cartId);

}