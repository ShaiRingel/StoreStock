package project.StoreStock.dal;

import org.springframework.stereotype.Repository;
import project.StoreStock.entity.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ProductFileDao {

    private final String FILENAME = "products.dat";

    public List<Product> getAll() throws IOException, ClassNotFoundException {
        File file = new File(FILENAME);
        
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Product> products = (List<Product>) ois.readObject();
            Collections.sort(products);
            return products;
        }
    }

    public void save(Product product) throws IOException, ClassNotFoundException {
        List<Product> products = getAll();
        products.add(product);
        writeToFile(products);
    }

    public void update(Product product) throws IOException, ClassNotFoundException {
        List<Product> products = getAll();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(product.getId())) {
                products.set(i, product);
                break;
            }
        }
        writeToFile(products);
    }

    public void delete(String id) throws IOException, ClassNotFoundException {
        List<Product> products = getAll();
        products.removeIf(p -> p.getId().equals(id));
        writeToFile(products);
    }

    public Product get(String id) throws IOException, ClassNotFoundException {
        List<Product> products = getAll();
        for (Product p : products) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    private void writeToFile(List<Product> products) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(products);
        }
    }
}
