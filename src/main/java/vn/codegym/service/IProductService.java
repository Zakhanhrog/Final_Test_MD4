package vn.codegym.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.codegym.model.Product;

import java.util.Optional;

public interface IProductService {
    Page<Product> findAll(String name, Double price, Integer productTypeId, Pageable pageable);
    Optional<Product> findById(int id);
    void save(Product product);
    void remove(int id);
    void removeMultiple(int[] ids);
}