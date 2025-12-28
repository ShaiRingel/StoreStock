package project.StoreStock.dal;

import org.springframework.stereotype.Repository;
import project.StoreStock.entity.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ProductFileDao implements ProductDAO {

    private final String FILENAME = "products.dat";
    private List<Product> products;

    public ProductFileDao() {
        this.products = initFromFile();
    }

    private List<Product> initFromFile() {
        File file = new File(FILENAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Product> loadedList = (List<Product>) ois.readObject();
            Collections.sort(loadedList);

            if (!loadedList.isEmpty()) {
                int maxId = loadedList.stream()
                        .mapToInt(Product::getId)
                        .max()
                        .orElse(0);
                Product.setCounter(maxId);
            }
            return loadedList;
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public List<Product> getAll() {
        return this.products;
    }

    public void save(Product product) throws IOException {
        products.add(product);
        saveToFile();
    }

    public void update(Product product) throws IOException {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                products.set(i, product);
                break;
            }
        }

        saveToFile();
    }

    public void delete(int id) throws IOException {
        products.removeIf(p -> p.getId() == id);
        saveToFile();
    }

    public Product get(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private void saveToFile() throws IOException {
        Collections.sort(products);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(products);
        }
    }
}
