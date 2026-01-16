package com.uexcel.productservice.core.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLookupRepository extends JpaRepository<ProductLookupEntity,String> {
    boolean existsByProductIdOrTitle(String productId, String title);
    ProductLookupEntity findByProductIdOrTitle(String productId, String title);
}
