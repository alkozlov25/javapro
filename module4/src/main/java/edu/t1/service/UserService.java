package edu.t1.service;

import edu.t1.dto.User;
import edu.t1.model.UserEntity;
import edu.t1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    //private final UserDao userDao;
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll()
                .stream()
                .map(x -> new User(x.getId(), x.getUsername()))
                .collect(Collectors.toList());
    }

    public User findById(Integer id) {
        return userRepository.findById(id)
                .map(x -> new User(x.getId(), x.getUsername()))
                .orElse(null);
    }

    public void addUser(User newUser) {
        userRepository.save(new UserEntity(newUser.getId(), newUser.getUsername()));
    }

    public void delUserById(Integer id) {
        userRepository.deleteById(id);
    }
}
