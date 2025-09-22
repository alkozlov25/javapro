package edu.t1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class);
/*        DBInitializer dbInitializer = ctx.getBean(DBInitializer.class);
        dbInitializer.createDB();
        UserService mr = ctx.getBean(UserService.class);
        System.out.println(mr.findAll());
        mr.addUser(new User(4, "Ivan"));
        System.out.println(mr.findAll());
        System.out.println(mr.findById(4));
        mr.delUserById(4);
        System.out.println(mr.findById(4));*/
    }
}