package project.StoreStock.dal;

import project.StoreStock.entity.User;

import java.util.List;

public interface UserDAO {

    List<User> getAll() throws Exception;

    void save(User product) throws Exception;

    void update(User product) throws Exception;

    void delete(int id) throws Exception;

    User get(int id) throws Exception;
}
