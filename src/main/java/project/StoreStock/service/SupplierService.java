package project.StoreStock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.StoreStock.dal.ProductDAO;
import project.StoreStock.dal.SupplierDAO;
import project.StoreStock.entity.Product;
import project.StoreStock.entity.Supplier;

import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierDAO supplierDAO;

    public List<Supplier> getAll() throws Exception {
        return supplierDAO.getAll();
    }

    public void save(Supplier supplier) throws Exception {
        supplierDAO.save(supplier);
    }

    public void update(Supplier supplier) throws Exception {
        supplierDAO.update(supplier);
    }

    public void delete(String id) throws Exception {
        supplierDAO.delete(id);
    }

    public Supplier get(String id) throws Exception {
        return supplierDAO.get(id);
    }
}
