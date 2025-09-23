package edu.t1;

import edu.t1.dto.User;
import edu.t1.service.ProductService;
import edu.t1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final DBInitializer dbInitializer;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        dbInitializer.createDB();
        System.out.println(userService.findAll());
        userService.addUser(new User(4, "Ivan"));
        System.out.println(userService.findAll());
        System.out.println(userService.findById(4));
        userService.delUserById(4);
        System.out.println(userService.findById(4));
        System.out.println(productService.findProductsByUserId(1));
    }
}
