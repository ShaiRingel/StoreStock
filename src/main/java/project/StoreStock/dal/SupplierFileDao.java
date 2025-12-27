package project.StoreStock.dal;

import org.springframework.stereotype.Repository;
import project.StoreStock.entity.Supplier;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class SupplierFileDao {

    private final String FILENAME = "suppliers.dat";

public List<Product> getAll() throws IOException, ClassNotFoundException {
    File file = new File(FILENAME);    
    if (!file.exists()) {
        return new ArrayList<>();
    }
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        List<Product> products = (List<Product>) ois.readObject();
        
        Collections.sort(products);

        if (!products.isEmpty()) {
            int maxId = products.stream()
                                .mapToInt(Product::getId) 
                                .max()                
                                .orElse(0);           
            
            Product.setCounter(maxId + 1);
        }        
        return products;
    }
}

    public void save(Supplier supplier) throws IOException, ClassNotFoundException {
        List<Supplier> suppliers = getAll();
        suppliers.add(supplier);
        writeToFile(suppliers);
    }

    public void update(Supplier supplier) throws IOException, ClassNotFoundException {
        List<Supplier> suppliers = getAll();
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getId().equals(supplier.getId())) {
                suppliers.set(i, supplier);
                break;
            }
        }
        writeToFile(suppliers);
    }

    public void delete(String id) throws IOException, ClassNotFoundException {
        List<Supplier> suppliers = getAll();
        suppliers.removeIf(s -> s.getId().equals(id));
        writeToFile(suppliers);
    }

    public Supplier get(String id) throws IOException, ClassNotFoundException {
        List<Supplier> suppliers = getAll();
        for (Supplier s : suppliers) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    private void writeToFile(List<Supplier> suppliers) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(suppliers);
        }
    }
}
