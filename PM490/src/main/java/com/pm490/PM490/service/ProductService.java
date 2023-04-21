package com.pm490.PM490.service;

import com.pm490.PM490.dto.ProductRequest;
import com.pm490.PM490.dto.ProductSearchDto;
import com.pm490.PM490.model.Product;
import com.pm490.PM490.model.ProductStatus;
import com.pm490.PM490.model.User;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    List<Product> findAllStatus(ProductStatus status);
    List<Product> findAllByVendor(User vendor);
    List<Product> searchProductAdvanced(ProductSearchDto productSearchDto);
    Product save(ProductRequest product, User user);
    Product update(long id, ProductRequest product, User user);
    boolean delete(long id);
}
