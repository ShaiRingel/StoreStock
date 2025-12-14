package project.StoreStock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.StoreStock.dal.ProductDAO;
import project.StoreStock.entity.Product;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productDao;

    public List<Product> getAll() throws Exception {
        return productDao.getAll();
    }

    public void save(Product product) throws Exception {
        productDao.save(product);
    }

    public void update(Product product) throws Exception {
        productDao.update(product);
    }

    public void delete(String id) throws Exception {
        productDao.delete(id);
    }

    public Product get(String id) throws Exception {
        return productDao.get(id);
    }
}
