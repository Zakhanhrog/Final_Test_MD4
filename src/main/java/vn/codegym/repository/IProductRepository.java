package vn.codegym.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import vn.codegym.model.Product;

public interface IProductRepository extends PagingAndSortingRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE " +
            "(:nameSearch = '' OR p.name LIKE %:nameSearch%) AND " +
            "(:priceSearch IS NULL OR p.price >= :priceSearch) AND " +
            "(:typeIdSearch IS NULL OR p.productType.id = :typeIdSearch)")
    Page<Product> search(
            @Param("nameSearch") String name,
            @Param("priceSearch") Double price,
            @Param("typeIdSearch") Integer productTypeId,
            Pageable pageable
    );
}