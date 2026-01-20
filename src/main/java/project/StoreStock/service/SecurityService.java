package project.StoreStock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.StoreStock.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SecurityService {
    private final Map<String, Integer> failedLoginAttempts = new HashMap<>();
    @Autowired
    UserService userService;

    public boolean login(User user){
        List<User> users;
        try {
            users = userService.findByName(user.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return !users.stream().
                filter(u -> u.getPassword().equals(user.getPassword())).
                toList().isEmpty();
    }

    public void registerFailedLogin(String username) {
        failedLoginAttempts.merge(username, 1, Integer::sum);
    }

    public void resetAttempts(String username) {
        failedLoginAttempts.remove(username);
    }

    public int getAttempts(String username) {
        return failedLoginAttempts.getOrDefault(username, 0);
    }

    public boolean isBlocked(String username) {
        return getAttempts(username) >= 3;
    }
}