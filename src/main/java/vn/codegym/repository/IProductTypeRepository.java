package vn.codegym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.codegym.model.ProductType;

public interface IProductTypeRepository extends JpaRepository<ProductType, Integer> {
}