package com.example.module2.obj;

import lombok.ToString;

@ToString
public class Employee {
    private String name;
    private int age;
    private String post;

    public Employee(String name, int age, String post) {
        this.name = name;
        this.age = age;
        this.post = post;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
