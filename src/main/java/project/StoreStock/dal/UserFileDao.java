package project.StoreStock.dal;

import org.springframework.stereotype.Repository;
import project.StoreStock.entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class UserFileDao implements UserDAO {

    private final String FILENAME = "user.dat";
    private final List<User> users;

    public UserFileDao() {
        this.users = initFromFile();
    }

    private List<User> initFromFile() {
        File file = new File(FILENAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<User> loadedList = (List<User>) ois.readObject();
            Collections.sort(loadedList);

            if (!loadedList.isEmpty()) {
                int maxId = loadedList.stream()
                        .mapToInt(User::getId)
                        .max()
                        .orElse(0);
                User.setCounter(maxId);
            }
            return loadedList;
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public List<User> getAll() {
        return this.users;
    }

    public void save(User user) throws IOException {
        users.add(user);
        saveToFile();
    }

    public void update(User user) throws IOException {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                break;
            }
        }

        saveToFile();
    }

    public void delete(int id) throws IOException {
        users.removeIf(u -> u.getId() == id);
        saveToFile();
    }

    public User get(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private void saveToFile() throws IOException {
        Collections.sort(users);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(users);
        }
    }
}
