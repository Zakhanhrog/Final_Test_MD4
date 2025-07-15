package vn.codegym.service;

import vn.codegym.model.ProductType;

public interface IProductTypeService {
    Iterable<ProductType> findAll();
}