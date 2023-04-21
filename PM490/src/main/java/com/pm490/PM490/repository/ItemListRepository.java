package com.pm490.PM490.repository;

import com.pm490.PM490.model.ItemList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ItemListRepository extends JpaRepository<ItemList,Long> {

    @Query(value = "SELECT * FROM ItemList i WHERE i.user_id = :ui and i.product_id = :pi limit 1", nativeQuery = true)
    ItemList findByUserAndProductIds(@Param("ui") long ui, @Param("pi") long pi);

    @Query(value = "SELECT * FROM ItemList i WHERE i.user_id = :ui and purchaseStatus = 'CREATED'", nativeQuery = true)
    List<ItemList> findByUserAndCreated(@Param("ui") long ui);

    @Query("select il from ItemList il where il.purchaseStatus = 'ORDERED'")
    List<ItemList> findOrderedItems();
}
