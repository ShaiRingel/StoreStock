package project.StoreStock.service;

import org.springframework.stereotype.Service;
import project.StoreStock.entity.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityService {
    private final Map<String, Integer> failedLoginAttempts = new HashMap<>();

    public boolean login(User user){
        return "abc".equals(user.getPassword());
    }

    public void registerFailedLogin(String username) {
        failedLoginAttempts.merge(username, 1, Integer::sum);
    }

    public int getAttempts(String username) {
        return failedLoginAttempts.getOrDefault(username, 0);
    }

    public boolean isBlocked(String username) {
        return getAttempts(username) >= 3;
    }

    public void resetAttempts(String username) {
        failedLoginAttempts.remove(username);
    }
}
