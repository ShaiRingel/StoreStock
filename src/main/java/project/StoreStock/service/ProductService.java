package project.StoreStock.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.StoreStock.dal.ProductDAO;
import project.StoreStock.entity.Product;
import project.StoreStock.exceptions.IDNotFoundException;
import project.StoreStock.exceptions.MaxQuantityReachedException;
import project.StoreStock.exceptions.ValidationException;

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
            throw new MaxQuantityReachedException("Products", maxProducts);;

        productDao.save(product);
    }

    public void update(Product product) throws Exception {
        validate(product);

        if (productDao.get((product.getId())) == null) {
            throw new IDNotFoundException("Update", "Products", product.getId());
        }

        productDao.update(product);
    }

    public void delete(int id) throws Exception {
        if (productDao.get(id) == null) {
            throw new IDNotFoundException("Delete", "Product", id);
        }
        productDao.delete(id);
    }

    public Product get(int id) throws Exception {
        return productDao.get(id);
    }

    private void validate(Product product) throws Exception {
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Product> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new ValidationException(sb.toString());
        }
    }

}
