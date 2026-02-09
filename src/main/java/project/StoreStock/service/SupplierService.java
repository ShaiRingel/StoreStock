package project.StoreStock.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.StoreStock.dal.SupplierDAO;
import project.StoreStock.entity.Product;
import project.StoreStock.entity.Supplier;
import project.StoreStock.exceptions.IDNotFoundException;
import project.StoreStock.exceptions.MaxQuantityReachedException;
import project.StoreStock.exceptions.SupplierHasProductsException;
import project.StoreStock.exceptions.ValidationException;

import java.util.List;
import java.util.Set;

@Service
public class SupplierService {
    private final ProductService productService;
    private final SupplierDAO supplierDao;
    private final Validator validator;

    @Value("${suppliers.max}")
    private int maxSuppliers;

    @Autowired
    public SupplierService(ProductService productService, SupplierDAO supplierDao, Validator validator) {
        this.productService = productService;
        this.supplierDao = supplierDao;
        this.validator = validator;
    }

    public List<Supplier> getAll() throws Exception {
        return supplierDao.getAll();
    }

    public void save(Supplier supplier) throws Exception {
        validate(supplier);

        if (supplierDao.getAll().size() >= maxSuppliers)
            throw new MaxQuantityReachedException("Supplier", maxSuppliers);

        supplierDao.save(supplier);
    }

    public void update(Supplier supplier) throws Exception {
        validate(supplier);

        if (supplierDao.get(supplier.getId()) == null) {
            throw new IDNotFoundException("Update", "Supplier", supplier.getId());
        }

        supplierDao.update(supplier);
    }

    public void delete(int id) throws Exception {
        if (supplierDao.get(id) == null)
            throw new IDNotFoundException("Delete", "Supplier", id);

        if (getSupplierProducts(id).isEmpty())
            supplierDao.delete(id);
        else
            throw new SupplierHasProductsException(id);
    }

    public Supplier get(int id) throws Exception {
        return supplierDao.get(id);
    }

    public List<Product> getSupplierProducts(int supplierId) throws Exception {
        return productService.getAll().stream().
                filter(p -> p.getSupplier().getId() == supplierId).
                toList();
    }

    private void validate(Supplier supplier) throws Exception {
        Set<ConstraintViolation<Supplier>> violations = validator.validate(supplier);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Supplier> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new ValidationException(sb.toString());
        }
    }

    public Supplier getById(int id) throws Exception {
        return supplierDao.get(id);
    }

    public boolean exists(int id) throws Exception {
        return supplierDao.get(id) != null;
    }

    public List<Supplier> findByName(String name) throws Exception {
        return supplierDao.getAll().stream()
                .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }
}