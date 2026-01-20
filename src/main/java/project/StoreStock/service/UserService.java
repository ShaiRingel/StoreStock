package project.StoreStock.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.StoreStock.dal.UserDAO;
import project.StoreStock.entity.User;
import project.StoreStock.exceptions.IDNotFoundException;
import project.StoreStock.exceptions.ValidationException;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserDAO userDao;
    private final Validator validator;

    @Autowired
    public UserService(UserDAO userDao, Validator validator) {
        this.userDao = userDao;
        this.validator = validator;
    }

    public List<User> getAll() throws Exception {
        return userDao.getAll();
    }

    public void save(User user) throws Exception {
        userDao.save(user);
    }

    public void update(User user) throws Exception {
        validate(user);

        if (userDao.get(user.getId()) == null) {
            throw new IDNotFoundException("Update", "User", user.getId());
        }

        userDao.update(user);
    }

    public void delete(int id) throws Exception {
        if (userDao.get(id) == null) {
            throw new IDNotFoundException("Delete", "User", id);
        }
        userDao.delete(id);
    }

    public User get(int id) throws Exception {
        return userDao.get(id);
    }

    private void validate(User user) throws Exception {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<User> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new ValidationException(sb.toString());
        }
    }

    public User getById(int id) throws Exception {
        return userDao.get(id);
    }

    public boolean exists(int id) throws Exception {
        return userDao.get(id) != null;
    }

    public List<User> findByName(String userName) throws Exception {
        return userDao.getAll().stream()
                .filter(u -> u.getUsername().toLowerCase().contains(userName.toLowerCase()))
                .filter(u -> u.getHasPermission() == true)
                .toList();
    }
}