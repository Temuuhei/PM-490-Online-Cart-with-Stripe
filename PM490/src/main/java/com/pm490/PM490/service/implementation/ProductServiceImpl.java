package com.pm490.PM490.service.implementation;

import com.pm490.PM490.dto.ProductRequest;
import com.pm490.PM490.dto.ProductSearchDto;
import com.pm490.PM490.model.*;
import com.pm490.PM490.repository.CategoryRepository;
import com.pm490.PM490.repository.ProductRepository;
import com.pm490.PM490.repository.UserRepository;
import com.pm490.PM490.service.ProductService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    public ProductRepository productRepository;

    @Autowired
    public UserRepository vendorRepository;

    @Autowired
    public CategoryRepository categoryRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAllStatus(ProductStatus status) {
        return productRepository.findAllStatus(status);
    }

    @Override
    public List<Product> findAllByVendor(User vendor) {
        return productRepository.findAllByVendor(vendor);
    }


    @Override
    public List<Product> searchProductAdvanced(ProductSearchDto productSearchDto) {
        User vendor;
        Category category;
        //color vendor category*
        if (productSearchDto.getIdVendor() != 0 &&
                productSearchDto.getIdCategory() != 0 &&
                productSearchDto.getColor() != "") {
            vendor = vendorRepository.findById(productSearchDto.getIdVendor())
                    .orElseThrow(() -> new ResourceNotFoundException("vendor doesn't exist "));
            category = categoryRepository.findById(productSearchDto.getIdCategory())
                    .orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist with id "));
            Collection<Product> test = productRepository.searchProductAdvanced(productSearchDto.getStatus(), productSearchDto.getName(), productSearchDto.getColor(), vendor, category);
            return productRepository.searchProductAdvanced(productSearchDto.getStatus(), productSearchDto.getName(), productSearchDto.getColor(), vendor, category);
            //vendor category*
        } else if (productSearchDto.getIdVendor() != 0 &&
                productSearchDto.getIdCategory() != 0 &&
                productSearchDto.getColor() == "") {
            vendor = vendorRepository.findById(productSearchDto.getIdVendor())
                    .orElseThrow(() -> new ResourceNotFoundException("vendor doesn't exist "));
            category = categoryRepository.findById(productSearchDto.getIdCategory())
                    .orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist with id "));
            return productRepository.searchProductAdvanced(productSearchDto.getStatus(), productSearchDto.getName(), vendor, category);
            //vendor color*
        } else if (productSearchDto.getIdVendor() != 0 &&
                productSearchDto.getIdCategory() == 0 &&
                productSearchDto.getColor() != "") {
            vendor = vendorRepository.findById(productSearchDto.getIdVendor())
                    .orElseThrow(() -> new ResourceNotFoundException("vendor doesn't exist "));
            return productRepository.searchProductAdvanced(productSearchDto.getStatus(), productSearchDto.getName(), productSearchDto.getColor(), vendor);
        }
        // category color*
        else if (productSearchDto.getIdVendor() == 0 &&
                productSearchDto.getIdCategory() != 0 &&
                productSearchDto.getColor() != "") {
            category = categoryRepository.findById(productSearchDto.getIdCategory())
                    .orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist with id "));
            return productRepository.searchProductAdvanced(productSearchDto.getStatus(), productSearchDto.getName(), productSearchDto.getColor(), category);
            //color*
        } else if (productSearchDto.getIdVendor() == 0 &&
                productSearchDto.getIdCategory() == 0 &&
                productSearchDto.getColor() != "") {
            return productRepository.searchProductAdvanced(productSearchDto.getStatus(), productSearchDto.getName(), productSearchDto.getColor());
            //category
        } else if (productSearchDto.getIdVendor() == 0 &&
                productSearchDto.getIdCategory() != 0 &&
                productSearchDto.getColor() == "") {
            category = categoryRepository.findById(productSearchDto.getIdCategory())
                    .orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist with id "));
            System.out.println(category.getName());
            return productRepository.searchProductAdvanced(productSearchDto.getStatus(), productSearchDto.getName(), category);
            //vendor
        } else if (productSearchDto.getIdVendor() != 0 &&
                productSearchDto.getIdCategory() == 0 &&
                productSearchDto.getColor() == "") {
            vendor = vendorRepository.findById(productSearchDto.getIdVendor())
                    .orElseThrow(() -> new ResourceNotFoundException("vendor doesn't exist "));
            return productRepository.searchProductAdvanced(productSearchDto.getStatus(), productSearchDto.getName(), vendor);
        }
            return productRepository.searchProductAdvanced(productSearchDto.getStatus(), productSearchDto.getName());

    }

    @Override
    public Product save(ProductRequest newProduct, User user) {

        Category category = categoryRepository.findById(newProduct.getIdCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist with id :" + newProduct.getIdCategory()));
        System.out.println("### CAT " + category.getName());
        Product product = new Product(newProduct.getName(),
                newProduct.getColor(),
                user,
                ProductStatus.NOTAPPROVED,
                newProduct.getQuantity(),
                category,
                newProduct.getPrice());
        return productRepository.save(product);
    }

    @Override
    public Product update(long id, ProductRequest editedProduct, User user) {
        Optional<Product> optionalProduct = Optional.ofNullable(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product doesn't exist with id :" + id)));
        Product product = optionalProduct.get();
        if (optionalProduct.isPresent()) {
            product.setName(editedProduct.getName());
            product.setColor(editedProduct.getColor());
            product.setVendor(user);
            product.setStatus(editedProduct.getStatus());
            product.setQuantity(editedProduct.getQuantity());
            Category newCategory = categoryRepository.findById(editedProduct.getIdCategory())
                    .orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist with id :" + editedProduct.getIdCategory()));
            product.setCategory(newCategory);
            product.setPrice(editedProduct.getPrice());

            product = productRepository.save(product);
        }
        return product;
    }

    @Override
    public boolean delete(long id) {
        Optional<Product> optionalProduct = Optional.ofNullable(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product doesn't exist with id :" + id)));
        Product product = optionalProduct.get();
        if (optionalProduct.isPresent()) {
            product.setStatus(ProductStatus.DELETED);
            product = productRepository.save(product);
            return !productRepository.existsById(id);
        } else {
            return false;
        }
    }

    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "/Users/temuujintsogt/Downloads/Report/";
        List<Object[]> productReports = productRepository.findAllProductByVendor();
        List<ProductReport> finalReports = new ArrayList<ProductReport>();

        for (Object[] obj : productReports) {
            String userName = (String) obj[0];
            double totalSales = (Double) obj[1];
            ProductReport summary = new ProductReport( userName, totalSales);
            finalReports.add(summary);
        }
        //load file and compile it
        File file = ResourceUtils.getFile("classpath:vendorsalesreports.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(finalReports);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Binod Kathayat");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "vendor_sales_report.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "vendor_sales_report.pdf");
        }
        return path;
    }

    @Override
    public List<Product> searchProducts(String query) {
        if (StringUtils.isEmpty(query)) {
            return productRepository.findAll();
        } else {
            return productRepository.searchProductAdvancedproducts(query);
        }
    }
}
