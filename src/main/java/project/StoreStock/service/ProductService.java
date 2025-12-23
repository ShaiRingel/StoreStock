package project.StoreStock.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.StoreStock.dal.ProductDAO;
import project.StoreStock.entity.Product;

import java.util.List;
import java.util.Set;

@Service
public class ProductService {
    private final ProductDAO productDao;
    private final Validator validator;

    @Value("${products.max}")
    private int maxProducts;

    @Autowired
    public ProductService(ProductDAO productDao, Validator validator) {
        this.productDao = productDao;
        this.validator = validator;
    }

    public List<Product> getAll() throws Exception {
        return productDao.getAll();
    }

    public void save(Product product) throws Exception {
        validate(product);

        if (productDao.getAll().size() >= maxProducts)
            throw new Exception("Cannot save more than 100 products");

        productDao.save(product);
    }

    public void update(Product product) throws Exception {
        validate(product);

        if (productDao.get(String.valueOf(product.getId())) == null) {
            throw new Exception("Cannot update product with ID " + product.getId() + ". It is not found");
        }

        productDao.update(product);
    }

    public void delete(String id) throws Exception {
        if (productDao.get(id) == null) {
            throw new Exception("Product with ID " + id + " was not deleted");
        }
        productDao.delete(id);
    }

    public Product get(String id) throws Exception {
        return productDao.get(id);
    }

    private void validate(Product product) throws Exception {
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Product> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new Exception(sb.toString());
        }
    }

}
