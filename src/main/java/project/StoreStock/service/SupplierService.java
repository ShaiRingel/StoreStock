package project.StoreStock.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.StoreStock.dal.SupplierDAO;
import project.StoreStock.entity.Supplier;

import java.util.List;
import java.util.Set;

@Service
public class SupplierService {
    private final SupplierDAO supplierDao;
    private final Validator validator;

    @Value("${suppliers.max}")
    private int maxSuppliers;

    @Autowired
    public SupplierService(SupplierDAO supplierDao, Validator validator) {
        this.supplierDao = supplierDao;
        this.validator = validator;
    }

    public List<Supplier> getAll() throws Exception {
        return supplierDao.getAll();
    }

    public void save(Supplier supplier) throws Exception {
        validate(supplier);

        if (supplierDao.getAll().size() >= maxSuppliers)
            throw new Exception("Cannot save more than 50 suppliers");

        supplierDao.save(supplier);
    }

    public void update(Supplier supplier) throws Exception {
        validate(supplier);

        if (supplierDao.get(supplier.getId()) == null) {
            throw new Exception("Cannot update supplier with ID " + supplier.getId() + ". It is not found");
        }

        supplierDao.update(supplier);
    }

    public void delete(String id) throws Exception {
        if (supplierDao.get(id) == null) {
            throw new Exception("Supplier with ID " + id + " was not deleted");
        }
        supplierDao.delete(id);
    }

    public Supplier get(String id) throws Exception {
        return supplierDao.get(id);
    }

    private void validate(Supplier supplier) throws Exception {
        Set<ConstraintViolation<Supplier>> violations = validator.validate(supplier);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Supplier> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new Exception(sb.toString());
        }
    }
}