package project.StoreStock.dal;

import project.StoreStock.entity.Supplier;

import java.util.List;

public interface SupplierDAO {
    
    List<Supplier> getAll() throws Exception;
    
    void save(Supplier supplier) throws Exception;
    
    void update(Supplier supplier) throws Exception;
    
    void delete(int id) throws Exception;
    
    Supplier get(int id) throws Exception;

}
