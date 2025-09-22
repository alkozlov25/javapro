package edu.t1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    //private final UserDao userDao;
    private final UserRepository userRepository;

    List<User> findAll() {
        return userRepository.findAll()
                .stream()
                .map(x -> new User(x.getId(), x.getUsername()))
                .collect(Collectors.toList());
    }

    User findById(Integer id) {
        return userRepository.findById(id)
                .map(x -> new User(x.getId(), x.getUsername()))
                .orElse(null);
    }

    void addUser(User newUser) {
        userRepository.save(new UserEntity(newUser.getId(), newUser.getUsername()));
    }

    void delUserById(Integer id) {
        userRepository.deleteById(id);
    }
}
