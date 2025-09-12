package edu.t1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("edu.t1");
        DBInitializer dbInitializer = ctx.getBean(DBInitializer.class);
        dbInitializer.createDB();
        UserService mr = ctx.getBean(UserService.class);
        System.out.println(mr.findAll());
        mr.addUser(new User(4, "Ivan"));
        System.out.println(mr.findAll());
        System.out.println(mr.findById(4));
        mr.delUserById(4);
        System.out.println(mr.findById(4));
    }
}