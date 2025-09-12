package edu.t1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    List<User> findAll() {
        return userDao.findAll();
    }

    User findById(Integer id) {
        return userDao.findById(id).orElse(null);
    }

    void addUser(User newUser) {
        userDao.addUser(newUser);
    }

    void delUserById(Integer id) {
        userDao.delUserById(id);
    }
}
