package project.StoreStock.dal;

import project.StoreStock.entity.Product;

import java.util.List;

public interface ProductDAO {

    List<Product> getAll() throws Exception;

    void save(Product product) throws Exception;

    void update(Product product) throws Exception;

    void delete(String id) throws Exception;

    Product get(String id) throws Exception;
}
