package com.iyzico.challenge.repository;

import com.iyzico.challenge.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query("UPDATE Product p SET p.name = :name ,p.description = :description,p.stock = :stock,p.price = :price  WHERE p.id = :productId")
    int updateProduct(@Param("productId") Long productId, @Param("name") String name, @Param("description") String description,
                      @Param("stock") Integer stock, @Param("price") BigDecimal price);

    @Modifying
    @Query(value = "insert into Product (id,name,stock,price,description) VALUES (:id,:name,:stock,:price,:desc)", nativeQuery = true)
    void addProductNative(@Param("id") Long id, @Param("name") String name, @Param("stock") Integer stock,
                    @Param("price") BigDecimal price, @Param("desc") String description);


}
