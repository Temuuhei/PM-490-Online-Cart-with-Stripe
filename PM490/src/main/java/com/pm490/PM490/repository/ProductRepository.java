package com.pm490.PM490.repository;

import com.pm490.PM490.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select p from Product p where p.status =:status")
    List<Product> findAllStatus(@Param("status") ProductStatus status);

    List<Product> findAllByVendor(User vendor);

    Optional<Product> findAllByVendor_IdOrCategory_Id(long idVen, long idCat);

    List<Product> findAllByCategory(Category cat);

    @Query("select p from Product p where p.status =:status and p.name like %:searchPro% ")
    List<Product> searchProductAdvanced(@Param("status") ProductStatus status, @Param("searchPro") String searchPro);

    //color*
    @Query("select p from Product p where p.status =:status and  p.name like %:searchPro% and p.color=:col ")
    List<Product> searchProductAdvanced(@Param("status") ProductStatus status, @Param("searchPro") String searchPro, @Param("col") String col);

    //vendor*
    @Query("select p from Product p where p.status =:status and  p.name like %:searchPro% and p.vendor=:vendor ")
    List<Product> searchProductAdvanced(@Param("status") ProductStatus status, @Param("searchPro") String searchPro, @Param("vendor") User vendor);

    //category*
    @Query("select p from Product p where p.status =:status and  p.name like %:searchPro% and p.category=:idCat ")
    List<Product> searchProductAdvanced(@Param("status") ProductStatus status, @Param("searchPro") String searchPro, @Param("idCat") Category idCategory);

    //color vendor*
    @Query("select p from Product p where p.status =:status and  p.name like %:searchPro% and p.color=:col and p.vendor=:vendor")
    List<Product> searchProductAdvanced(@Param("status") ProductStatus status, @Param("searchPro") String searchPro, @Param("col") String col,  @Param("vendor") User vendor);

    //color category*
    @Query("select p from Product p where p.status =:status and  p.name like %:searchPro% and p.color=:col and p.category=:idCat")
    List<Product> searchProductAdvanced(@Param("status") ProductStatus status, @Param("searchPro") String searchPro, @Param("col") String col,  @Param("idCat") Category idCategory);

    //vendor category*
    @Query("select p from Product p where p.status =:status and  p.name like %:searchPro% and p.vendor=:vendor  and p.category=:idCat")
    List<Product> searchProductAdvanced(@Param("status") ProductStatus status, @Param("searchPro") String searchPro, @Param("vendor") User vendor,  @Param("idCat") Category idCategory);

    //color vendor category*
    @Query("select p from Product p where p.status =:status and p.name like %:searchPro% and p.color=:col and p.vendor=:vendor and p.category=:idCat")
    List<Product> searchProductAdvanced(@Param("status") ProductStatus status, @Param("searchPro") String searchPro, @Param("col") String col,  @Param("vendor") User vendor, @Param("idCat") Category idCategory);

    @Query("select distinct color from Product")
    List<String> getColors();


    @Query(value = "select u.username as username,sum(price*quantity) as sale_amount from user u inner join product p  on u.id=p.vendor_id where lower(u.role)='vendor' group by u.username", nativeQuery = true)
    List<Object[]> findAllProductByVendor();


    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Product> searchProductAdvancedproducts(@Param("query") String query);


}
