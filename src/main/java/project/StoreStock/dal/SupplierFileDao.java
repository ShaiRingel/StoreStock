package project.StoreStock.dal;

import org.springframework.stereotype.Repository;
import project.StoreStock.entity.Supplier;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class SupplierFileDao implements SupplierDAO {

    private final String FILENAME = "suppliers.dat";
    private final List<Supplier> suppliers;

    public SupplierFileDao() {
        this.suppliers = initFromFile();
    }

    private List<Supplier> initFromFile() {
        File file = new File(FILENAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Supplier> loadedList = (List<Supplier>) ois.readObject();
            Collections.sort(loadedList);

            if (!loadedList.isEmpty()) {
                int maxId = loadedList.stream()
                        .mapToInt(Supplier::getId)
                        .max()
                        .orElse(0);
                Supplier.setCounter(maxId);
            }
            return loadedList;
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public List<Supplier> getAll() {
        return this.suppliers;
    }

    public void save(Supplier supplier) throws IOException {
        suppliers.add(supplier);
        saveToFile();
    }

    public void update(Supplier supplier) throws IOException {
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getId() == supplier.getId()) {
                suppliers.set(i, supplier);
                break;
            }
        }
        saveToFile();
    }

    public void delete(int id) throws IOException {
        suppliers.removeIf(s -> s.getId() == id);
        saveToFile();
    }

    public Supplier get(int id) {
        return suppliers.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private void saveToFile() throws IOException {
        Collections.sort(suppliers);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(suppliers);
        }
    }
}
