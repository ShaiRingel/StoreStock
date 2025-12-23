package project.StoreStock.dal;

import project.StoreStock.entity.Product;
import project.StoreStock.entity.Supplier;

import java.util.List;

public class SupplierDAO {
    
    List<Supplier> getAll() throws Exception;
    
    void save(Supplier supplier) throws Exception;
    
    void update(Supplier supplier) throws Exception;
    
    void delete(String id) throws Exception;
    
    Supplier get(String id) throws Exception;

}
